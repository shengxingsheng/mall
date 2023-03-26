package org.mall.ware.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sxs
 * @since 2023/2/23
 */
@Setter
@Getter
@ToString
public class LockStockResultDTO {
    private Long skuId;
    private Integer num;
    private Boolean locked;

}
