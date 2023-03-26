package org.mall.ware.service.impl;

import org.mall.ware.entity.WareOrderTask;
import org.mall.ware.mapper.WareOrderTaskMapper;
import org.mall.ware.service.WareOrderTaskService;
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
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskMapper, WareOrderTask> implements WareOrderTaskService {

}
