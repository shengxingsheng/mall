package org.mall.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author sxs
 * @since 2023/2/21
 */
@Setter
@Getter
@ToString
public class OrderSubmitDTO {
    @NotNull(message = "收货地址不能为空")
    @Schema(description = "收货地址id")
    private Long addrId;
    @Schema(description = "支付类型")
    private String payType;
    @NotBlank(message = "防重令牌不能为空")
    @Schema(description = "防重令牌")
    private String orderToken;
    @Schema(description = "应付价格")
    private BigDecimal payPrice;
    @Schema(description = "备注")
    private String note;
}
