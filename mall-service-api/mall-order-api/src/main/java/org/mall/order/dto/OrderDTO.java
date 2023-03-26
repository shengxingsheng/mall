package org.mall.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.order.entity.Order;
import org.mall.order.entity.OrderItem;

import java.util.List;

/**
 * @author sxs
 * @since 2023/2/22
 */
@Getter
@Setter
@ToString
@Schema(description = "订单生成")
public class OrderDTO {
    private Order order;
    private List<OrderItem> orderItems;
}
