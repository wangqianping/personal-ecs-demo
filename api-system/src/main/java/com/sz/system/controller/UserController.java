package com.sz.system.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.sz.common.response.Response;
import com.sz.system.pojo.dto.UserDTO;
import com.sz.system.pojo.entity.User;
import com.sz.system.service.UserService;
import com.sz.system.util.FileUtil;
import com.sz.system.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public Response<User> getUserById(HttpServletRequest request,@RequestParam("id") Integer id) {
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


    @ApiOperation("上传头像")
    @PostMapping("/uploadProfilePhoto")
    @CrossOrigin
    public Response<String> uploadProfilePhoto(HttpServletRequest request,MultipartFile file){
        String path = FileUtil.uploadFile(file);
        if(StringUtils.isNotEmpty(path)){
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            User user = JwtUtil.getUser(token);
            LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(User::getId,user.getId()).set(User::getProfilePhoto,path);
            userService.update(updateWrapper);
        }
        return Response.success();

    }



}
