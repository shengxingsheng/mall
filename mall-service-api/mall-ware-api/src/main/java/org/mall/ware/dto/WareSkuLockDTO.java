package org.mall.ware.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @author sxs
 * @since 2023/2/23
 */
@Setter
@Getter
@ToString
public class WareSkuLockDTO {
    @Schema(description = "订单号")
    private String orderSn;
    @Schema(description = "锁定项")
    private List<LockItemDTO> locks;
}
