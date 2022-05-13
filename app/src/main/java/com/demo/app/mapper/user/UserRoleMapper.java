package com.demo.app.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.entity.sys.UserRole;
import model.dto.sys.user.InsertUserRoleDto;
import model.dto.sys.role.UpdateRoleDto;

import java.util.List;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-15 09:37
 **/
public interface UserRoleMapper extends BaseMapper<UserRole> {

   Integer insertUserRole(List<InsertUserRoleDto> userRoleDtos);

   List<UpdateRoleDto> selectRoleName(Integer userId);
}
