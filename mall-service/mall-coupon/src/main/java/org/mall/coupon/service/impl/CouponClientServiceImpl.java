package org.mall.coupon.service.impl;

import org.mall.coupon.dto.SpuSkuDTO;
import org.mall.coupon.entity.MemberPrice;
import org.mall.coupon.entity.SkuFullReduction;
import org.mall.coupon.entity.SkuLadder;
import org.mall.coupon.entity.SpuBounds;
import org.mall.coupon.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author sxs
 * @since 2023/1/25
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CouponClientServiceImpl implements CouponClientService {
    private final SpuBoundsService spuBoundsService;
    private final SkuLadderService skuLadderService;
    private final SkuFullReductionService skuFullReductionService;
    private final MemberPriceService memberPriceService;

    public CouponClientServiceImpl(SpuBoundsService spuBoundsService, SkuLadderService skuLadderService, SkuFullReductionService skuFullReductionService, MemberPriceService memberPriceService) {
        this.spuBoundsService = spuBoundsService;
        this.skuLadderService = skuLadderService;
        this.skuFullReductionService = skuFullReductionService;
        this.memberPriceService = memberPriceService;
    }

    @Override
    public void saveBySpuSku(SpuSkuDTO spuSkuDTO) {
        Optional<SpuBounds> boundsOptional = Optional.ofNullable(spuSkuDTO.getSpuBounds());
        boundsOptional.ifPresent(spuBoundsService::save);
        List<SkuLadder> skuLadders = spuSkuDTO.getSkuLadder();
        if (!CollectionUtils.isEmpty(skuLadders)) {
            skuLadderService.saveBatch(skuLadders);
        }
        List<SkuFullReduction> fullReductions = spuSkuDTO.getSkuFullReduction();
        if (!CollectionUtils.isEmpty(fullReductions)) {
            skuFullReductionService.saveBatch(fullReductions);
        }
        List<MemberPrice> memberPrices = spuSkuDTO.getMemberPrice();
        if (!CollectionUtils.isEmpty(memberPrices)) {
            memberPriceService.saveBatch(memberPrices);
        }
    }
}
