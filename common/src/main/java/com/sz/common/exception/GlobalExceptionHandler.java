package com.sz.common.exception;

import com.sz.common.response.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 *
 * @author wangqianping
 * @date 2022-09-15
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Response ServiceExceptionHandler(ServiceException exception) {
        return Response.error(exception);
    }

}
