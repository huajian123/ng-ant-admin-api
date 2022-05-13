package com.demo.app.config.webmvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 类功能描述:　CorsConfig
 *
 * @author Eternal
 * @date 2019-11-26 15:11
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${spring.servlet.multipart.location}")
    private String path;

    @Value("${fbl.uploadPath}")
    private String uploadPath;


    /**
     * 跨域配置
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
                // 是否允许发送Cookie
                .allowCredentials(true)
                .allowedHeaders("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态文件
        registry.addResourceHandler("**").addResourceLocations("classpath:/static/");
        // swagger
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        // 上传文件
        registry.addResourceHandler(uploadPath + "**").addResourceLocations("file:/" + path);
    }
}
