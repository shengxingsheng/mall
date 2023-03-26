package org.mall.order.controller.front;

import com.alipay.api.AlipayApiException;
import org.mall.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sxs
 * @since 2023/3/4
 */
@Controller
public class PayController {
    private final OrderService orderService;

    public PayController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ResponseBody
    @GetMapping(value = "/aliPayOrder",produces = "text/html")
    public String aliPayOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {
        return orderService.aliPayOrder(orderSn);

    }
}
