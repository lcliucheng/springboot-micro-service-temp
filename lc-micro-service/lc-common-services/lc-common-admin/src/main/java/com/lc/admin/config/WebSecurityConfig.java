package com.lc.admin.config;



import com.lc.admin.security.CustomAccessDecisionManager;
import com.lc.admin.security.CustomAuthenticationEntryPoint;
import com.lc.admin.security.CustomInvocationSecurityMetadataSource;
import com.lc.admin.security.CustomUserDetailsServiceImpl;
import com.lc.admin.security.filter.CustomAuthenticationFilter;
import com.lc.admin.security.handler.CustomAccessDeniedHandler;
import com.lc.admin.security.handler.CustomAuthenticationFailureHandler;
import com.lc.admin.security.handler.CustomAuthenticationSuccessHandler;
import com.lc.admin.security.handler.CustomLogoutSuccessHandler;
import com.lc.common.config.AuthConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.method.ExpressionBasedPreInvocationAdvice;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * Security配置
 *
 * @author liucheng
 * @since 2019-12-21
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
@AutoConfigureAfter(AuthConfig.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsServiceImpl customUserDetailsService;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final CustomAuthenticationFilter customAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomInvocationSecurityMetadataSource customInvocationSecurityMetadataSource;
    private final CustomAccessDecisionManager customAccessDecisionManager;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(final CustomUserDetailsServiceImpl customUserDetailsService,
                             final CustomAuthenticationFailureHandler customAuthenticationFailureHandler,
                             final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                             final CustomLogoutSuccessHandler customLogoutSuccessHandler,
                             final CustomAuthenticationFilter customAuthenticationFilter,
                             final CustomAccessDeniedHandler customAccessDeniedHandler,
                             final CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                             final CustomInvocationSecurityMetadataSource customInvocationSecurityMetadataSource,
                             final CustomAccessDecisionManager customAccessDecisionManager, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
        this.customAuthenticationFilter = customAuthenticationFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customInvocationSecurityMetadataSource = customInvocationSecurityMetadataSource;
        this.customAccessDecisionManager = customAccessDecisionManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AccessDecisionVoter preInvocationAuthorizationAdviceVoter() {
        return new PreInvocationAuthorizationAdviceVoter(new ExpressionBasedPreInvocationAdvice());
    }

    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor() {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(customInvocationSecurityMetadataSource);
        filterSecurityInterceptor.setAccessDecisionManager(customAccessDecisionManager);
        return filterSecurityInterceptor;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)

                .and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().loginProcessingUrl("/sign/in").successHandler(customAuthenticationSuccessHandler).failureHandler(customAuthenticationFailureHandler)

                .and()
                .logout().logoutUrl("/sign/out").logoutSuccessHandler(customLogoutSuccessHandler).permitAll().invalidateHttpSession(true)

                .and()
                .authorizeRequests()

                // 对于获取token的rest api要允许匿名访问
                .antMatchers("/sign/in").anonymous()
                .antMatchers("/actuator/**").anonymous()

                // swagger start
                .antMatchers("/doc.html").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                // swagger end

                .antMatchers(HttpMethod.OPTIONS, "/**").anonymous()

                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()

                .and()
                // 防止iframe 造成跨域
                .headers().frameOptions().disable()

                .and()

                .addFilterAt(filterSecurityInterceptor(), FilterSecurityInterceptor.class)
                //在 beforeFilter 之前添加 自定义 filter
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customAuthenticationFilter, LogoutFilter.class);

        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }

}
