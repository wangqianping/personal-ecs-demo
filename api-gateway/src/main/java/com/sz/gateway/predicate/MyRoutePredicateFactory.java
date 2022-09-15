package com.sz.gateway.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 自定义断言
 * @author wangqianping
 * @since 2022-09-15
 */
@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

    public static final String PARAM_KEY = "queryParam";

    public MyRoutePredicateFactory() {
        super(MyRoutePredicateFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("queryParam");
    }

    @Override
    public Predicate<ServerWebExchange> apply(MyRoutePredicateFactory.Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                Map<String, String> stringStringMap = serverWebExchange.getRequest().getQueryParams().toSingleValueMap();
                return stringStringMap.containsKey(config.getQueryParam());
            }
        };
    }

    @Validated
    public static class Config{
        private String queryParam;

        public String getQueryParam() {
            return queryParam;
        }

        public void setQueryParam(String queryParam) {
            this.queryParam = queryParam;
        }
    }
}
