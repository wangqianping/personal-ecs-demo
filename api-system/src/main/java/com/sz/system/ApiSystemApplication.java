package com.sz.system;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wangqianping
 * @date 2022-08-19
 */
@SpringBootApplication(scanBasePackages = "com.sz")
@EnableKnife4j
public class ApiSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiSystemApplication.class);
    }
}
