package com.sz.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "整合测试")
@RequestMapping("/login")
@RestController
public class LoginController {

    @ApiOperation(value = "测试接口")
    @RequestMapping("/test")
    public String test(){
        System.out.println("111111");
        return "test";
    }

}
