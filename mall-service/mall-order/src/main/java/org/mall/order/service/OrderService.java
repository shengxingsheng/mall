package org.mall.order.service;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.common.dto.mq.SeckillOrderDTO;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.order.dto.OrderConfirmDTO;
import org.mall.order.dto.OrderSubmitDTO;
import org.mall.order.dto.OrderSubmitResponseDTO;
import org.mall.order.entity.Order;
import org.mall.order.vo.OrderVO;
import org.mall.order.vo.PayAsyncVo;

import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
public interface OrderService extends IService<Order> {

    /**
     * 订单确认页需要的数据
     * @return
     */
    OrderConfirmDTO getConfirmOrder() throws ExecutionException, InterruptedException;

    /**
     * 提价订单
     * @param orderSubmitDTO
     * @return
     */
    OrderSubmitResponseDTO submitOrder(OrderSubmitDTO orderSubmitDTO);

    /**
     * 根据orderSn获取order
     * @param orderSn
     * @return
     */
    Order getByOrderSn(String orderSn);

    /**
     * 超时时间到
     * @param order
     */
    void closeOrder(Order order);

    /**
     * 支付宝
     * @param orderSn
     * @return
     */
    String aliPayOrder(String orderSn) throws AlipayApiException;

    /**
     * 获取order列表
     * @param id
     * @param pageQuery
     * @return
     */
    PageResult<OrderVO> getOrderPageByMemberId(Long id, PageQuery pageQuery);

    /**
     * 处理支付成功通知
     * @param payAsyncVo
     * @return
     */
    void aliPayNotify(PayAsyncVo payAsyncVo) ;

    /**
     * 创建秒杀订单
     * @param seckillSkuDTO
     */
    void createSeckillOrder(SeckillOrderDTO seckillSkuDTO);

    /**
     * 秒杀订单超时时间到
     * @param seckillOrderDTO
     */
    void closeOrder(SeckillOrderDTO seckillOrderDTO);
}
