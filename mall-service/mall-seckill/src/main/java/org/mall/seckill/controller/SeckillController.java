package org.mall.seckill.controller;

import org.mall.common.pojo.ResponseEntity;
import org.mall.seckill.dto.chache.SeckillSkuInfoDTO;
import org.mall.seckill.service.SeckillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author sxs
 * @since 2023/3/8
 */
@Controller
public class SeckillController {
    private final SeckillService seckillService;

    public SeckillController(SeckillService seckillService) {
        this.seckillService = seckillService;
    }

    @GetMapping("/seckill/skus")
    @ResponseBody
    public ResponseEntity<List<SeckillSkuInfoDTO>> getSeckillSkus() {
        List<SeckillSkuInfoDTO> list = seckillService.getCurrentSeckillSkus();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/kill")
    public String kill(@RequestParam String killId,
                       @RequestParam String key,
                       @RequestParam Integer num, Model model) {
        String orderSn = seckillService.kill(killId,key,num);
        model.addAttribute("orderSn",orderSn);
        return "success";
    }
}
