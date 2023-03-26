package org.mall.search.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sxs
 * @since 2023/1/10
 */
@EnableFeignClients(basePackages = "org.mall.search.feign")
public class FeignAutoConfiguration {
}
