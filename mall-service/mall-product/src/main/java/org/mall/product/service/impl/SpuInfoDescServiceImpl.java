package org.mall.product.service.impl;

import org.mall.product.entity.SpuInfoDesc;
import org.mall.product.service.SpuInfoDescService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * spu信息介绍 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SpuInfoDescServiceImpl extends ServiceImpl<org.mall.product.mapper.SpuInfoDescMapper, SpuInfoDesc> implements SpuInfoDescService {

}
