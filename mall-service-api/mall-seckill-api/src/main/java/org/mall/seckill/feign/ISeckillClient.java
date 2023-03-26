package org.mall.seckill.feign;

import org.mall.common.pojo.ResponseEntity;
import org.mall.seckill.dto.chache.SeckillSkuInfoDTO;
import org.mall.seckill.feign.fallback.SeckillClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author sxs
 * @since 2023/3/8
 */
@FeignClient(value = "mall-seckill",fallback = SeckillClientFallback.class)
public interface ISeckillClient {

    @GetMapping("/feign/seckill/getSeckillSku/{skuId}")
    ResponseEntity<SeckillSkuInfoDTO> getSeckillSku(@PathVariable("skuId") Long skuId);
}
