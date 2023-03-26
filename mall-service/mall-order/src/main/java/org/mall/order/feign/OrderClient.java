package org.mall.order.feign;

import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.order.entity.Order;
import org.mall.order.entity.PaymentInfo;
import org.mall.order.service.OrderService;
import org.mall.order.service.PaymentInfoService;
import org.mall.order.vo.OrderVO;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sxs
 * @since 2023/3/3
 */
@RestController
public class OrderClient implements IOrderClient{
    private final OrderService orderService;
    private final PaymentInfoService paymentInfoService;

    public OrderClient(OrderService orderService, PaymentInfoService paymentInfoService) {
        this.orderService = orderService;
        this.paymentInfoService = paymentInfoService;
    }

    @Override
    public ResponseEntity<Order> getOrderByOrderSn(String orderSn) {
        Order order = orderService.getByOrderSn(orderSn);
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<PageResult<OrderVO>> getOrderPageByMemberId(Long id, PageQuery pageQuery) {
        PageResult<OrderVO> result = orderService.getOrderPageByMemberId(id, pageQuery);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<PaymentInfo> getPayment(String orderSn) {
        return ResponseEntity.ok(paymentInfoService.getByOrderSn(orderSn));
    }

}
