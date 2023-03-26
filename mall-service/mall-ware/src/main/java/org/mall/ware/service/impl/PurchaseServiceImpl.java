package org.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.constant.ErrorCode;
import org.mall.common.exception.BusinessException;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.util.Assert;
import org.mall.ware.constant.WareConstant;
import org.mall.ware.dto.MergeDTO;
import org.mall.ware.dto.PurchaseDoneDTO;
import org.mall.ware.dto.PurchaseItemDoneDTO;
import org.mall.ware.entity.Purchase;
import org.mall.ware.entity.PurchaseDetail;
import org.mall.ware.entity.WareSku;
import org.mall.ware.mapper.PurchaseMapper;
import org.mall.ware.query.PurchasePageQuery;
import org.mall.ware.service.PurchaseDetailService;
import org.mall.ware.service.PurchaseService;
import org.mall.ware.service.WareSkuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * <p>
 * 采购信息 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements PurchaseService {

    private final PurchaseDetailService purchaseDetailService;
    private final WareSkuService wareSkuService;

    public PurchaseServiceImpl(PurchaseDetailService purchaseDetailService, WareSkuService wareSkuService) {
        this.purchaseDetailService = purchaseDetailService;
        this.wareSkuService = wareSkuService;
    }

    @Override
    public PageResult page(PurchasePageQuery query) {
        Page<Purchase> purchasePage = new Page<>(query.getPage(), query.getLimit());
        LambdaQueryWrapper<Purchase> wrapper = new LambdaQueryWrapper<>();
        String key = query.getKey();
        wrapper.and(StringUtils.isNotBlank(key),
                w -> w.like(Purchase::getAssigneeName, key)
                        .or()
                        .eq(Purchase::getAssigneeId, key)
                        .or()
                        .eq(Purchase::getWareId, key)
                        .or()
                        .like(Purchase::getPhone, key)
                        .or()
                        .eq(Purchase::getId, key)
                        .or()
                        .eq(Purchase::getPriority, key));
        Byte status = query.getStatus();
        wrapper.eq(Objects.nonNull(status), Purchase::getStatus, status);
        this.page(purchasePage, wrapper);

        return new PageResult(purchasePage);
    }

    @Override
    public PageResult getUnreceivePage(PageQuery query) {
        LambdaQueryWrapper<Purchase> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Purchase::getStatus, WareConstant.PURCHASE_STATUS_NEW)
                .or()
                .eq(Purchase::getStatus, WareConstant.PURCHASE_STATUS_ALLOCATED);
        List<Purchase> list = this.list(wrapper);
        PageResult result = new PageResult();
        result.setList(list);
        return result;
    }

    @Override
    public void merge(MergeDTO mergeDTO) {
        //校验数据
        check(mergeDTO);
        //确认采购单
        Long purchaseId = mergeDTO.getPurchaseId();
        Purchase purchase = new Purchase();
        if (Objects.isNull(purchaseId)) {
            purchase.setStatus(WareConstant.PURCHASE_STATUS_NEW);
            this.save(purchase);
        } else {
            purchase.setId(purchaseId);
            purchase.setStatus(WareConstant.PURCHASE_STATUS_ALLOCATED);
            this.updateById(purchase);
        }
        //确认采购项
        List<PurchaseDetail> details = purchaseDetailService.listByIds(mergeDTO.getItems());
        details = details.stream().map(detail -> {
            PurchaseDetail d = new PurchaseDetail();
            d.setId(detail.getId());
            d.setPurchaseId(purchase.getId());
            d.setStatus(WareConstant.PURCHASE_DETAIL_STATUS_ALLOCATED);
            return d;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(details);

    }

    /**
     * 校验合并数据
     *
     * @param mergeDTO
     * @return
     */
    private void check(MergeDTO mergeDTO) {
        List<Long> items = mergeDTO.getItems();
        List<Long> collect = items.stream().distinct().collect(Collectors.toList());
        if (collect.size() != items.size()) {
            log.error("数据重复");
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR, "采购需求项id重复");
        }
        List<PurchaseDetail> details = purchaseDetailService.listByIds(mergeDTO.getItems());
        if (CollectionUtils.isEmpty(details) || details.size() != items.size()) {
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR, "采购需求项id错误");
        }
        //检查重复的skuid和wareid 和不合法的状态
        List<String> skuWareId = details.stream().map(detail -> {
            boolean flag = WareConstant.PURCHASE_DETAIL_STATUS_NEW == (detail.getStatus()) || WareConstant.PURCHASE_DETAIL_STATUS_ALLOCATED == (detail.getStatus());
            Assert.isTrue(flag, "只有新建或已分配的采购项才能合并整单");
            return detail.getSkuId() + "" + detail.getWareId();
        }).distinct().collect(Collectors.toList());
        if (skuWareId.size() != items.size()) {
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR, "合并整单不能有相同采购商品id和仓库id的采购需求项");
        }
        Long purchaseId = mergeDTO.getPurchaseId();
        if (purchaseId != null) {
            Purchase oldPurchase = this.getById(purchaseId);
            Assert.notNull(oldPurchase, "系统无该采购单");
            boolean flag = WareConstant.PURCHASE_STATUS_NEW == (oldPurchase.getStatus()) || WareConstant.PURCHASE_STATUS_ALLOCATED == (oldPurchase.getStatus());
            Assert.isTrue(flag, "该采购单的状态不非法");
            //查询
            List<PurchaseDetail> list = purchaseDetailService.list(Wrappers.<PurchaseDetail>lambdaQuery().eq(PurchaseDetail::getPurchaseId, purchaseId));
            if (!CollectionUtils.isEmpty(list)) {
                List<String> oldSkuWareId = list.stream().map(detail ->
                        detail.getSkuId() + "" + detail.getWareId()
                ).distinct().collect(Collectors.toList());
                for (String s : oldSkuWareId) {
                    if (skuWareId.contains(s)) {
                        throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR, "指定的采购单中有相同采购商品id和仓库id的采购需求项");
                    }
                }
            }
        }
    }

    @Override
    public void received(List<Long> ids) {
        Assert.notEmpty(ids, "需要采购单id");
        List<Purchase> purchases = this.listByIds(ids);
        Assert.notEmpty(purchases, "没有该采购单");
        List<Purchase> collect = purchases.stream()
                .filter(p -> WareConstant.PURCHASE_STATUS_NEW == (p.getStatus()) || WareConstant.PURCHASE_STATUS_ALLOCATED == (p.getStatus()))
                .map(p -> {
                    Purchase purchase = new Purchase();
                    purchase.setId(p.getId());
                    purchase.setStatus(WareConstant.PURCHASE_STATUS_RECEIVED);
                    return purchase;
                }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            this.updateBatchById(collect);
            collect.forEach(purchase -> {
                List<PurchaseDetail> details = purchaseDetailService.list(new LambdaQueryWrapper<PurchaseDetail>().eq(PurchaseDetail::getPurchaseId, purchase.getId()));
                if (!CollectionUtils.isEmpty(details)) {
                    List<PurchaseDetail> list = details.stream().map(detail -> {
                        PurchaseDetail purchaseDetail = new PurchaseDetail();
                        purchaseDetail.setId(detail.getId());
                        purchaseDetail.setStatus(WareConstant.PURCHASE_DETAIL_STATUS_BUYING);
                        return purchaseDetail;
                    }).collect(Collectors.toList());
                    purchaseDetailService.updateBatchById(list);
                }
            });
        }
    }

    @Override
    public void done(PurchaseDoneDTO purchaseDoneDTO) {
        //判断数据状态是否正确
        check(purchaseDoneDTO);

        Long id = purchaseDoneDTO.getId();
        List<PurchaseItemDoneDTO> items = purchaseDoneDTO.getItems();

        //1.更新采购项状态 TODO 添加数据库表的reason
        AtomicBoolean flag = new AtomicBoolean(true);
        //成功的菜单项id集合
        ArrayList<Long> detailIds = new ArrayList<>();
        List<PurchaseDetail> details = items.stream().map(item -> {
            if (item.getStatus().equals(WareConstant.PURCHASE_DETAIL_STATUS_FAIl)) {
                flag.set(false);
            } else {
                //成功的采购项 构造
                detailIds.add(item.getItemId());

            }
            PurchaseDetail detail = new PurchaseDetail();
            detail.setId(item.getItemId());
            detail.setStatus(item.getStatus());
            return detail;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(details);


        //2.更新采购单状态
        Purchase purchase = new Purchase();
        purchase.setId(id);
        purchase.setStatus(flag.get() ? WareConstant.PURCHASE_STATUS_COMPLETED : WareConstant.PURCHASE_STATUS_ABNORMAL);
        this.updateById(purchase);

        //3.更新库存
        List<PurchaseDetail> detailList = purchaseDetailService.listByIds(detailIds);
        Assert.notEmpty(detailList, "数据异常");
        List<WareSku> wareSkus = detailList.stream().map(detail -> {
            WareSku wareSku = new WareSku();
            wareSku.setSkuId(detail.getSkuId());
            wareSku.setWareId(detail.getWareId());
            wareSku.setStock(detail.getSkuNum());
            return wareSku;
        }).collect(Collectors.toList());
        wareSkuService.addStock(wareSkus);
    }

    private void check(PurchaseDoneDTO purchaseDoneDTO) {
        Long id = purchaseDoneDTO.getId();
        List<PurchaseItemDoneDTO> items = purchaseDoneDTO.getItems();
        Purchase purchase = this.getById(id);
        Assert.notNull(purchase,"采购单["+id+"]不存在");
        Assert.isTrue(purchase.getStatus() == WareConstant.PURCHASE_STATUS_RECEIVED,"采购单["+id+"]状态不是已领取");

        List<Long> detailIds = items.stream().map(PurchaseItemDoneDTO::getItemId).distinct().collect(Collectors.toList());
        Assert.isTrue(detailIds.size() == items.size(),"采购项中有重复id");

        List<PurchaseDetail> details = purchaseDetailService.listByIds(detailIds);
        Assert.isTrue((!CollectionUtils.isEmpty(details)) && (details.size() == detailIds.size()),"采购项id错误");
        details.forEach(detail->{
            Assert.isTrue(WareConstant.PURCHASE_DETAIL_STATUS_BUYING == detail.getStatus(), "采购项[" + detail.getId() + "]的状态不是购买中");
        });
    }

}
