package org.mall.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.mall.product.entity.Category;

import java.util.List;

/**
 * @author sxs
 * @since 2023/1/13
 */
@Getter
@Setter
@Schema(name = "CategoryDTO", description = "")
public class CategoryDTO extends Category {
    @Schema(description = "分类的子分类")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryDTO> child;

    @Override
    public String toString() {
        return super.toString()+"CategoryDTO{" +
                "child=" + child +
                '}';
    }
}
