package org.mall.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author sxs
 * @since 2023/2/16
 */
@Setter
@Getter
@ToString
@Schema(description = "购物项")
public class CartItemDTO {
    private Long skuId;
    private Boolean check = true;
    private String title;
    private String image;
    private List<String> skuAttrs;
    private BigDecimal price;
    private Integer count;
    private BigDecimal totalPrice;


    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getSkuAttrs() {
        return skuAttrs;
    }

    public void setSkuAttrs(List<String> skuAttrs) {
        this.skuAttrs = skuAttrs;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getTotalPrice() {
        return this.price.multiply(new BigDecimal(this.count));
    }

    public void setTotalPrice(BigDecimal totalPrice) {

        this.totalPrice = totalPrice;
    }
}
