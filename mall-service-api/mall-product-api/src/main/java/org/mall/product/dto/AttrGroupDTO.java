package org.mall.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.mall.product.entity.AttrGroup;

import java.util.Arrays;

/**
 * @author sxs
 * @since 2023/1/22
 */
@Schema(name = "AttrGroupDTO",description = "" )
@Getter
@Setter
public class AttrGroupDTO extends AttrGroup{
    @Schema(name = "分类的完整路径")
    private Long[] catelogPath;

    @Override
    public String toString() {
        return super.toString()+"AttrGroupDTO{" +
                "catelogPath=" + Arrays.toString(catelogPath) +
                '}';
    }
}
