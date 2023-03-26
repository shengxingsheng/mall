package org.mall.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberpriceDTO {
    @NotNull(message = "会员等级id不能为空",groups = {AddGroup.class})
    @Schema(description = "会员等级id")
    private Long id;
    @NotNull(message = "会员等级名字不能为空",groups = {AddGroup.class})
    @Schema(description = "会员等级名字")
    private String name;
    @NotNull(message = "会员价格不能为空",groups = {AddGroup.class})
    @Min(value = 0,message = "会员价格不能为负",groups = {AddGroup.class})
    @Schema(description = "会员价格")
    private BigDecimal price;
}