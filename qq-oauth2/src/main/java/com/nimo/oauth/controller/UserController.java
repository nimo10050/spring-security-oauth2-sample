package com.nimo.oauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class UserController {

    @RequestMapping("/getUser")
    public Object user() {
        HashMap<String, Object> user = new HashMap<>();
        user.put("username", "zhangsan");
        return user;
    }

}
