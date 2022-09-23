package com.sz.system.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sz.common.response.Response;
import com.sz.system.pojo.dto.UserDTO;
import com.sz.system.pojo.entity.User;
import com.sz.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

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
    public Response<List<User>> listUser(@RequestParam(value = "account") String account, @RequestParam(value = "name") String name) {
        List<User> userList = userService.listUser(account, name);
        return Response.success(userList);
    }

    @ApiOperation("/保存一个用户信息")
    @PostMapping("/saveUser")
    public Response<User> saveUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userService.save(user);
        user.setPassword("123456");
        return Response.success(user);
    }


    @ApiOperation("/查询一个用户信息")
    @GetMapping("/getUserById")
    public Response<User> getUserById(@RequestParam("id") Integer id) {
        return Response.success(userService.getById(id));
    }

    @ApiOperation("/修改一个用户信息")
    @PostMapping("/updateUser")
    public Response updateUserById(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO);
        return Response.success();
    }


    @ApiOperation("/删除一个用户信息")
    @DeleteMapping("/deleteUser")
    public Response deleteUser(@RequestParam("id") Long id) {
        userService.removeById(id);
        return Response.success();
    }


}
