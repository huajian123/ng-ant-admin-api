package com.demo.app.service.permission;

import com.demo.app.config.jwt.JwtTokenUtils;
import enums.TokenEnum;
import model.dto.sys.user.RedisUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import util.ServletUtils;
import util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Set;

/**
 * @program: springBoot-demo - 副本
 * @description:
 * @author: fbl
 * @create: 2021-09-10 14:11
 **/
@Service("ss")
public class HasPermissionService {
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPer(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        RedisUserDto redisUser = isRedisHave();
        if (Objects.isNull(redisUser) || CollectionUtils.isEmpty(redisUser.getPermissions())) {
            return false;
        }
        return hasPermissions(redisUser.getPermissions(), permission);
    }

    public boolean hasAnyPer(String permission){
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        RedisUserDto redisUser = isRedisHave();
        if (Objects.isNull(redisUser) || CollectionUtils.isEmpty(redisUser.getPermissions())) {
            return false;
        }
        return hasAnyPermissions(redisUser.getPermissions(), permission);
    }

    /**
     * redis 中是否有该用户以及该用户是否有权限
     *
     * @return
     */
    private RedisUserDto isRedisHave(){
        HttpServletRequest request = ServletUtils.getRequest();
        String tokenHeader = request.getHeader(TokenEnum.TOKEN_HEADER.getValue());
        String token = tokenHeader.replace(TokenEnum.TOKEN_PREFIX.getValue(), "");
        return jwtTokenUtils.getRedisUser(token);
    }

    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission) {
        return permissions.contains(StringUtils.trim(permission));
    }

    /**
     * 判断是否包含任意权限
     *
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasAnyPermissions(Set<String> permissions, String permission){
        String[] split = permission.split(",");
        boolean has = false;
        for (String paramPer : split) {
            for (String sourcePer : permissions) {
                if(paramPer.equals(sourcePer)){
                    has = true;
                    break;
                }
            }
        }
        return has;
    }
}
