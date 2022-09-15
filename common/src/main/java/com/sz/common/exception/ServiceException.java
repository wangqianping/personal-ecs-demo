package com.sz.common.exception;

import lombok.Data;

/**
 * 自定义异常
 *
 * @author wangqianping
 * @date 2022-09-15
 */
@Data
public class ServiceException extends Exception {

    private Integer code;
    private String message;


    public ServiceException() {

    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(ExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }
}
