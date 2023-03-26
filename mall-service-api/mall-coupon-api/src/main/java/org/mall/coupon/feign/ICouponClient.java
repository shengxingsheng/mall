package org.mall.coupon.feign;

import org.mall.common.pojo.ResponseEntity;
import org.mall.coupon.dto.SeckillSessionDTO;
import org.mall.coupon.dto.SpuSkuDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author sxs
 * @since 2023/1/25
 */
@FeignClient("mall-coupon")
public interface ICouponClient {

    @PostMapping("/feign/coupon/spusku")
    ResponseEntity<Void> saveBySpuSku(@RequestBody SpuSkuDTO spuSkuDTO);
    @GetMapping("/feign/coupon/seckillSession/latest3Day")
    ResponseEntity<List<SeckillSessionDTO>> getLatest3DaySession();
}
