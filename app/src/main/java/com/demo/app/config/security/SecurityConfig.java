package com.demo.app.config.security;

import com.demo.app.config.jwt.JWTAuthorizationFilter;
import com.demo.app.config.security.handle.AuthenticationEntryPointImpl;
import com.demo.app.config.security.handle.LogoutSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @program: spring-security
 * @description:
 * @author: fbl
 * @create: 2020-12-01 14:40
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * jwt——token认证过滤器
     */
    @Autowired
    JWTAuthorizationFilter authorizationFilter;

    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 自定义用户认证逻辑
     */
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                // 禁用 CSRF
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                // 防止iframe 造成跨域
                .headers()
                .frameOptions()
                .disable()

                // 不创建会话
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http
                .authorizeRequests() // 授权配置
                // 自定义匿名访问所有url放行 ： 允许匿名和带权限以及登录用户访问
                // 阿里巴巴 druid
                .antMatchers("/api/login/").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/file/**").permitAll()

                .antMatchers("/druid/**").permitAll()
                .antMatchers("/default/**").permitAll()
                .antMatchers("/").permitAll()
                // swagger 文档
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("swagger/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-ui.html/*").permitAll()
                .antMatchers("/swagger-resources").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                .antMatchers("/v2/api-docs-ext").permitAll()
                // 静态资源等等
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.svg",
                        "/**/*.ico",
                        "/**/*.png",
                        "/**/*.jpg",
                        "/**/*.xlsx",
                        "/webSocket/**"
                ).permitAll()
                // anyRequest 只能配置一个，多个会报错
                //其他接口需要登录后才能访问
                .anyRequest().authenticated()
                .and()
                // 退出
                .logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler)
                .and()
                // token鉴权
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

    }


    /**
     * 自定义用户名和密码有2种方式，一种是在代码中写死 另一种是使用数据库
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

}
