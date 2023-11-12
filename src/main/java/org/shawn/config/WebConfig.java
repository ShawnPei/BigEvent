package org.shawn.config;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.shawn.intercptors.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 无需拦截的接口集合
        List<String> ignorePath = new ArrayList<>();
        // swagger
        ignorePath.add("/swagger-resources/**");
        ignorePath.add("/doc.html");
        ignorePath.add("/v3/**");
        ignorePath.add("/webjars/**");
        ignorePath.add("/springdoc/**");
        ignorePath.add("/static/**");
        ignorePath.add("/templates/**");
        ignorePath.add("/error");
        ignorePath.add("/cipher/check");
        ignorePath.add("/manager/login");
        ignorePath.add("/swagger-ui.html");
        ignorePath.add("/user/login");
        ignorePath.add("/user/register");

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(ignorePath);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }



}
