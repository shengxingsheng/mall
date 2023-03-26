package org.mall.product.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sxs
 * @since 2023/2/11
 */
@Setter
@Getter
@ToString
public class AttrValueWithSkuIdVO {
    private String attrValue;
    private String skuIds;
}
