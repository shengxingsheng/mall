package org.mall.ware.service;

import org.mall.common.pojo.PageResult;
import org.mall.ware.entity.PurchaseDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.ware.query.PurchasePageQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
public interface PurchaseDetailService extends IService<PurchaseDetail> {

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult page(PurchasePageQuery query);

    /**
     * 新增采购需求
     * @param purchaseDetail
     */
    void saveDetail(PurchaseDetail purchaseDetail);
}
