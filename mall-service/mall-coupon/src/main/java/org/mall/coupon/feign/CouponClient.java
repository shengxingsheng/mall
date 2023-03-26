package org.mall.coupon.feign;

import io.swagger.v3.oas.annotations.Operation;
import org.mall.common.pojo.ResponseEntity;
import org.mall.coupon.dto.SeckillSessionDTO;
import org.mall.coupon.dto.SpuSkuDTO;
import org.mall.coupon.service.CouponClientService;
import org.mall.coupon.service.SeckillSessionService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sxs
 * @since 2023/1/25
 */
@RestController
public class CouponClient implements ICouponClient {
    private final CouponClientService couponClientService;
    private final SeckillSessionService seckillSessionService;

    public CouponClient(CouponClientService couponClientService, SeckillSessionService seckillSessionService) {
        this.couponClientService = couponClientService;
        this.seckillSessionService = seckillSessionService;
    }

    @Override
    public ResponseEntity<Void> saveBySpuSku(SpuSkuDTO spuSkuDTO) {
        couponClientService.saveBySpuSku(spuSkuDTO);
        return ResponseEntity.ok();
    }

    @Operation(summary = "获取最近3天的场次")
    @Override
    public ResponseEntity<List<SeckillSessionDTO>> getLatest3DaySession() {
        return ResponseEntity.ok(seckillSessionService.getLatest3DaySession());
    }
}
