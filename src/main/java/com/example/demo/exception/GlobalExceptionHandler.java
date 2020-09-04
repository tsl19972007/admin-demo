package com.example.demo.exception;

import com.example.demo.common.ResponseCodeEnum;
import com.example.demo.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ：tsl
 * @date ：Created in 2020/6/9 15:56
 * @description：
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UnauthorizedException.class)
    public Response handleUnauthorizedException(UnauthorizedException e) {
        log.error("UnauthorizedException:", e);
        return Response.ofFailure(ResponseCodeEnum.RESPONSE_UNAUTHORIZED_ERROR);
    }

    @ExceptionHandler(value = UnauthenticatedException.class)
    public Response handleUnauthenticatedException(ShiroException e) {
        log.error("UnauthenticatedException:", e);
        return Response.ofFailure(ResponseCodeEnum.RESPONSE_UNAUTHENTICATED_ERROR);
    }

    @ExceptionHandler(value = BusinessException.class)
    public Response handleBusinessException(BusinessException e) {
        log.error("BusinessException:", e);
        return Response.ofFailure(e.getCode(), e.getDesc());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public Response handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException:", e);
        return Response.ofFailure(ResponseCodeEnum.RESPONSE_SERVER_ERROR);
    }
}
