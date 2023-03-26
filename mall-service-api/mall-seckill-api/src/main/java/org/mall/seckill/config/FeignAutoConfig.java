package org.mall.seckill.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sxs
 * @since 2023/3/12
 */
@EnableFeignClients("org.mall.seckill.feign")
@ComponentScan("org.mall.seckill.feign.fallback")
public class FeignAutoConfig {
}
