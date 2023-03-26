package org.mall.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.mall.product.entity.Attr;
import org.mall.product.entity.AttrGroup;

import java.io.Serializable;
import java.util.List;

/**
 * @author sxs
 * @since 2023/1/24
 */
@Setter
@Getter
@Schema(name = "AttrGroupWithAttrVo")
public class AttrGroupWithAttrDTO extends AttrGroup implements Serializable {

    @Schema(description = "属性")
    private List<Attr> attrs;
}
