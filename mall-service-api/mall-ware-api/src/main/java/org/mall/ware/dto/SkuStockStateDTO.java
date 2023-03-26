package org.mall.ware.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author sxs
 * @since 2023/1/29
 */
@Setter
@Getter
@ToString
@Schema(name = "SkuStockStateDTO", description = "sku库存状态")
public class SkuStockStateDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Schema(description = "skuId")
    private Long skuId;
    @Schema(description = "是否有库存")
    private Boolean hasStock;

}
