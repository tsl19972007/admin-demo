package com.example.demo.config.shiro;

import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/23 16:38
 * @description：
 */

@Slf4j
public class UserRealm extends AuthorizingRealm {
    /**
     * 配置懒加载，否则与Spring初始化冲突，导致@Transactional不可用
     */
    @Autowired
    @Lazy
    UserService userService;

    /**
     * 分身份用户的授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserDetail userDetail = (UserDetail) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(userDetail.getRoles());
        info.addStringPermissions(userDetail.getPermissions());
        return info;
    }

    /**
     * 分身份用户的认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userService.getByUsername(username);
        if (null != user) {
            ByteSource salt = ByteSource.Util.bytes(user.getSalt());
            UserDetail userDetail = new UserDetail();
            userDetail.setUser(user);
            userDetail.setRoles(userService.getRoleNamesByUserId(user.getId()));
            userDetail.setPermissions(userService.getPermissionNamesByUserId(user.getId()));
            return new SimpleAuthenticationInfo(userDetail, user.getPassword(), salt, getName());
        } else {
            throw new UnsupportedTokenException("userName错误");
        }
    }
}
