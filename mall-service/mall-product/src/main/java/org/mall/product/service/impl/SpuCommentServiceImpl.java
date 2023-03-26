package org.mall.product.service.impl;

import org.mall.product.entity.SpuComment;
import org.mall.product.mapper.SpuCommentMapper;
import org.mall.product.service.SpuCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 商品评价 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SpuCommentServiceImpl extends ServiceImpl<SpuCommentMapper, SpuComment> implements SpuCommentService {

}
