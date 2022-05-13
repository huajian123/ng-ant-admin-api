package com.demo.app.config.security.handle;

import com.alibaba.fastjson.JSON;
import com.demo.app.config.jwt.JwtTokenUtils;
import enums.TokenEnum;
import model.dto.sys.user.RedisUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import result.Result;
import util.ServletUtils;
import util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 *
 * @author fbl
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){

        String token = getToken(request);
        RedisUserDto redisUserDto = jwtTokenUtils.getRedisUser(token);
        if (StringUtils.isNotNull(redisUserDto)) {
            // 删除用户缓存记录
            jwtTokenUtils.delLoginUser(redisUserDto.getToken());
        }
        ServletUtils.renderString(response, JSON.toJSONString(Result.success()));
    }

    private String getToken(HttpServletRequest request) {
        // 得到用户token
        String tokenHeader = request.getHeader(TokenEnum.TOKEN_HEADER.getValue());
        return tokenHeader.replace(TokenEnum.TOKEN_PREFIX.getValue(), "");
    }
}

