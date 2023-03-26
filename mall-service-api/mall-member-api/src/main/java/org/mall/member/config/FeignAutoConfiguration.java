package org.mall.member.config;

import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author sxs
 * @since 2023/1/10
 */
@EnableFeignClients("org.mall.member.feign")
public class FeignAutoConfiguration {
}
