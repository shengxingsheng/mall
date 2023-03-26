package org.mall.product.service.impl;

import org.mall.product.entity.AttrAttrgroupRelation;
import org.mall.product.mapper.AttrAttrgroupRelationMapper;
import org.mall.product.service.AttrAttrgroupRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 属性&属性分组关联 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationMapper, AttrAttrgroupRelation> implements AttrAttrgroupRelationService {

}
