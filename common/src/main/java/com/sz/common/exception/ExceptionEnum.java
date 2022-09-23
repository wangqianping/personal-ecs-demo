package com.sz.common.exception;

/**
 * @author wangqianping
 * @date 2022-09-15
 */
public enum ExceptionEnum {

    ACCOUNT_NOT_EXIST(1001,"账号不存在"),
    PASSWORD_ERROR(1002,"密码错误"),
    OLD_PASSWORD_ERROR(1003,"旧密码输入错误")


    ;


    private Integer code;
    private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
