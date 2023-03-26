package org.mall.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author sxs
 * @since 2023/2/11
 */
@Configuration
public class ThreadConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadConfigProperties properties) {
       return new ThreadPoolExecutor(
               properties.getCoreSize(),
               properties.getMaxSize(),
               properties.getKeepAliveTime(),
               TimeUnit.SECONDS,
               new LinkedBlockingDeque<>(100000),
               Executors.defaultThreadFactory(),
               new ThreadPoolExecutor.AbortPolicy()
       );
    }
}
