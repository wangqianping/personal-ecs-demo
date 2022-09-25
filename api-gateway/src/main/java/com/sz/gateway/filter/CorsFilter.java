package com.sz.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 跨域问题解决
 */
public class CorsFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Access-Control-Allow-Credentials","true");
        response.getHeaders().set("Access-Control-Allow-Origin","*");
        response.getHeaders().set("Access-Control-Request-Methods","GET, POST, PUT, DELETE, OPTIONS");
        response.getHeaders().set("Access-Control-Allow-Headers","x-requested-with,content-type,test-token,test-sessid");

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
