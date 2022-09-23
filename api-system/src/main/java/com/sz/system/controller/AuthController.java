package com.sz.system.controller;

import com.sz.common.exception.ServiceException;
import com.sz.common.response.Response;
import com.sz.system.pojo.dto.PasswordDTO;
import com.sz.system.pojo.dto.UserDTO;
import com.sz.system.pojo.entity.User;
import com.sz.system.pojo.vo.LoginUserVO;
import com.sz.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wangqianping
 */
@Api(tags = "登入认证")
@RequestMapping("/auth")
@RestController
public class AuthController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "账号密码登入")
    @PostMapping("/login")
    public Response login(@RequestBody UserDTO userDTO) throws ServiceException {
        LoginUserVO loginUserVO = userService.login(userDTO);
        return Response.success(loginUserVO);
    }


    @ApiOperation(value = "修改密码")
    @PostMapping("/updatePassword")
    public Response updatePassword(@RequestBody PasswordDTO passwordDTO) throws ServiceException {
        userService.updatePassword(passwordDTO);
        return Response.success();
    }


    @ApiOperation(value = "注册接口")
    @PostMapping("/register")
    public Response register(@RequestBody UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userService.save(user);
        return Response.success();
    }

    @ApiOperation(value = "发送验证码")
    @GetMapping("/sendVerificationCode")
    public Response<String> sendVerificationCode(@RequestParam("email") String email) {
        return Response.success("12345");
    }


}
