package org.mall.product.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sxs
 * @since 2023/1/10
 */
@EnableFeignClients("org.mall.product.feign")
public class FeignAutoConfiguration {
}
