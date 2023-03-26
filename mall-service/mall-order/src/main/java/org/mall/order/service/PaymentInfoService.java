package org.mall.order.service;

import org.mall.order.entity.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 支付信息表 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
public interface PaymentInfoService extends IService<PaymentInfo> {

    PaymentInfo getByOrderSn(String orderSn);
}
