package org.mall.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.mall.product.entity.Attr;

/**
 * @author sxs
 * @since 2023/1/23
 */
@Schema(name = "AttrDTO")
@Getter
@Setter
public class AttrDTO extends Attr {
    @Schema(name = "属性分组id")
//    @NotNull(message = "属性分组id不能为空", groups = {AddGroup.class})
    private Long attrGroupId;

    @Schema(name = "所属分类名")
    private String catelogName;
    @Schema(name = "所属属性分组名")
    private String groupName;
    @Schema(name = "分类的完整路径")
    private Long[] catelogPath;


}
