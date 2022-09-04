package com.sz.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 配置docket以配置Swagger具体参数
     * @return
     */
    @Bean
    public Docket productDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)//配置是否启用Swagger，如果是false，在浏览器将无法访问
                .pathMapping("/")
                .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.sz.controller"))//配置需要扫描的包
                .paths(PathSelectors.any()) //配置扫描的路径，我设置为所有的路径
                .build()
                .ignoredParameterTypes(HttpSession.class, HttpServletRequest.class, HttpServletResponse.class)  //忽略参数
                .apiInfo(new ApiInfoBuilder()
                        .title("接口文档")  //标题
                        .description("测试接口文档")  //描述
                        .version("1.0").build());
    }







}
