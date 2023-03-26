package org.mall.seckill.feign;

import org.mall.common.pojo.ResponseEntity;
import org.mall.seckill.dto.chache.SeckillSkuInfoDTO;
import org.mall.seckill.service.SeckillService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sxs
 * @since 2023/3/8
 */
@RestController
public class SeckillClient implements ISeckillClient{
    private final SeckillService seckillService;

    public SeckillClient(SeckillService seckillService) {

        this.seckillService = seckillService;
    }

    @Override
    public ResponseEntity<SeckillSkuInfoDTO> getSeckillSku(Long skuId)  {
        return ResponseEntity.ok(seckillService.getSeckillSku(skuId));
    }

}
