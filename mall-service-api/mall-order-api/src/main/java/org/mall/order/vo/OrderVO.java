package org.mall.order.vo;

import lombok.Data;
import org.mall.order.entity.Order;
import org.mall.order.entity.OrderItem;

import java.util.List;

/**
 * @author sxs
 * @since 2023/3/5
 */
@Data
public class OrderVO extends Order {
    private List<OrderItem> orderItemList;
}
