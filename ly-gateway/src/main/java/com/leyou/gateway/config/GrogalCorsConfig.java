package com.leyou.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
* @author: furong
* @date: 2019/4/6
* @description:
*/

@Configuration
public class GrogalCorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        //添加cors配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许的域,不要写*,否者cookie无法使用
        corsConfiguration.addAllowedOrigin("http://manage.leyou.com");
        //是否发送cookie信息
        corsConfiguration.setAllowCredentials(true);
        //允许请求的方式
        corsConfiguration.addAllowedMethod("OPTIONS");
        corsConfiguration.addAllowedMethod("HEAD");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("PATCH");
        // 允许的头信息
        corsConfiguration.addAllowedHeader("*");
        //有效时间
        corsConfiguration.setMaxAge(3600L);
        //添加映射路径,拦截一切请求
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        //返回新的CorsFilter
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
