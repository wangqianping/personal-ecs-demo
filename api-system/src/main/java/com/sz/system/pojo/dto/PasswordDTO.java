package com.sz.system.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangqianping
 * @date 2022-09-23
 */
@Data
public class PasswordDTO implements Serializable {

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("旧密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    private String newPassword;

}
