package org.mall.auth.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sxs
 * @since 2023/1/10
 */
@EnableFeignClients("org.mall.auth.feign")
public class FeignAutoConfiguration {
}
