package org.mall.product.mapper;

import org.mall.product.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品三级分类 Mapper 接口
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
