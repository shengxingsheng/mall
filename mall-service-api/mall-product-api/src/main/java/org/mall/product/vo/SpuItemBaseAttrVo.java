package org.mall.product.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class SpuItemBaseAttrVo {
    private String groupName;
    private List<SpuBaseAttrVo> attrs;
}