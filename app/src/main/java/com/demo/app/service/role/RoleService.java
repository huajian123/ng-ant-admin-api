package com.demo.app.service.role;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.demo.app.mapper.role.RoleMapper;
import com.demo.app.mapper.user.UserRoleMapper;
import enums.ErrorCodeEnum;
import model.entity.sys.SysRole;
import model.entity.sys.UserRole;
import model.dto.del.BatchDeleteDto;
import model.dto.sys.user.InsertRoleDto;
import model.dto.sys.role.UpdateRoleDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import result.CommonConstants;
import result.Result;
import util.SearchFilter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-19 10:24
 **/
@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    public Result roleList(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());
        JSONObject filters = searchFilter.getFilters();
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        if(Objects.nonNull(filters)){
            String roleName = filters.getString("roleName");
            if(Objects.nonNull(roleName)){
                sysRoleQueryWrapper.like("role_name",roleName);
            }
        }
        List<SysRole> sysRoles = roleMapper.selectList(sysRoleQueryWrapper);
        PageInfo<SysRole> sysRolePageInfo = new PageInfo<>(sysRoles);
        return Result.success(sysRolePageInfo);

    }

    public Result insertRole(InsertRoleDto roleVo) {
        // 角色名重复
        if(isUniqueRoleName(roleVo.getRoleName())){
            return Result.failure(ErrorCodeEnum.SYS_ERR_ROLE_REPETITION);
        }
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleVo, sysRole);
        roleMapper.insert(sysRole);
        return Result.success();
    }

    public Result updateRole(UpdateRoleDto roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        roleMapper.updateById(sysRole);
        return Result.success();
    }

    public Result batchDeleteRole(BatchDeleteDto batchDeleteRoleDto){
        @NotNull List<Integer> ids = batchDeleteRoleDto.getIds();
        for (Integer id : ids) {
            if(isHaveUser(id)){
                return Result.failure(ErrorCodeEnum.SYS_ERR_ROLE_DELETE);
            }
        }
        int res = roleMapper.deleteBatchIds(ids);
        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_DELETE_FAILED);
        }
        return Result.success();
    }

    private boolean isHaveUser(Integer roleId){
        // 如果有用户是这个用户无法删除
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<UserRole>().eq("role_id", roleId);
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleQueryWrapper);
        return CollectionUtil.isNotEmpty(userRoles);
    }

    /**
     * @Author fbl
     * @Description 删除用户的角色信息
     * @Date 14:28 2021/2/8
     * @Param id
    */
    private void deleteUserRole(Integer id){
        QueryWrapper<UserRole> roleId = new QueryWrapper<UserRole>().eq("role_id", id);
        userRoleMapper.delete(roleId);
    }

    public Result getRole(Integer id) {
        return Result.success(roleMapper.selectById(id));
    }

    /**
     * 角色名唯一
     *
     * @param roleName
     * @return
     */
    private boolean isUniqueRoleName(String roleName){
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper.eq("role_name", roleName);
        return CollectionUtil.isNotEmpty(roleMapper.selectList(sysRoleQueryWrapper));
    }
}
