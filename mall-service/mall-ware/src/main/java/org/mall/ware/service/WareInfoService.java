package org.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.ware.dto.FareDTO;
import org.mall.ware.entity.WareInfo;

/**
 * <p>
 * 仓库信息 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
public interface WareInfoService extends IService<WareInfo> {

    /**
     * 分页查询
     * @param pageQuery
     * @return
     */
    PageResult page(PageQuery pageQuery);

    /**
     * 运费估算
     * @param addrId
     * @return
     */
    FareDTO getFare(Long addrId);
}
