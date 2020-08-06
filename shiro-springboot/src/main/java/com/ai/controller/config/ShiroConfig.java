package com.ai.controller.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 描述
 * @Author: qiaodong
 * @Date: 2020/8/4 1:06
 */
@Configuration
public class ShiroConfig {

    /**
     * 第三步： ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        // 添加 shiro 内置过滤器
        /*
            anon: 无需认证就可以访问
            authc: 必须认证才能访问
            user: 必须拥有，记住我，功能才能用
            perms: 拥有对某个资源的权限才能访问
            role: 拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();

        // 授权，正常情况下没有权限，会跳转到未授权页面
        filterMap.put("/user/add", "perms[user:add]");

        // add 和 update 只有在认证后能可以访问
        filterMap.put("/user/update", "authc");

        bean.setFilterChainDefinitionMap(filterMap);

        // 设置登录的请求：如果没有权限跳转到登录
        bean.setLoginUrl("/toLogin");

        // 未授权时跳转此页面
//        bean.setUnauthorizedUrl("url");

        return bean;
    }

    /**
     * 第二步： DefaultWebSecurityManager
     * ▶ @Bean - name: 指定 bean 名称，默认不指定时为方法名
     * ▶ @Qualifier: 指定 bean，如这里直接指定第一步的方法返回 bean
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联 UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 第一步： 创建 realm 对象，需要自定义类
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }
}
