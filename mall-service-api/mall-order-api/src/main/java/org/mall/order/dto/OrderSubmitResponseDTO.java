package org.mall.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.order.entity.Order;

/**
 * @author sxs
 * @since 2023/2/21
 */
@Setter
@Getter
@ToString
public class OrderSubmitResponseDTO {

    private Order order;
    @Schema(description = "0成功，2验价失败 ,3锁定库存失败")
    private Integer code;
}
