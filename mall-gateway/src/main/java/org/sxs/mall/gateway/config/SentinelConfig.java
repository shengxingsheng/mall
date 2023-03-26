package org.sxs.mall.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import org.mall.common.constant.ErrorCode;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.util.JsonUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author sxs
 * @since 2023/3/12
 */
@Configuration
public class SentinelConfig {

    public SentinelConfig() {
        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
                ResponseEntity entity = ResponseEntity.error(ErrorCode.SYSTEM_CURRENT_LIMITING, "请求过于频繁");
                String json = JsonUtil.classToJson(entity);
                Mono<ServerResponse> mono = ServerResponse.ok().body(Mono.just(json), String.class);
                return mono;
            }
        });
    }
}
