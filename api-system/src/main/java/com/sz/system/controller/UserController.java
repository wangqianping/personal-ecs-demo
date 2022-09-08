package com.sz.system.controller;

import com.sz.common.response.Response;
import com.sz.system.pojo.entity.User;
import com.sz.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("查询用户列表信息")
    @GetMapping("/listUser")
    public Response<List<User>> listUser(){
        List<User> userList = userService.list();
        return Response.success(userList);
    }


}
