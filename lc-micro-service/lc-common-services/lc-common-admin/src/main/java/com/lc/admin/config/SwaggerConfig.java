/*
 * All rights Reserved, Designed By www.vanbrother.com
 * Copyright (c) 2012-2018. www.vanbrother.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳市万家兄弟电子信息商务有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

package com.lc.admin.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Swagger ui 配置
 *
 * @author liucheng
 * @since 2019-12-16
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    private static final String NAME = "liucheng";
    private static final String URL = "http://www.chinamobiad.com";
    private static final String EMAIL = "wuxuan@chinamobiad.com";

    /**
     * Swagger请求头
     */
    private static List<Parameter> SWAGGER_HEADERS = Lists.newLinkedList();

    static {
        //参数类型支持header, cookie, body, query etc
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.parameterType("header").name("Authorization").defaultValue("token")
                .description("登录成功时返回的凭证").modelRef(new ModelRef("string"))
                .required(false).build();
        SWAGGER_HEADERS.add(aParameterBuilder.build());
    }

    @Bean
    @Profile({"dev", "test"})
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("system")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lc.service.admin.web"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo()).globalOperationParameters(SWAGGER_HEADERS);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("后台管理Restful Api")
                .contact(new Contact(NAME, URL, EMAIL))
                .version("1.0").description("后台管理").build();
    }

}
