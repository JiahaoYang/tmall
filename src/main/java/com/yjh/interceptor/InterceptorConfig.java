package com.yjh.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/fore*")
                .excludePathPatterns("/foreHome", "/foreRegister", "/foreLogin",
                        "/foreProduct", "/foreCheckLogin", "/foreLoginAjax",
                        "/foreCategory", "/foreSearch");
/*        registry.addInterceptor(new OtherInterceptor())
                .addPathPatterns("/fore*")
                .excludePathPatterns("/foreRegister", "/foreLogin", "/foreCheckLogin",
                        "/foreLoginAjax");*/
    }
}
