package com.demo.app.config.security.handle;

import com.alibaba.fastjson.JSON;
import com.demo.app.config.jwt.JwtTokenUtils;
import enums.ErrorCodeEnum;
import enums.TokenEnum;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import result.Result;
import util.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Objects;

/**
 * 认证失败处理类 返回未授权
 *
 * @author fbl
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        String token = getToken(request);
        if(Objects.nonNull(token)){
            try {
                JwtTokenUtils.getUserPermission(token);
            }catch (SignatureException signatureException){
                ServletUtils.renderString(response, JSON.toJSONString(Result.failure(ErrorCodeEnum.SYS_ERR_TOKEN_SIGNATURE)));
                return;
            }
            ServletUtils.renderString(response, JSON.toJSONString(Result.failure(ErrorCodeEnum.SYS_ERR_TOKEN_EXPIRE)));
        }
    }

    private String getToken(HttpServletRequest request) {
        // 得到用户token
        String tokenHeader = request.getHeader(TokenEnum.TOKEN_HEADER.getValue());
        if(Objects.nonNull(tokenHeader)){
            return tokenHeader.replace(TokenEnum.TOKEN_PREFIX.getValue(), "");
        }
        return null;
    }
}
