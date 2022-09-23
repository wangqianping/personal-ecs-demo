package com.sz.system.controller;

import com.sz.common.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wangqianping
 * @date 2022-09-23
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping("/file")
public class UploadController {


    @ApiOperation("上传头像")
    @PostMapping("uploadProfilePhoto")
    public Response<String> uploadProfilePhoto(MultipartFile file){
        String name = file.getName();
        System.out.println(name);
        return Response.success(name);

    }




}
