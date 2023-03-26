package org.mall.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author sxs
 * @since 2023/1/24
 */
@Getter
@Setter
@ToString
@Schema(name = "BrandDTO",description = "品牌名字")
public class BrandDTO implements Serializable {
    @Schema(description = "品牌id")
    private Long brandId;
    @Schema(description = "品牌名字")
    private String brandName;
}
