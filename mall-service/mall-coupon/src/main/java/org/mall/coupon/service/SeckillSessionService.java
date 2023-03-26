package org.mall.coupon.service;

import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.coupon.dto.SeckillSessionDTO;
import org.mall.coupon.entity.SeckillSession;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 秒杀活动场次 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
public interface SeckillSessionService extends IService<SeckillSession> {

    /**
     * 获取分页
     * @param pageQuery
     * @return
     */
    PageResult<SeckillSession> getPage(PageQuery pageQuery);

    /**
     * 获取最近三天的场次
     * @return
     */
    List<SeckillSessionDTO> getLatest3DaySession();
}
