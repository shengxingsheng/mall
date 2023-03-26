package org.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.product.entity.Brand;

/**
 * <p>
 * 品牌 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
public interface BrandService extends IService<Brand> {

    /**
     * 分页查询
     * @return
     */
    PageResult page(PageQuery pageQuery);

    /**
     * 更新品牌和级联信息
     * @param brand
     */
    void updateBrandAndCascade(Brand brand);
}
