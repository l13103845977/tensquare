package com.tensquare.qa.config;

import com.tensquare.qa.Interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Configuration
public class JwtInterceptorConfig extends WebMvcConfigurationSupport {

    @Resource
    private JwtInterceptor jwtInterceptor;
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/qa/search/**","/**/reply/search/**");

    }

}
