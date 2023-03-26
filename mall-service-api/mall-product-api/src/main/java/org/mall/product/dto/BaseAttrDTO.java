package org.mall.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.ListValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Schema(name = "BaseAttr", description = "规格参数")
public class BaseAttrDTO {

    @NotNull(message = "属性id不能为空",groups = {AddGroup.class})
    @Schema(description = "属性id")
    private Long attrId;

    @NotBlank(message = "属性值不能为空",groups = {AddGroup.class})
    @JsonProperty("attrValues")
    @Schema(description = "属性值")
    private String attrValue;


    @JsonProperty("showDesc")
    @NotNull(message = "快速展示的值不能为空",groups = {AddGroup.class})
    @ListValue(vals = {0, 1}, message = "快速展示的值只能是0或1", groups = {AddGroup.class})
    @Schema(description = "快速展示【是否展示在介绍上；0-否 1-是】")
    private Byte quickShow;

}