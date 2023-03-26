package org.mall.seckill.config;

import org.mall.seckill.interceptor.SeckillInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author sxs
 * @since 2023/3/10
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SeckillInterceptor()).addPathPatterns("/**");
    }
}
