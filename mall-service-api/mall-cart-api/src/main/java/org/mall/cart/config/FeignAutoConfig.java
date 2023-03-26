package org.mall.cart.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sxs
 * @since 2023/2/19
 */
@EnableFeignClients("org.mall.cart.feign")
public class FeignAutoConfig {
}
