package com.example.demo.config.shiro;

import com.example.demo.common.constant.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/23 18:12
 * @description：util of shiro
 */

public class ShiroUtil {

    private static final RandomNumberGenerator RANDOM_NUMBER_GENERATOR = new SecureRandomNumberGenerator();

    public static void main(String[] args) {
        System.out.println(encrypt("admin", "salt"));
    }

    public static UserDetail getCurrentUser() {
        UserDetail userDetail = null;
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            userDetail = (UserDetail) subject.getPrincipal();
        }
        return userDetail;
    }

    public static String getCurrentUsername() {
        UserDetail userDetail = getCurrentUser();
        if (userDetail != null) {
            return userDetail.getUser().getUsername();
        }
        return "";
    }

    public static void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            SecurityUtils.getSecurityManager().logout(subject);
        }
    }

    /**
     * 密码+盐值md5加密三次
     *
     * @param originalPassword
     * @param salt
     * @return 加密结果
     */
    public static String encrypt(String originalPassword, String salt) {
        return new Md5Hash(originalPassword, salt, Constants.HASH_ITERATIONS).toString();
    }

    /**
     * 生成随机盐值
     *
     * @return
     */
    public static String createSalt() {
        return RANDOM_NUMBER_GENERATOR.nextBytes().toHex();
    }
}
