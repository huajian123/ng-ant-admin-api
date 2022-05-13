package com.demo.app.config.jwt;

import enums.TokenEnum;
import model.dto.sys.user.RedisUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: spring-security
 * @description: 验证成功当然就是进行鉴权了，每一次需要权限的请求都需要检查该用户是否有该权限去操作该资源，当然这也是框架帮我们做的，那么我们需要做什么呢？
 * 很简单，只要告诉spring-security该用户是否已登录，是什么角色，拥有什么权限就可以了。
 * @author: fbl
 * @create: 2020-12-02 14:25
 **/
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenUtils tokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(TokenEnum.TOKEN_HEADER.getValue());
        if (Objects.nonNull(tokenHeader)) {
            String token = tokenHeader.replace(TokenEnum.TOKEN_PREFIX.getValue(), "");
            RedisUserDto redisUser = tokenUtils.getRedisUser(token);
            if (Objects.nonNull(redisUser)) {
                // 验证token有效期
                tokenUtils.verifyToken(redisUser);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(redisUser, null, redisUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}
