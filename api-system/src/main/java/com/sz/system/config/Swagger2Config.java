package com.sz.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 访问路径： http://localhost:8001/doc.html
 * 访问路径不是固定的，会随着你引入的jar包不同而调整
 */
@Configuration
public class Swagger2Config {

    /**
     *
     * 配置docket以配置Swagger具体参数
     * @return
     */
    @Bean
    public Docket productDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api-system")
                .apiInfo(new ApiInfoBuilder().title("ECS-DEMO Project API").description("接口文档").version("1.0").contact(new Contact("王前平","","1981517703@qq.com")).build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sz.system.controller"))
                .build();
    }


}
