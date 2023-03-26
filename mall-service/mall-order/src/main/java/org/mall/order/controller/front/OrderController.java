package org.mall.order.controller.front;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.mall.common.constant.ErrorCode;
import org.mall.common.exception.BusinessException;
import org.mall.order.dto.OrderConfirmDTO;
import org.mall.order.dto.OrderSubmitDTO;
import org.mall.order.dto.OrderSubmitResponseDTO;
import org.mall.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

/**
 * @author sxs
 * @since 2023/2/18
 */
@Slf4j
@Controller("frontOrderController")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmDTO orderConfirmDTO = orderService.getConfirmOrder();
        model.addAttribute("confirmOrderData", orderConfirmDTO);
        return "confirm";
    }

    @Operation(summary = "下单功能")
    @PostMapping("/submitOrder")
    public String submitOrder(@Validated OrderSubmitDTO orderSubmitDTO, Model model, RedirectAttributes redirectAttributes) {
        OrderSubmitResponseDTO orderSubmitResponse = new OrderSubmitResponseDTO();
        try {
            orderSubmitResponse = orderService.submitOrder(orderSubmitDTO);
        } catch (BusinessException e) {
            ErrorCode errorCode = e.getErrorCode();
            if (errorCode.equals(ErrorCode.QUANTITY_EXCEEDS_LIMIT)) {
                orderSubmitResponse.setCode(3);
            } else {
                orderSubmitResponse.setCode(4);
            }
            log.error("{},{}",e.getErrorCode(),e.getMsg());
        } catch (Exception e) {
            orderSubmitResponse.setCode(4);
            log.error(e.getMessage());
        } finally {
            if (orderSubmitResponse.getCode() == 0) {
                //成功
                model.addAttribute("submitOrderResp", orderSubmitResponse);
                return "pay";
            } else {
                String msg = "下单失败:";
                switch (orderSubmitResponse.getCode()) {
                    case 1:
                        msg += "订单信息过期,请刷新提交";
                        break;
                    case 2:
                        msg += "订单商品价格发生变化,请确认后提交";
                        break;
                    case 3:
                        msg += "商品库存不足";
                        break;
                    case 4:
                        msg += "系统繁忙,请稍后重试";
                        break;
                }
                redirectAttributes.addFlashAttribute("msg", msg);
                return "redirect:http://order.mall.org/toTrade";
            }
        }


    }
}
