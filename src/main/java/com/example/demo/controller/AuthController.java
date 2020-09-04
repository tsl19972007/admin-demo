package com.example.demo.controller;

import com.example.demo.common.Response;
import com.example.demo.common.ResponseCodeEnum;
import com.example.demo.config.shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：tsl
 * @date ：Created in 2020/8/28 15:24
 * @description ：
 */

@RestController
@RequestMapping("/auth")
public class AuthController {
    @GetMapping("login")
    public Response login(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password) {
        if (StringUtils.isEmpty(username)) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "密码不能为空");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            return Response.ofSuccess(ShiroUtil.getCurrentUser());
        } catch (AuthenticationException e) {
            return Response.ofFailure(ResponseCodeEnum.RESPONSE_PARAM_ERROR, "用户名或密码错误");
        }
    }

    @GetMapping("logout")
    public Response logout() {
        ShiroUtil.logout();
        return Response.ofSuccess();
    }

    @GetMapping("currentUser")
    public Response getCurrentUser() {
        return Response.ofSuccess(ShiroUtil.getCurrentUser());
    }
}
