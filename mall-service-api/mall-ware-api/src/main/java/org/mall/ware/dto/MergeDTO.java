package org.mall.ware.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author sxs
 * @since 2023/1/26
 */
@Setter
@Getter
@ToString
@Schema(name = "MergeDTO",description = "合并整单接收对象")
public class MergeDTO {

    @NotEmpty(message = "采购项id不能为空")
    private List<Long> items;
    private Long purchaseId;
}
