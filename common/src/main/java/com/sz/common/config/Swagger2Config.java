package com.sz.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Config {

    /**
     *
     * 访问路径： http://localhost:8001/swagger-ui/index.html#/
     * 配置docket以配置Swagger具体参数
     *
     * @return
     */
    @Bean
    public Docket productDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("123").description("测试接口文档").version("1.0").build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sz.controller"))
                .build();
    }


}
