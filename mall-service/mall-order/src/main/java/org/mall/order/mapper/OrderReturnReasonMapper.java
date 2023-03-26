package org.mall.order.mapper;

import org.mall.order.entity.OrderReturnReason;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 退货原因 Mapper 接口
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Mapper
public interface OrderReturnReasonMapper extends BaseMapper<OrderReturnReason> {

}
