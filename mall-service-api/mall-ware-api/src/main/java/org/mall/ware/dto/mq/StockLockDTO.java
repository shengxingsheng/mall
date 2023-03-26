package org.mall.ware.dto.mq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author sxs
 * @since 2023/3/3
 */
@Getter
@Setter
@ToString
@Schema(description = "库存锁定" )
public class StockLockDTO {
    @Schema(description = "库存任务单id")
    private Long taskId;
    @Schema(description = "库存任务详情id集合")
    private List<Long> detailIds;
}
