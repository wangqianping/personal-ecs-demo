package com.sz.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangqianping
 */
@Api(tags = "登入认证")
@RequestMapping("/login")
@RestController
public class AuthController {

    @ApiOperation(value = "测试接口")
    @GetMapping("/test")
    public String test(){
        System.out.println("111111");
        return "test";
    }

}
