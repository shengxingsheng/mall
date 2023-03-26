package org.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.product.entity.SkuImages;
import org.mall.product.entity.SkuInfo;
import org.mall.product.entity.SpuInfoDesc;
import org.mall.product.mapper.*;
import org.mall.product.query.SkuPageQuery;
import org.mall.product.service.SkuInfoService;
import org.mall.product.vo.SeckillSkuVo;
import org.mall.product.vo.SkuItemSaleAttrVo;
import org.mall.product.vo.SkuItemVo;
import org.mall.product.vo.SpuItemBaseAttrVo;
import org.mall.seckill.dto.chache.SeckillSkuInfoDTO;
import org.mall.seckill.feign.ISeckillClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {

    private final SkuImagesMapper skuImagesMapper;
    private final SpuInfoDescMapper spuInfoDescMapper;
    private final ProductAttrValueMapper productAttrValueMapper;
    private final SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    private final ISeckillClient seckillClient;
    private final ThreadPoolExecutor threadPoolExecutor;
    public SkuInfoServiceImpl(SkuImagesMapper skuImagesMapper, SpuInfoDescMapper spuInfoDescMapper, ProductAttrValueMapper productAttrValueMapper, SkuSaleAttrValueMapper skuSaleAttrValueMapper, ISeckillClient seckillClient, ThreadPoolExecutor threadPoolExecutor) {
        this.skuImagesMapper = skuImagesMapper;
        this.spuInfoDescMapper = spuInfoDescMapper;
        this.productAttrValueMapper = productAttrValueMapper;
        this.skuSaleAttrValueMapper = skuSaleAttrValueMapper;
        this.seckillClient = seckillClient;
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public PageResult page(SkuPageQuery query) {
        Page<SkuInfo> skuInfoPage = new Page<>(query.getPage(), query.getLimit());
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        String key = query.getKey();
        wrapper.and(StringUtils.isNotBlank(key), w -> w.eq(SkuInfo::getSkuId, key).or().eq(SkuInfo::getSkuName, key));
        Long catelogId = query.getCatelogId();
        wrapper.eq(catelogId != null && catelogId != 0, SkuInfo::getCatalogId, catelogId);
        Long brandId = query.getBrandId();
        wrapper.eq(brandId != null && brandId != 0, SkuInfo::getBrandId, brandId);
        BigDecimal min = query.getMin();
        wrapper.ge(min != null, SkuInfo::getPrice, min);
        BigDecimal max = query.getMax();
        wrapper.le(max != null && max.compareTo(new BigDecimal(0)) > 0, SkuInfo::getPrice, max);
        this.page(skuInfoPage, wrapper);
        return new PageResult(skuInfoPage);
    }

    @Override
    public SkuItemVo item(Long skuId) throws ExecutionException, InterruptedException {
        SkuItemVo skuItemVo = new SkuItemVo();
        CompletableFuture<SkuInfo> infoFuture = CompletableFuture.supplyAsync(() -> {
            //1.sku基本信息
            SkuInfo skuInfo = this.getById(skuId);
            skuItemVo.setSkuInfo(skuInfo);
            return skuInfo;
        }, threadPoolExecutor);
        CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync((skuInfo) -> {
            //3.sku销售属性
            List<SkuItemSaleAttrVo> saleAttrs = skuSaleAttrValueMapper.getSaleAttrsBySpuId(skuInfo.getSpuId());
            skuItemVo.setSaleAttrs(saleAttrs);
        }, threadPoolExecutor);
        CompletableFuture<Void> desFuture = infoFuture.thenAcceptAsync((skuInfo) -> {
            //4.spu的介绍
            SpuInfoDesc spuInfoDesc = spuInfoDescMapper.selectById(skuInfo.getSpuId());
            skuItemVo.setDesc(spuInfoDesc);
        }, threadPoolExecutor);
        CompletableFuture<Void> attrFuture = infoFuture.thenAcceptAsync((skuInfo) -> {
            //5.spu规格组及其组下的规格参数
            List<SpuItemBaseAttrVo> groupAttrs = productAttrValueMapper.getAttrGroupWithAttrsBySpuId(skuInfo.getSpuId());
            skuItemVo.setGroupAttrs(groupAttrs);
        }, threadPoolExecutor);
        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            //2.sku图片
            List<SkuImages> skuImages = skuImagesMapper.selectList(Wrappers.<SkuImages>lambdaQuery().eq(SkuImages::getSkuId, skuId).orderByDesc(SkuImages::getImgSort));
            skuItemVo.setImages(skuImages);
        }, threadPoolExecutor);
        //秒杀信息
        CompletableFuture<Void> seckillFuture = CompletableFuture.runAsync(() -> {
            ResponseEntity<SeckillSkuInfoDTO> resp = seckillClient.getSeckillSku(skuId);
            if (resp.isOK () &&resp.getData()!=null) {
                SeckillSkuVo seckillSkuVo = new SeckillSkuVo();
                SeckillSkuInfoDTO data = resp.getData();
                BeanUtils.copyProperties(data, seckillSkuVo);
                skuItemVo.setSeckillSkuVo(seckillSkuVo);
            }
        }, threadPoolExecutor);

        CompletableFuture.allOf(imageFuture, saleAttrFuture,desFuture, attrFuture, imageFuture,seckillFuture).get();
        return skuItemVo;
    }

    @Override
    public Map<Long, BigDecimal> getPrices(List<Long> skuIds) {
        List<SkuInfo> list = this.list(Wrappers.<SkuInfo>lambdaQuery().in(SkuInfo::getSkuId, skuIds));
        return list.stream().collect(Collectors.toMap(SkuInfo::getSkuId,SkuInfo::getPrice));
    }
}
