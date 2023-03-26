package org.mall.product.service;

import org.mall.product.entity.CategoryBrandRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.product.dto.BrandDTO;

import java.util.List;

/**
 * <p>
 * 品牌分类关联 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelation> {

    /**
     * 获取关联通过品牌id
     * @param brandId
     * @return
     */
    List<CategoryBrandRelation> listByBrandId(Long brandId);

    /**
     * 保存关系
     * @param categoryBrandRelation
     */
    void saveRelation(CategoryBrandRelation categoryBrandRelation);

    /**
     * 更新品牌名字
     * @param brandId
     * @param name
     */
    void updateBrandName(Long brandId, String name);

    /**
     * 更新分类名字
     * @param catId
     * @param name
     */
    void updateCatelogName(Long catId, String name);

    List<CategoryBrandRelation> getByCatId(Long catId);

    List<BrandDTO> getBrandNameByCatId(Long catId);
}
