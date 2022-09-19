package com.sz.gateway.filter;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sz.gateway.config.WhiteList;
import com.sz.gateway.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * JWT 拦截器
 */
@Component
public class JwtFilter implements GlobalFilter, Ordered {


    @Resource
    private WhiteList whiteList;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        RequestPath path = exchange.getRequest().getPath();
        //白名单放行
        if (whiteList.getAllowPaths().contains(path)) {
            return chain.filter(exchange);
        }
        //校验token
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(token)) {
            JSONObject response = new JSONObject();
            response.put("code", 400);
            response.put("msg", "token is null");
            return fillExchangeResponse(exchange, response);
        }

        JwtUtil.verifyToken(token);
        //todo 存储用户token信息

        return chain.filter(exchange);
    }

    /**
     * @param exchange
     * @param response
     * @return
     */
    private Mono<Void> fillExchangeResponse(ServerWebExchange exchange, JSONObject response) {
        byte[] bytes = new byte[0];
        try {
            bytes = new ObjectMapper().writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        Flux<DataBuffer> just = Flux.just(buffer);
        return exchange.getResponse().writeWith(just);
    }


    @Override
    public int getOrder() {
        return 1;
    }
}
