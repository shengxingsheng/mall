package org.mall.ware.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.ListValue;
import org.mall.ware.constant.WareConstant;

import javax.validation.constraints.NotNull;

/**
 * @author sxs
 * @since 2023/1/26
 */
@Setter
@Getter
@ToString
public class PurchaseItemDoneDTO {
    @NotNull(message = "采购项id不能为空")
    @Schema(description = "采购项id")
    private Long itemId;

    @NotNull(message = "采购项状态不能为空")
    @ListValue(vals = {WareConstant.PURCHASE_DETAIL_STATUS_COMPLETED, WareConstant.PURCHASE_DETAIL_STATUS_FAIl}, message = "采购项状态只能是完成或失败")
    @Schema(description = "采购项状态")
    private Byte status;

    @Schema(description = "说明")
    private String reason;

}
