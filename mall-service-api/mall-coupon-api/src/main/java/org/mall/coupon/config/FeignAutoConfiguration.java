package org.mall.coupon.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sxs
 * @since 2023/1/10
 */
@EnableFeignClients("org.mall.coupon.feign")
public class FeignAutoConfiguration {
}
