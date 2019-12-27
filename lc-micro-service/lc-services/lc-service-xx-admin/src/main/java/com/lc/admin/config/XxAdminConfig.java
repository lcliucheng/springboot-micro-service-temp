package com.lc.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p> 配置 </p>
 *
 * @author Tortoise
 * @since 2019-12-16
 */
@Configuration
@MapperScan(basePackages = "com.lc.admin.repository.*")
public class XxAdminConfig {



}
