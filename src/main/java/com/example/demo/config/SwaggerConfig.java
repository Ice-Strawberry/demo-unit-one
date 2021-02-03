package com.example.demo.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author Rhy
 * @title swagger配置类
 * @description swagger配置类
 * @createTime 2020年09月16日 16:48:00
 * @modifier：Rhy
 * @modification_time：2020-09-16 16:48
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
    @Bean("apiV1")
    public Docket initDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(initApiInfo())
                .groupName("V1.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Lists.newArrayList(initSecutityContext()))
                .securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()));
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }

    /**
     * security上下文配置
     * @return
     */
    private SecurityContext initSecutityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }

    /**
     * API基本文档信息配置
     * @return
     */
    private ApiInfo initApiInfo() {
        return new ApiInfoBuilder()
                .title("测试项目swagger")
                .contact(new Contact("王圆圆","localhost","1522413575@qq.com"))
                .description("测试项目接口文档")
                .termsOfServiceUrl("localhost")
                .version("1.0")
                .build();
    }
}
