package org.mall.ware.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author sxs
 * @since 2023/1/26
 */
@Setter
@Getter
@ToString
@Schema(name = "PurchaseDoneDTO",description = "完成采购请求接收对象")
public class PurchaseDoneDTO {
    @NotNull(message = "采购单id不能为空")
    @Schema(description = "采购单id")
    private Long id;

    @NotEmpty(message = "采购项不能为空")
    private List<@Valid PurchaseItemDoneDTO> items;
}

