package org.mall.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.mall.order.entity.PaymentInfo;
import org.mall.order.mapper.PaymentInfoMapper;
import org.mall.order.service.PaymentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付信息表 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    @Override
    public PaymentInfo getByOrderSn(String orderSn) {
        return this.getOne(Wrappers.<PaymentInfo>lambdaQuery().eq(PaymentInfo::getOrderSn,orderSn));
    }
}
