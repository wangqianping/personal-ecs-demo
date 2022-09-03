package com.sz.system.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/login")
@RestController
public class LoginController {

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

}
