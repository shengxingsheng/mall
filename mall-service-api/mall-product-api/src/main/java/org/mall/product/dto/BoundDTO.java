package org.mall.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Schema(name = "Bound", description = "积分值")
public class BoundDTO {

    @NotNull(message = "金币值不能为空", groups = {AddGroup.class})
    @Min(value = 0, message = "金币值不能为负", groups = {AddGroup.class})
    @Schema(description = "金币值")
    private BigDecimal buyBounds;

    @NotNull(message = "成长值不能为空", groups = {AddGroup.class})
    @Min(value = 0, message = "成长值不能为负", groups = {AddGroup.class})
    @Schema(description = "成长值")
    private BigDecimal growBounds;

}