package com.demo.app.config.exception;

import enums.ErrorCodeEnum;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import result.Result;


/**
 * @program: fire_control
 * @description: 处理自定义的业务异常
 * @author: fbl
 * @create: 2021-01-15 16:21
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 重复提交
     *
     * @return
     */
    @ExceptionHandler(value = RepetitionSubmitException.class)
    @ResponseBody
    public Result repetitionSubmitException() {
        return Result.failure(ErrorCodeEnum.SYS_ERR_REPETITION_SUBMIT);
    }

    /**
     * 账号或密码错误
     *
     * @return
     */
    @ExceptionHandler(value = UserPasswordNotMatchException.class)
    @ResponseBody
    public Result userPasswordNotMatchException() {
        return Result.failure(ErrorCodeEnum.SYS_ERR_LOGIN_FAIL);
    }

    /**
     * 参数提交错误
     *
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Result.failure(ErrorCodeEnum.SYS_ERR_VALIDATION_PARAMS_ERROR.setParam(e.getMessage()));
    }
}
