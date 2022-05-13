package com.demo.app.service.permission;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.app.mapper.permission.PermissionMapper;
import com.demo.app.mapper.permission.RolePermissionMapper;
import enums.ErrorCodeEnum;
import model.entity.sys.RolePermission;
import model.dto.sys.permission.UpdateRolePermissionDto;
import model.entity.sys.SysPermission;
import model.vo.sys.PermissionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import result.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-22 15:21
 **/
@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission> {

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Transactional(rollbackFor = Exception.class)
    public Result updatePermission(UpdateRolePermissionDto updatePermissionDto) {
        QueryWrapper<RolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
        rolePermissionQueryWrapper.eq("role_id", updatePermissionDto.getRoleId());
        // 删除角色所有权限
        rolePermissionMapper.delete(rolePermissionQueryWrapper);
        // 新增新的权限
        List<Integer> permissionIds = updatePermissionDto.getPermissionIds();

        ArrayList<RolePermission> rolePermissions = new ArrayList<>();
        RolePermission rolePermission = null;
        for (Integer permissionId : permissionIds) {
            rolePermission = new RolePermission();
            rolePermission.setPermissionId(permissionId);
            rolePermission.setRoleId(updatePermissionDto.getRoleId());
            rolePermissions.add(rolePermission);
        }

        boolean res = saveBatch(rolePermissions);
        if (!res) {
            return Result.failure(ErrorCodeEnum.SYS_ERR_UPDATE_FAILED);
        }
        return Result.success();
    }

    public Result userPermission(Integer roleId) {
        List<Integer> permissionIds = rolePermissionMapper.getPermissionIds(roleId);
        if (CollectionUtil.isEmpty(permissionIds)) {
            return Result.success(Collections.emptyList());
        }
        QueryWrapper<SysPermission> sysPermissionQueryWrapper = new QueryWrapper<>();
        sysPermissionQueryWrapper.in("id", permissionIds);
        List<SysPermission> permissionList = permissionMapper.selectList(sysPermissionQueryWrapper);
        List<String> codes = permissionList.stream().map(SysPermission::getCode).collect(Collectors.toList());
        return Result.success(codes);
    }


    /**
     * @return List<PermissionVo>
     * @Author fbl
     * @Description 转换权限模型
     * @Date 16:03 2021/1/25
     * @Param permissionList
     */
    private List<PermissionVo> getPermissionVos(List<SysPermission> permission) {
        ArrayList<PermissionVo> permissionVos = new ArrayList<>();
        PermissionVo permissionVo = null;
        for (SysPermission sysPermission : permission) {
            permissionVo = new PermissionVo();
            BeanUtils.copyProperties(sysPermission, permissionVo);
            permissionVos.add(permissionVo);
        }

        return permissionVos;

    }

}
