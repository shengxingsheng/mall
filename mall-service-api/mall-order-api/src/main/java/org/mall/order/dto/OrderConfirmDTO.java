package org.mall.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.cart.dto.CartItemDTO;
import org.mall.member.entity.MemberReceiveAddress;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author sxs
 * @since 2023/2/18
 */
@Getter
@Setter
@ToString
@Schema(description = "订单所需的数据")
public class OrderConfirmDTO {
    @Schema(description = "收获地址列表")
    private List<MemberReceiveAddress> address;
    @Schema(description = "购物项列表")
    private List<CartItemDTO> items;
    @Schema(description = "商品库存状态")
    private Map<Long,Boolean> stocks;
    @Schema(description = "积分")
    private Integer integration;
    @Schema(description = "订单总额")
    private BigDecimal totalPrice;
    @Schema(description = "应付价格")
    private BigDecimal payPrice;
    @Schema(description = "防重令牌")
    private String orderToken ;

    public Integer getCount() {
        int count = 0;
        if (!CollectionUtils.isEmpty(items)) {
            for (CartItemDTO item : items) {
                count += item.getCount();
            }
        }
        return count;
    }
    public BigDecimal getTotalPrice() {
        totalPrice = new BigDecimal(0);
        if (!CollectionUtils.isEmpty(items)) {
            for (CartItemDTO item : items) {
                totalPrice = totalPrice.add(item.getTotalPrice());
            }
        }
        return totalPrice;
    }
    public BigDecimal getPayPrice() {
        return getTotalPrice();
    }
}
