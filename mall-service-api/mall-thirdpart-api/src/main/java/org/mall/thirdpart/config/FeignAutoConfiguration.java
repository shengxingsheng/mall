package org.mall.thirdpart.config;


import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sxs
 * @since 2023/1/10
 */
@EnableFeignClients("org.mall.thirdpart.feign")
public class FeignAutoConfiguration {
}
