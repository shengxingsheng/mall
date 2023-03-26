package org.mall.ware.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author sxs
 * @since 2023/2/23
 */
@Setter
@Getter
@ToString
public class LockItemDTO {
    private Long skuId;
    private Integer num;
    private List<Long> wareIds;
}
