package com.lc.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 授权配置
 *
 * @author liucheng
 * @since 2019-11-23
 */
@Configuration
public class AuthConfig {

    /**
     * 密码加密器
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder (12);
    }

}
