package com.nimo.oauth2demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther zgp
 * @desc 登陆页面
 * @date 2020/5/4
 */
@Controller
public class PageController {

    @RequestMapping("loginPage")
    public String loginPage(){
        System.out.println("进入登陆页面");
        return "login";
    }
}
