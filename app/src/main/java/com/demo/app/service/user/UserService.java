package com.demo.app.service.user;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.app.mapper.department.DepartmentMapper;
import com.demo.app.mapper.user.UserMapper;
import com.demo.app.mapper.user.UserRoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import enums.ErrorCodeEnum;
import model.dto.del.BatchDeleteDto;
import model.dto.sys.role.UpdateRoleDto;
import model.dto.sys.user.*;
import model.entity.sys.SysUser;
import model.entity.sys.UserRole;
import model.vo.sys.DetailUserVo;
import model.vo.sys.SelectUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.CommonConstants;
import result.Result;
import util.SearchFilter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-29 14:55
 **/
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    public Result listUser(SearchFilter searchFilter) {
        PageHelper.startPage(searchFilter.getPageNum(), searchFilter.getPageSize());
        // 搜索条件填充
        SearchUserDto searchUserDto = new SearchUserDto();
        if (Objects.nonNull(searchFilter.getFilters())) {
            searchUserDto = getSearchUserDto(searchFilter.getFilters());
        }

        // 查询所有用户信息
        List<SelectUserVo> selectUserVos = userMapper.listUser(searchUserDto);
        for (SelectUserVo selectUserVo : selectUserVos) {
            List<UpdateRoleDto> updateRoleDtos = userRoleMapper.selectRoleName(selectUserVo.getId());
            List<String> roleNames = updateRoleDtos.stream().map(UpdateRoleDto::getRoleName).collect(Collectors.toList());
            selectUserVo.setRoleName(roleNames);
        }
        PageInfo<SelectUserVo> selectUserVoPageInfo = new PageInfo<>(selectUserVos);
        return Result.success(selectUserVoPageInfo);
    }

    public Result userDetail(Integer id) {
        DetailUserVo detailUserVo = userMapper.selectUserVoById(id);
        List<UpdateRoleDto> updateRoleDtos = userRoleMapper.selectRoleName(id);
        List<Integer> ids = updateRoleDtos.stream().map(UpdateRoleDto::getId).collect(Collectors.toList());
        detailUserVo.setRoleId(ids);
        return Result.success(detailUserVo);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insertUser(InsertUserDto insertUserDto) {
        if (isUniqueUserName(insertUserDto.getUserName())) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_ACCOUNT);
        }
        SysUser sysUser = new SysUser();
        // 密码加密
        String encodePassword = passwordEncoder.encode(insertUserDto.getPassword());
        BeanUtils.copyProperties(insertUserDto, sysUser);
        sysUser.setPassword(encodePassword);
        // 插入用户
        int res = userMapper.insert(sysUser);

        if (CollectionUtil.isNotEmpty(insertUserDto.getRoleId())) {
            // 插入用户角色
            UAndDAndIUserRoleDto uAndDAndIUserRoleDto = new UAndDAndIUserRoleDto();
            uAndDAndIUserRoleDto.setUserId(sysUser.getId());
            uAndDAndIUserRoleDto.setRoleId(insertUserDto.getRoleId());
            insertRoles(uAndDAndIUserRoleDto);
        }
        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_CREATE_FAILED);
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updateUser(UpdateUserDto updateUserDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(updateUserDto, sysUser);
        // 修改用户基本信息
        int res = userMapper.updateById(sysUser);
        // 修改用户角色
        if (CollectionUtil.isNotEmpty(updateUserDto.getRoleId())) {
            // 删除用户下所有的角色
            QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
            userRoleQueryWrapper.eq("user_id", updateUserDto.getId());
            userRoleMapper.delete(userRoleQueryWrapper);
            // 插入角色
            UAndDAndIUserRoleDto uAndDAndIUserRoleDto = new UAndDAndIUserRoleDto();
            uAndDAndIUserRoleDto.setUserId(sysUser.getId());
            uAndDAndIUserRoleDto.setRoleId(updateUserDto.getRoleId());
            insertRoles(uAndDAndIUserRoleDto);
        }
        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        }
        return Result.success();
    }

    public Result updateUserPsd(UpdateUserPsdDto updateUserPsdDto) {
        String oldPassword = updateUserPsdDto.getOldPassword();
        // 验证旧密码是否正确
        String password = userMapper.selectById(updateUserPsdDto.getId()).getPassword();
        if(!passwordEncoder.matches(oldPassword,password)){
            return Result.failure(ErrorCodeEnum.SYS_ERR_PASSWORD_ERROR);
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(updateUserPsdDto.getId());
        sysUser.setPassword(passwordEncoder.encode(updateUserPsdDto.getNewPassword()));
        int res = userMapper.updateById(sysUser);
        if (res == CommonConstants.DeleteCodeStatus.IS_NOT_DELETE) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        }
        return Result.success();
    }

    public Result delUser(BatchDeleteDto batchDeleteDto) {
        userMapper.deleteBatchIds(batchDeleteDto.getIds());
        return Result.success();
    }

    /**
     * @return
     * @Author fbl
     * @Description 判断用户名是否唯一
     * @Date 9:53 2021/2/4
     * @Param
     */
    private boolean isUniqueUserName(String userName) {
        QueryWrapper<SysUser> userNameQuery = new QueryWrapper<SysUser>().eq("user_name", userName);
        List<SysUser> sysUsers = userMapper.selectList(userNameQuery);
        return CollectionUtil.isNotEmpty(sysUsers);
    }

    /**
     * @return
     * @Author fbl
     * @Description 该用户赋予角色
     * @Date 9:53 2021/2/4
     * @Param
     */
    private Integer insertRoles(UAndDAndIUserRoleDto userRoleDto) {
        ArrayList<InsertUserRoleDto> insertUserRoleDtos = new ArrayList<>(userRoleDto.getRoleId().size());
        InsertUserRoleDto insertUserRoleDto = null;
        for (Integer roleId : userRoleDto.getRoleId()) {
            insertUserRoleDto = new InsertUserRoleDto();
            insertUserRoleDto.setRoleId(roleId);
            insertUserRoleDto.setUserId(userRoleDto.getUserId());
            insertUserRoleDtos.add(insertUserRoleDto);
        }
        return userRoleMapper.insertUserRole(insertUserRoleDtos);
    }


    /**
     * @return
     * @Author fbl
     * @Description 置换搜索条件
     * @Date 9:52 2021/2/4
     * @Param
     */
    private SearchUserDto getSearchUserDto(JSONObject jsonObject) {
        SearchUserDto searchUserDto = new SearchUserDto();
        String userName = jsonObject.getString("userName");
        Long mobile = jsonObject.getLong("mobile");
        Boolean available = jsonObject.getBoolean("available");
        Timestamp beginTime = jsonObject.getTimestamp("beginTime");
        Timestamp endTime = jsonObject.getTimestamp("endTime");
        Integer departmentId = jsonObject.getInteger("departmentId");

        if (Objects.nonNull(userName)) {
            searchUserDto.setUserName(userName);
        }
        if (Objects.nonNull(mobile)) {
            searchUserDto.setMobile(mobile);
        }
        if (Objects.nonNull(available)) {
            searchUserDto.setAvailable(available);
        }
        if (Objects.nonNull(beginTime)) {
            searchUserDto.setBeginTime(beginTime);
        }
        if (Objects.nonNull(endTime)) {
            searchUserDto.setEndTime(endTime);
        }
        if (Objects.nonNull(departmentId)) {
            searchUserDto.setDepartmentId(departmentId);
        }
        return searchUserDto;
    }


}
