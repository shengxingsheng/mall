package org.mall.ware.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sxs
 * @since 2023/1/10
 */
@EnableFeignClients("org.mall.ware.feign")
public class FeignAutoConfiguration {
}
