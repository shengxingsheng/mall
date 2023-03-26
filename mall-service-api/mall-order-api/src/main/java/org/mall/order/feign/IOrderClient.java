package org.mall.order.feign;

import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.order.entity.Order;
import org.mall.order.entity.PaymentInfo;
import org.mall.order.vo.OrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author sxs
 * @since 2023/3/3
 */
@FeignClient("mall-order")
public interface IOrderClient {
    /**
     * 根据orderSn获取order
     * @param orderSn
     * @return
     */
    @GetMapping("/feign/order/{orderSn}")
    ResponseEntity<Order> getOrderByOrderSn(@PathVariable("orderSn") String orderSn);

    /**
     * 获取order列表
     * @param memberId
     * @param pageQuery
     * @return
     */
    @PostMapping("/feign/order/{memberId}")
    ResponseEntity<PageResult<OrderVO>> getOrderPageByMemberId(@PathVariable("memberId") Long memberId, @RequestBody PageQuery pageQuery);
    @PostMapping("/feign/order/paymentInfo/{orderSn}")
    ResponseEntity<PaymentInfo> getPayment(@PathVariable("orderSn") String orderSn);
}
