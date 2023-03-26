package org.mall.product.service;

import org.mall.product.entity.ProductAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
public interface ProductAttrValueService extends IService<ProductAttrValue> {

    /**
     * 通过spuId获取
     * @param spuId
     * @return
     */
    List<ProductAttrValue> getBySpuId(Long spuId);

    void updateBySpuId(Long spuId, List<ProductAttrValue> attrList);
}
