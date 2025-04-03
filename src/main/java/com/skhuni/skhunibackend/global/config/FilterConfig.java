package com.skhuni.skhunibackend.global.config;

import com.skhuni.skhunibackend.global.filter.LoginTokenCheckFilter;
import com.skhuni.skhunibackend.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public FilterRegistrationBean<LoginTokenCheckFilter> loginTokenCheckFilter() {
        FilterRegistrationBean<LoginTokenCheckFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginTokenCheckFilter(tokenProvider));
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

}
