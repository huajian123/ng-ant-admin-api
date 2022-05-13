package com.demo.app.controller.exception;

import com.demo.app.config.exception.RepetitionSubmitException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-01-18 07:54
 **/
@RestController
public class ExceptionController {

    /**
     * 重新抛出异常
     */
    @RequestMapping("/expiredJwtException")
    public void expiredJwtException(HttpServletRequest request) throws ExpiredJwtException {
        throw ((ExpiredJwtException) request.getAttribute("expiredJwtException"));
    }

    @RequestMapping("/signatureException")
    public void signatureException(HttpServletRequest request) throws SignatureException {
        throw ((SignatureException) request.getAttribute("signatureException"));
    }

    // 是一个
    @RequestMapping("/repetitionSubmitException")
    public void repetitionSubmitException(HttpServletRequest request) throws RepetitionSubmitException {
        throw ((RepetitionSubmitException) request.getAttribute("repetitionSubmitException"));
    }
}
