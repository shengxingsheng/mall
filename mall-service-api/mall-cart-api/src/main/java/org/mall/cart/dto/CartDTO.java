package org.mall.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sxs
 * @since 2023/2/16
 */
@ToString
@Schema(description = "购物车")
public class CartDTO {
    @Schema(description = "购物项")
    private List<CartItemDTO> items;
    @Schema(description = "商品数量")
    private Integer countNum;
    @Schema(description = "商品类型")
    private Integer countType;
    @Schema(description = "商品总价")
    private BigDecimal totalAmount;
    @Schema(description = "减免价格")
    private BigDecimal reduce = new BigDecimal(0);

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int count = 0;
        if (!CollectionUtils.isEmpty(this.items)) {
            for (CartItemDTO cartItem : items) {
                count += cartItem.getCount();
            }
        }
        return count;
    }

    public Integer getCountType() {
        int count = 0;
        if (!CollectionUtils.isEmpty(this.items)) {
            for (CartItemDTO cartItem : items) {
                count += 1;
            }
        }
        return count;
    }


    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal(0);
        if (!CollectionUtils.isEmpty(this.items)) {
            for (CartItemDTO cartItem : items) {
                if (cartItem.getCheck()) {
                    amount = amount.add(cartItem.getTotalPrice());
                }
            }
        }
        return amount.subtract(this.getReduce());
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
