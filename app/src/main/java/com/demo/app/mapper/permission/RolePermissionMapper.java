package com.demo.app.mapper.permission;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.entity.sys.RolePermission;
import model.entity.sys.SysPermission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-15 09:52
 **/
public interface RolePermissionMapper extends BaseMapper<RolePermission> {


    @Select("select permission_id from role_permission where role_id = #{roleId}")
    List<Integer> getPermissionIds(Integer roleId);

}
