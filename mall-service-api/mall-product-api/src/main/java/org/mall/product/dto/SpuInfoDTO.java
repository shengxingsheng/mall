package org.mall.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;
import org.mall.product.entity.SpuInfo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author sxs
 * @since 2023/1/24
 */
@Getter
@Setter
@ToString
@Schema(name = "SpuInfoDTO", description = "")
public class SpuInfoDTO extends SpuInfo {


    @Schema(description = "商品介绍图片url")
    private List<String> decript;

    @Schema(description = "商品图集图片url")
    private List<String> images;

    @Valid
    @NotNull(message = "积分值不能为空",groups = {AddGroup.class})
    @Schema(description = "积分值")
    private BoundDTO bounds;

    @Valid
    @Schema(description = "规格参数")
    private List<BaseAttrDTO> baseAttrs;

    @Valid
    @Schema(description = "sku集合")
    private List<SkuInfoDTO> skus;





}
