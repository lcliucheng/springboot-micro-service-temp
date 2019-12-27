package com.lc.admin.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.lc.common.config.AuthProperties;
import com.lc.uitls.JsonUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> 属性配置 </p>
 *
 * @author liucheng
 * @since 2019-12-16
 */
@Configuration

public class PropertiesConfig {
    /**
     * 授权属性
     */
    @Bean
    public AuthProperties authProperties() {
        return new AuthProperties();
    }









}
