package org.mall.ware.service.impl;

import org.mall.ware.entity.WareOrderTaskDetail;
import org.mall.ware.mapper.WareOrderTaskDetailMapper;
import org.mall.ware.service.WareOrderTaskDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 库存工作单 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WareOrderTaskDetailServiceImpl extends ServiceImpl<WareOrderTaskDetailMapper, WareOrderTaskDetail> implements WareOrderTaskDetailService {

}
