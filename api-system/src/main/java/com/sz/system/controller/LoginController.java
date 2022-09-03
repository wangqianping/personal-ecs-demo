package com.sz.system.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/login")
@RestController
public class LoginController {

    @RequestMapping("/test")
    public String test(){
        System.out.println("111111");
        return "test";
    }

}
