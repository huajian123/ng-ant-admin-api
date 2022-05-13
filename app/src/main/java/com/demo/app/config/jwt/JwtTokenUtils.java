package com.demo.app.config.jwt;

import com.demo.app.config.redis.RedisCache;
import enums.TokenEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import model.dto.sys.user.RedisUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @program: spring-security
 * @description:
 * @author: fbl
 * @create: 2020-12-02 14:08
 **/
@Component
public class JwtTokenUtils {

    @Autowired
    RedisCache redisCache;

    /**
     * 创建token
     */
    public String createToken(RedisUserDto user) {

        byte[] keyBytes = Decoders.BASE64.decode(TokenEnum.SECRET.getValue());
        Key key = Keys.hmacShaKeyFor(keyBytes);

        HashMap<String, Object> map = new HashMap<>();
        // 转换成字符串
        String permissionStr = String.join(",", user.getPermissions());
        map.put(TokenEnum.ROLE_CLAIMS.getValue(), permissionStr);
        map.put(TokenEnum.USER_ID.getValue(), user.getUser().getId());
        String token = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                // 这里要早set一点，放到后面会覆盖别的字段
                .setClaims(map)
                .setIssuer(TokenEnum.ISS.getValue())
                .setSubject(user.getUser().getUserName())
                .setIssuedAt(new Date())
                // 过期时间由redis控制，jwt不再设置过期时间
                //.setExpiration(new Date(System.currentTimeMillis() + TokenEnum.EXPIRATION.getTime() * 1000))
                .compact();

        // 置入redis
        user.setToken(token);
        user.setLoginTime(System.currentTimeMillis());
        // 当前时间加上过期时间（一个小时）的毫秒数
        Long expireTime = user.getLoginTime() + (long) TokenEnum.EXPIRATION.getTime() * TokenEnum.MILLIS_SECOND.getTime();
        user.setExpireTime(expireTime);
        refreshToken(user);
        return token;
    }


    /**
     * 刷新令牌有效期
     *
     * @param redisUserDto 登录信息
     */
    public void refreshToken(RedisUserDto redisUserDto) {
        // redis超时时间
        Integer timeout = TokenEnum.EXPIRATION.getTime();
        // 置入redis
        redisCache.setCacheObject(redisUserDto.getToken(), redisUserDto, timeout, TimeUnit.SECONDS);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param redisUserDto
     * @return 令牌
     */
    public void verifyToken(RedisUserDto redisUserDto) {
        long expireTime = redisUserDto.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= TokenEnum.MILLIS_MINUTE_TEN.getTime()) {
            refreshToken(redisUserDto);
        }
    }


    public RedisUserDto getRedisUser(String token) {
        RedisUserDto redisUserDto = redisCache.getCacheObject(token);
        if (Objects.nonNull(redisUserDto)) {
            return redisUserDto;
        }
        return null;
    }


    /**
     * 从token中获取用户名
     */
    public static String getUsername(String token) {
        try {
            return getTokenBody(token).getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        }

    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            redisCache.deleteObject(token);
        }
    }

    /**
     * 是否已过期
     */
    public static boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return e.getClaims().getExpiration().before(new Date());
        }
    }

    /**
     * 获取token
     *
     * @param token
     * @return
     */
    private static Claims getTokenBody(String token) throws SignatureException{
        return Jwts.parser()
                .setSigningKey(TokenEnum.SECRET.getValue())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取用户权限
     *
     * @param token
     * @return
     */
    public static String getUserPermission(String token) throws SignatureException{
        return (String) getTokenBody(token).get(TokenEnum.ROLE_CLAIMS.getValue());
    }

    /**
     * 获取用户id
     *
     * @param token
     * @return
     */
    public static Integer getUserId(String token) {
        try {
            return (Integer) getTokenBody(token).get(TokenEnum.USER_ID.getValue());
        } catch (ExpiredJwtException e) {
            return (Integer) e.getClaims().get(TokenEnum.USER_ID.getValue());
        }

    }

}

