package org.mall.coupon.service;

import org.mall.coupon.dto.SpuSkuDTO;

/**
 * @author sxs
 * @since 2023/1/25
 */

public interface CouponClientService {
    void saveBySpuSku(SpuSkuDTO spuSkuDTO);
}
