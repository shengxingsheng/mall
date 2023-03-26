package org.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.ware.dto.PurchaseDoneDTO;
import org.mall.ware.dto.MergeDTO;
import org.mall.ware.entity.Purchase;
import org.mall.ware.query.PurchasePageQuery;

import java.util.List;

/**
 * <p>
 * 采购信息 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
public interface PurchaseService extends IService<Purchase> {

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult page(PurchasePageQuery query);

    /**
     * 未领取的单子
     * @return
     */
    PageResult getUnreceivePage(PageQuery query);

    /**
     * 合并整单
     * @param mergeDTO
     */
    void merge(MergeDTO mergeDTO);

    /**
     * 领取采购单
     * @param ids 采购单id
     */
    void received(List<Long> ids);

    /**
     * 完成采购
     * @param purchaseDoneDTO
     */
    void done(PurchaseDoneDTO purchaseDoneDTO);
}
