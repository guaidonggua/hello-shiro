package com.ai.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 描述
 * @Author: qiaodong
 * @Date: 2020/8/4 0:33
 */
@Controller
public class MyController {

    @RequestMapping({"/", "/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg", "hello,shiro");
        return "index";
    }

    @RequestMapping({"/user/add"})
    public String add() {
        return "user/add";
    }

    @RequestMapping({"/user/update"})
    public String update() {
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username, String password, Model model) {
        // 获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登录数据，通过用户名密码获取到 token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            // 执行登录的方法，如果没有异常就登录成功
            subject.login(token);
            return "index";
        } catch (UnknownAccountException e) { // 用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) { // 密码错误
            model.addAttribute("msg", "密码错误");
            return "login";
        } catch (Exception e) {
            model.addAttribute("msg", "登录异常");
            return "login";
        }
    }

}
