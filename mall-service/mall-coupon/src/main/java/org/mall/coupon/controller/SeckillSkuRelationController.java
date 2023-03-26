package org.mall.coupon.controller;

import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.coupon.entity.SeckillSkuRelation;
import org.mall.coupon.service.SeckillSkuRelationService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 秒杀活动商品关联 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@RestController
@RequestMapping("/coupon/seckillskurelation")
public class SeckillSkuRelationController {

    private final SeckillSkuRelationService seckillSkuRelationService;

    public SeckillSkuRelationController(SeckillSkuRelationService seckillSkuRelationService) {
        this.seckillSkuRelationService = seckillSkuRelationService;
    }

    @GetMapping("/list")
    public ResponseEntity<PageResult<SeckillSkuRelation>> list(PageQuery pageQuery, @RequestParam Long promotionSessionId) {
        return ResponseEntity.ok(seckillSkuRelationService.getPage(pageQuery,promotionSessionId));
    }
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody SeckillSkuRelation seckillSkuRelation) {
        seckillSkuRelationService.save(seckillSkuRelation);
        return ResponseEntity.ok();
    }
}
