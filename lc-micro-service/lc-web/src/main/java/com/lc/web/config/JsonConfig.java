package com.lc.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lc.uitls.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Json配置
 *
 * @author Tortoise
 * @since 2019-12-15
 **/
@Configuration
public class JsonConfig {

    /**
     * Json序列化
     *
     * @return ObjectMapper
     */
    @Bean(name = "objectMapper")
    public ObjectMapper objectMapper() {
        return JsonUtil.getObjectMapper();
    }

}