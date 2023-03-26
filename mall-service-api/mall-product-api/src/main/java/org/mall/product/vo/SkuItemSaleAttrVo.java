package org.mall.product.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class SkuItemSaleAttrVo {
    private String attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVO> attrValues;
}