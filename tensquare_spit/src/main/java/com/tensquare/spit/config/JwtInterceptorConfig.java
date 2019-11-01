package com.tensquare.spit.config;

import com.tensquare.spit.Interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Configuration
public class JwtInterceptorConfig  extends WebMvcConfigurationSupport {

    @Resource
    private JwtInterceptor jwtInterceptor;

    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/spit/search/**");
//   .excludePathPatterns("/**");
    }

}
