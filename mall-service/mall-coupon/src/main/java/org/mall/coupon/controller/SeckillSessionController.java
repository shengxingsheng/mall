package org.mall.coupon.controller;

import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.coupon.entity.SeckillSession;
import org.mall.coupon.service.SeckillSessionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 秒杀活动场次 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@RestController
@RequestMapping("/coupon/seckillsession")
public class SeckillSessionController {

    private final SeckillSessionService seckillSessionService;

    public SeckillSessionController(SeckillSessionService seckillSessionService) {
        this.seckillSessionService = seckillSessionService;
    }


    @GetMapping("/list")
    public ResponseEntity<PageResult> list(@Validated PageQuery pageQuery) {
        return ResponseEntity.ok(seckillSessionService.getPage(pageQuery));
    }
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody @Validated SeckillSession seckillSession) {
        seckillSessionService.save(seckillSession);
        return ResponseEntity.ok();
    }
}
