package com.ai.controller.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @Description: 自定义的 Realm 需要继承 AuthorizingRealm
 * @Author: qiaodong
 * @Date: 2020/8/4 0:59
 */
public class UserRealm extends AuthorizingRealm {
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了===>授权doGetAuthorizationInfo");

        // SimpleAuthorizationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");

        return info;
    }
    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了===>认证doGetAuthenticationInfo");

        // 将 token 转换为我们认识的 UsernamePasswordToken，也就是登录那里的 token
        // 只要登录就会进入到这里，从这里拿到用户的所有信息，shiro 中这些类相关联的，取到数据后保存全局共享
        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;

        // 用户名，密码 数据库中取
        String name = "root";
        String password = "123456";

        /**
         * 连接数据库写法
         * select * from user where name = userToken.getUsername()
         */

        if(!userToken.getUsername().equals(name)) {
            // 抛出异常：用户名不存在 UnknownAccountException
            return null;
        }
        // 密码认证，shiro自己做~（获取当前用户的认证，传递的密码对象，认证名）
        // 加密了
        return new SimpleAuthenticationInfo("", password, "");
    }
}
