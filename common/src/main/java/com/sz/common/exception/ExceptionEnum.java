package com.sz.common.exception;

/**
 * @author wangqianping
 * @date 2022-09-15
 */
public enum ExceptionEnum {
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
