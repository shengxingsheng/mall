package org.mall.order.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sxs
 * @since 2023/3/3
 */
@EnableFeignClients("org.mall.order.feign")
public class FeignAutoConfig {
}
