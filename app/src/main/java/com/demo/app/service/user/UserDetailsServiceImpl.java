package com.demo.app.service.user;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.demo.app.mapper.permission.PermissionMapper;
import com.demo.app.mapper.permission.RolePermissionMapper;
import com.demo.app.mapper.user.UserMapper;
import com.demo.app.mapper.user.UserRoleMapper;
import enums.ErrorCodeEnum;
import model.dto.sys.user.RedisUserDto;
import model.entity.sys.RolePermission;
import model.entity.sys.SysPermission;
import model.entity.sys.SysUser;
import model.entity.sys.UserRole;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: 用户验证权限
 * @description:
 * @author: fbl
 * @create: 2021-09-10 12:46
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 用户名是否存在
        QueryWrapper<SysUser> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", username).eq("is_available",true);
        SysUser userInfo = userMapper.selectOne(userQueryWrapper);
        if (Objects.isNull(userInfo)) {
            throw new RuntimeException(ErrorCodeEnum.SYS_ERR_LOGIN_FAIL.getMsg());
        }
        // 刷新用户最后登录时间
        userInfo.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
        userMapper.updateById(userInfo);

        // 得到用户权限
        Set<String> permissions = getUserRole(userInfo);

        RedisUserDto redisUserDto = new RedisUserDto();
        redisUserDto.setUser(userInfo);
        redisUserDto.setPermissions(permissions);
        return redisUserDto;
    }

    /**
     * @Author fbl
     * @Description 得到用户权限
     * @Date 9:39 2021/1/18
     * @Param userInfo
     * @return UserRoleDto
     */
    private Set<String> getUserRole(SysUser userInfo) {
        // 用户存在，查找角色
        Set<String> codes = permissionMapper.selectPermissionByUserId(userInfo.getId());
        if(CollectionUtil.isNotEmpty(codes)){
            return codes;
        }
        return Collections.emptySet();
    }
}
