package com.sz.system.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangqianping
 * @date 2022-09-15
 */
@Data
public class LoginUserVO {

    @ApiModelProperty(value = "姓名")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "是否是管理员")
    private Boolean isAdmin;

    @ApiModelProperty(value = "令牌")
    private String token;

}
