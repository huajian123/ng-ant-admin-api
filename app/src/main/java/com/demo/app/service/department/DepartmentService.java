package com.demo.app.service.department;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.app.mapper.department.DepartmentMapper;
import com.demo.app.mapper.user.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.del.BatchDeleteDto;
import model.dto.department.InsertDepartmentDto;
import model.dto.department.UpdateDepartmentDto;
import model.entity.department.SysDepartment;
import model.entity.sys.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import result.CommonConstants;
import result.Result;
import util.SearchFilter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-04-01 09:55
 **/
@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    UserMapper userMapper;

    public Result departmentList(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(),searchFilter.getPageSize());
        QueryWrapper<SysDepartment> deptQuery = new QueryWrapper<SysDepartment>().orderByAsc("order_num");
        JSONObject filters = searchFilter.getFilters();
        if(Objects.nonNull(filters)){
            String departmentName = filters.getString("departmentName");
            if(Objects.nonNull(departmentName)){
                deptQuery.like("department_name",departmentName);
            }
            Boolean state = filters.getBoolean("state");
            if(Objects.nonNull(state)){
                deptQuery.eq("state",state);
            }
        }
        List<SysDepartment> departments = departmentMapper.selectList(deptQuery);
        PageInfo<SysDepartment> sysDepartmentPageInfo = new PageInfo<>(departments);
        return Result.success(sysDepartmentPageInfo);
    }

    public Result departmentDetail(Integer id) {
        SysDepartment sysDepartment = departmentMapper.selectById(id);
        return Result.success(sysDepartment);
    }

    public Result updateDepartment(UpdateDepartmentDto updateDepartmentDto) {
        if (!isUniqueDeptName(updateDepartmentDto.getDepartmentName(),0,updateDepartmentDto.getId())) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_DEPT_REPETITION);
        }
        SysDepartment sysDepartment = new SysDepartment();
        BeanUtils.copyProperties(updateDepartmentDto, sysDepartment);
        departmentMapper.updateById(sysDepartment);
        return Result.success();
    }

    public Result addDepartment(InsertDepartmentDto insertDepartmentDto) {
        if (!isUniqueDeptName(insertDepartmentDto.getDepartmentName(),1,null)) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_DEPT_REPETITION);
        }
        SysDepartment sysDepartment = new SysDepartment();
        BeanUtils.copyProperties(insertDepartmentDto, sysDepartment);
        departmentMapper.insert(sysDepartment);
        return Result.success();
    }

    public Result delDepartment(BatchDeleteDto batchDeleteDto) {
        List<Integer> ids = batchDeleteDto.getIds();
        // 如果部门中有人员，不能删除
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.in("department_id", ids);
        List<SysUser> sysUsers = userMapper.selectList(sysUserQueryWrapper);
        if (CollectionUtil.isNotEmpty(sysUsers)) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_DEPT_DELETE_USER);
        }

        // 有子部门 无法删除
        List<SysDepartment> sysDepartments = departmentMapper.selectList(new QueryWrapper<>());
        List<SysDepartment> haveChild = sysDepartments.stream().filter(e -> ids.contains(e.getFatherId())).collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(haveChild)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_DEPT_DELETE_CHILD);
        }

        int res = departmentMapper.deleteBatchIds(ids);
        if (res == CommonConstants.DataBaseOperationStatus.IS_FAILURE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_DELETE_FAILED);
        }
        return Result.success();
    }

    /**
     * @return
     * @Author fbl
     * @Description 部门名称不能重复
     * @Date 10:24 2021/4/1
     * @Param
     */
    private boolean isUniqueDeptName(String name,Integer type,Integer id) {
        QueryWrapper<SysDepartment> sysDepartmentQueryWrapper = new QueryWrapper<SysDepartment>().eq("department_name", name);
        if(type.equals(0)){
            sysDepartmentQueryWrapper.notIn("id",id);
        }
        List<SysDepartment> departments = departmentMapper.selectList(sysDepartmentQueryWrapper);
        return CollectionUtil.isEmpty(departments);
    }
}
