package org.mall.seckill.feign.fallback;

import lombok.extern.slf4j.Slf4j;
import org.mall.common.constant.ErrorCode;
import org.mall.common.pojo.ResponseEntity;
import org.mall.seckill.dto.chache.SeckillSkuInfoDTO;
import org.mall.seckill.feign.ISeckillClient;
import org.springframework.stereotype.Component;

/**
 * @author sxs
 * @since 2023/3/12
 */
@Slf4j
@Component
public class SeckillClientFallback implements ISeckillClient {
    @Override
    public ResponseEntity<SeckillSkuInfoDTO> getSeckillSku(Long skuId) {
        log.error("熔断降级...");
        return ResponseEntity.error(ErrorCode.SYSTEM_CURRENT_LIMITING);
    }

}
