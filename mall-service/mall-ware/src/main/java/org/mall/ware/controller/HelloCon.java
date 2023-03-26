package org.mall.ware.controller;

import org.mall.common.pojo.ResponseEntity;
import org.mall.ware.config.AlipayTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sxs
 * @since 2023/3/11
 */
@RestController
public class HelloCon {
    @Autowired
    private AlipayTemplate alipayTemplate;
    @GetMapping("ali/query")
    public ResponseEntity query(@RequestParam String orderSn,@RequestParam String tradeNo) {
       String query = alipayTemplate.query(orderSn,tradeNo);
       return ResponseEntity.ok(query);
    }
}
