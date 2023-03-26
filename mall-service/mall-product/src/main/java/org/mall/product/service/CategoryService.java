package org.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.product.dto.Catalog2DTO;
import org.mall.product.dto.CategoryDTO;
import org.mall.product.entity.Category;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
public interface CategoryService extends IService<Category> {

    /**
     * 获取三级分类列表
     * @return 分类集合
     */
    List<CategoryDTO> listCategoryTree();

    /**
     * 根据id删除批量分类
     * @param catIds 分类id数组
     */
    void deleteCategoryByIds(Long[] catIds);

    /**
     * 添加一个分类
     * @param category
     */
    void addCategory(Category category);

    /**
     *
     * @param catId
     * @return
     */
    Category getCategoryById(Long catId);


    void updateCategorySort(Category[] category);

    /**
     * 获取catelogId的完整路径
     * @param catelogId
     * @return
     */
    Long[] findCatelogPath(Long catelogId);

    /**
     * 更新分类和级联
     * @param category
     */
    void updateCategoryAndCascade(Category category);

    /**
     * 获取1极分类
     * @return
     */
    List<Category> getLevel1();

    /**
     * web端 目录
     * @return
     */
    Map<String, List<Catalog2DTO>> getCatalog();
}
