package org.mall.product.service.impl;

import org.mall.product.entity.SkuImages;
import org.mall.product.mapper.SkuImagesMapper;
import org.mall.product.service.SkuImagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * sku图片 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesMapper, SkuImages> implements SkuImagesService {

}
