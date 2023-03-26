package org.mall.cart.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author sxs
 * @since 2023/2/11
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mall.thread")
public class ThreadConfigProperties {
    private Integer coreSize = 20;
    private Integer maxSize = 200;
    /**
     * 单位秒
     */
    private Integer keepAliveTime = 10;
}
