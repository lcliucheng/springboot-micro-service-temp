package com.lc.admin.config;

import com.lc.common.interceptor.AuthInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p> 配置 </p>
 *
 * @author liucheng
 * @since 2019-12-16
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
@MapperScan(basePackages = "com.lc.admin.repository")
public class AdminConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public AdminConfig(final AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }




    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/swagger-resources/**", "/callback/**", "/webjars/**",
                        "/v2/**", "/swagger-ui.html/**");
    }

}
