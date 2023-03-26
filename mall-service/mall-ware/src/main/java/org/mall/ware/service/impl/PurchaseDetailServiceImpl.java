package org.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.pojo.PageResult;
import org.mall.ware.entity.PurchaseDetail;
import org.mall.ware.mapper.PurchaseDetailMapper;
import org.mall.ware.query.PurchasePageQuery;
import org.mall.ware.service.PurchaseDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailMapper, PurchaseDetail> implements PurchaseDetailService {

    @Override
    public PageResult page(PurchasePageQuery query) {
        Page<PurchaseDetail> detailPage = new Page<>(query.getPage(),query.getLimit());
        LambdaQueryWrapper<PurchaseDetail> wrapper = new LambdaQueryWrapper<>();
        String key = query.getKey();
        wrapper.and(StringUtils.isNotBlank(key),
                w ->w.eq(PurchaseDetail::getPurchaseId,key)
                        .or()
                        .eq(PurchaseDetail::getId,key)
                        .or()
                        .eq(PurchaseDetail::getSkuId,key));
        Long wareId = query.getWareId();
        wrapper.eq(wareId != null, PurchaseDetail::getWareId, wareId);
        Byte status = query.getStatus();
        wrapper.eq(status != null, PurchaseDetail::getStatus, status);
        this.page(detailPage, wrapper);
        return new PageResult(detailPage);
    }

    @Override
    public void saveDetail(PurchaseDetail purchaseDetail) {
        //保存采购需求单
        //判断是否重复
//        Long skuId = purchaseDetail.getSkuId();
//        Long wareId = purchaseDetail.getWareId();
//        LambdaQueryWrapper<PurchaseDetail> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(PurchaseDetail::getSkuId, skuId);
//        wrapper.eq(PurchaseDetail::getWareId, wareId);
//        wrapper.and(w -> w.or()
//                .eq(PurchaseDetail::getStatus, WareConstant.PURCHASE_STATUS_NEW)
//                .or()
//                .eq(PurchaseDetail::getStatus, WareConstant.PURCHASE_STATUS_ALLOCATED));
//        long count = this.count(wrapper);
//        if (count > 0) {
//            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR, "新增采购需求单重复");
//        }
        this.save(purchaseDetail);
    }
}
