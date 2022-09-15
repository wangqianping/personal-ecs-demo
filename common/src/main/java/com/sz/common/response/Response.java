package com.sz.common.response;

import com.sz.common.exception.ServiceException;
import lombok.Data;

/**
 * @author wangqianping
 * @date 2022-09-07
 */
@Data
public class Response<T> {

    private Integer code;
    private String message;
    private T data;

    public static Response success(Object data) {
        Response response = new Response();
        response.setCode(200);
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    public static Response error(String msg) {
        Response response = new Response();
        response.setCode(400);
        response.setMessage(msg);
        return response;
    }


    public static Response success() {
        Response response = new Response();
        response.setCode(200);
        response.setMessage("success");
        return response;
    }

    public static Response error() {
        Response response = new Response();
        response.setCode(400);
        response.setMessage("error");
        return response;
    }

    public static Response error(ServiceException exception) {
        Response response = new Response();
        response.setCode(exception.getCode());
        response.setMessage(exception.getMessage());
        return response;
    }
}
