package org.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.constant.ErrorCode;
import org.mall.common.exception.BusinessException;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.util.Assert;
import org.mall.coupon.dto.SpuSkuDTO;
import org.mall.coupon.entity.MemberPrice;
import org.mall.coupon.entity.SkuFullReduction;
import org.mall.coupon.entity.SkuLadder;
import org.mall.coupon.entity.SpuBounds;
import org.mall.coupon.feign.ICouponClient;
import org.mall.product.constant.ProductConstant;
import org.mall.product.dto.BaseAttrDTO;
import org.mall.product.dto.MemberpriceDTO;
import org.mall.product.dto.SkuInfoDTO;
import org.mall.product.dto.SpuInfoDTO;
import org.mall.product.entity.*;
import org.mall.product.mapper.*;
import org.mall.product.query.SpuPageQuery;
import org.mall.product.service.*;
import org.mall.search.dto.SkuEsDTO;
import org.mall.search.feign.ISearchClient;
import org.mall.ware.feign.IWareClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * spu信息 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements SpuInfoService {

    private final SpuImagesService spuImagesService;
    private final SpuInfoDescMapper spuInfoDescMapper;
    private final ProductAttrValueService productAttrValueService;
    private final AttrMapper attrMapper;
    private final SkuInfoMapper skuInfoMapper;
    private final SkuImagesService skuImagesService;
    private final SkuSaleAttrValueService skuSaleAttrValueService;
    private final BrandMapper brandMapper;
    private final CategoryMapper categoryMapper;
    private final ICouponClient couponClient;
    private final IWareClient wareClient;
    private final ISearchClient searchClient;

    public SpuInfoServiceImpl(SpuImagesService spuImagesService, ProductAttrValueService productAttrValueService, AttrMapper attrMapper, SkuInfoMapper skuInfoMapper, SkuImagesService skuImagesService, SkuSaleAttrValueService skuSaleAttrValueService, SpuInfoDescMapper spuInfoDescMapper, BrandMapper brandMapper, CategoryMapper categoryMapper, ICouponClient couponClient, IWareClient wareClient, ISearchClient searchClient) {
        this.spuImagesService = spuImagesService;
        this.productAttrValueService = productAttrValueService;
        this.attrMapper = attrMapper;
        this.skuInfoMapper = skuInfoMapper;
        this.skuImagesService = skuImagesService;
        this.skuSaleAttrValueService = skuSaleAttrValueService;
        this.spuInfoDescMapper = spuInfoDescMapper;
        this.brandMapper = brandMapper;
        this.categoryMapper = categoryMapper;
        this.couponClient = couponClient;
        this.wareClient = wareClient;
        this.searchClient = searchClient;
    }

    @GlobalTransactional
    @Override
    public void saveSpuAndSku(SpuInfoDTO spuInfoDTO) {
        //1.新增pms_spu_info
        this.save(spuInfoDTO);
        //2.新增pms_spu_info_desc
        List<String> decript = spuInfoDTO.getDecript();
        if (!CollectionUtils.isEmpty(decript)) {
            SpuInfoDesc spuInfoDesc = new SpuInfoDesc();
            spuInfoDesc.setSpuId(spuInfoDTO.getId());
            spuInfoDesc.setDecript(String.join(",", decript));
            spuInfoDescMapper.insert(spuInfoDesc);
        }
        //3.新增pms_spu_images
        List<String> images = spuInfoDTO.getImages();
        if (!CollectionUtils.isEmpty(images)) {
            List<SpuImages> collect = images.stream().map(image -> {
                SpuImages spuImages = new SpuImages();
                spuImages.setSpuId(spuInfoDTO.getId());
                spuImages.setImgUrl(image);
                return spuImages;
            }).collect(Collectors.toList());
            spuImagesService.saveBatch(collect);
        }
        //4.新增pms_product_attr_value
        List<BaseAttrDTO> baseAttrs = spuInfoDTO.getBaseAttrs();
        if (!CollectionUtils.isEmpty(baseAttrs)) {
            List<ProductAttrValue> productAttrValues = baseAttrs.stream().map(baseAttr -> {
                ProductAttrValue productAttrValue = new ProductAttrValue();
                BeanUtils.copyProperties(baseAttr, productAttrValue);
                productAttrValue.setSpuId(spuInfoDTO.getId());
                Optional<Attr> attrOptional = Optional.ofNullable(attrMapper.selectById(baseAttr.getAttrId()));
                attrOptional.ifPresent(attr -> productAttrValue.setAttrName(attr.getAttrName()));
                return productAttrValue;
            }).collect(Collectors.toList());
            productAttrValueService.saveBatch(productAttrValues);
        }
        //5.构造 远程调用参数- 新增sms_spu_bounds -
        SpuSkuDTO spuSkuDTO = new SpuSkuDTO();

        SpuBounds spuBounds = new SpuBounds();
        BeanUtils.copyProperties(spuInfoDTO.getBounds(), spuBounds);
        spuBounds.setSpuId(spuInfoDTO.getId());
        spuBounds.setWork((byte) 0b1111);
        spuSkuDTO.setSpuBounds(spuBounds);

        ArrayList<SkuLadder> skuLadders = new ArrayList<>();
        ArrayList<SkuFullReduction> skuFullReductions = new ArrayList<>();
        ArrayList<MemberPrice> memberPrices = new ArrayList<>();
        //6.新增pms_sku_info
        List<SkuInfoDTO> skuInfoDtos = spuInfoDTO.getSkus();
        skuInfoDtos.forEach(skuInfoDTO -> {
            List<SkuImages> skuImages = skuInfoDTO.getImages().stream()
                    .filter(image -> StringUtils.isNotBlank(image.getImgUrl()))
                    .collect(Collectors.toList());
            String defaultImg = "";
            for (SkuImages image : skuImages) {
                if (ProductConstant.IS_DEFAULT.equals(image.getDefaultImg())) {
                    image.setImgSort(1);
                    defaultImg = image.getImgUrl();
                    break;
                }
            }
            skuInfoDTO.setSpuId(spuInfoDTO.getId());
            skuInfoDTO.setCatalogId(spuInfoDTO.getCatalogId());
            skuInfoDTO.setBrandId(spuInfoDTO.getBrandId());
            skuInfoDTO.setSkuDefaultImg(defaultImg);
            if (!CollectionUtils.isEmpty(skuInfoDTO.getDescar())) {
                skuInfoDTO.setSkuDesc(String.join(",", skuInfoDTO.getDescar()));
            }
            //6.1新增pms_sku_info
            skuInfoMapper.insert(skuInfoDTO);
            //6.2批量新增pms_sku_images
            skuImages.forEach(image -> image.setSkuId(skuInfoDTO.getSkuId()));
            skuImagesService.saveBatch(skuImages);
            //6.3批量新增pms_sku_sale_attr_value
            List<SkuSaleAttrValue> attrs = skuInfoDTO.getAttr();
            attrs.forEach(attr -> attr.setSkuId(skuInfoDTO.getSkuId()));
            skuSaleAttrValueService.saveBatch(attrs);
            //6.4构造 远程调用参数 新增sms_sku_ladder
            Integer fullCount = skuInfoDTO.getFullCount();
            BigDecimal discount = skuInfoDTO.getDiscount();
            if (fullCount != null && fullCount > 0 && discount != null && (discount.compareTo(new BigDecimal(0)) > 0) && (discount.compareTo(new BigDecimal(1)) < 0)) {
                SkuLadder skuLadder = new SkuLadder();
                skuLadder.setSkuId(skuInfoDTO.getSkuId());
                skuLadder.setFullCount(fullCount);
                skuLadder.setDiscount(discount);
                skuLadder.setAddOther(skuInfoDTO.getCountStatus() != 0);
                skuLadders.add(skuLadder);
            }
            //6.5构造 远程调用参数 新增sms_sku_full_reduction
            BigDecimal fullPrice = skuInfoDTO.getFullPrice();
            BigDecimal reducePrice = skuInfoDTO.getReducePrice();
            if (fullPrice != null && reducePrice != null && fullPrice.compareTo(new BigDecimal(0)) > 0 && reducePrice.compareTo(new BigDecimal(0)) > 0) {
                SkuFullReduction skuFullReduction = new SkuFullReduction();
                skuFullReduction.setSkuId(skuInfoDTO.getSkuId());
                skuFullReduction.setFullPrice(fullPrice);
                skuFullReduction.setReducePrice(reducePrice);
                skuFullReduction.setAddOther(skuInfoDTO.getPriceStatus() != 0);
                skuFullReductions.add(skuFullReduction);
            }
            //6.6构造 远程调用参数 新增sms_member_price
            List<MemberpriceDTO> memberpriceDtos = skuInfoDTO.getMemberPrice();
            if (!CollectionUtils.isEmpty(memberpriceDtos)) {
                memberpriceDtos.forEach(memberpriceDTO -> {
                    if (memberpriceDTO.getPrice().compareTo(new BigDecimal(0)) > 0) {
                        MemberPrice memberPrice = new MemberPrice();
                        memberPrice.setSkuId(skuInfoDTO.getSkuId());
                        memberPrice.setMemberPrice(memberpriceDTO.getPrice());
                        memberPrice.setMemberLevelId(memberpriceDTO.getId());
                        memberPrice.setMemberLevelName(memberpriceDTO.getName());
                        memberPrice.setAddOther(true);
                        memberPrices.add(memberPrice);
                    }
                });
            }
        });
        //7.发送远程调用
        spuSkuDTO.setSkuLadder(skuLadders);
        spuSkuDTO.setSkuFullReduction(skuFullReductions);
        spuSkuDTO.setMemberPrice(memberPrices);
        //TODO feign 待优化
        ResponseEntity resp = couponClient.saveBySpuSku(spuSkuDTO);
        if (!resp.getCode().equals(ErrorCode.OK.getCode())) {
            log.error("feign远程调用失败");
        }
    }

    @Override
    public PageResult page(SpuPageQuery query) {
        IPage<SpuInfo> spuInfoPage = new Page<>(query.getPage(), query.getLimit());
        LambdaQueryWrapper<SpuInfo> wrapper = new LambdaQueryWrapper<>();
        String key = query.getKey();
        wrapper.and(StringUtils.isNotBlank(key), w -> w.eq(SpuInfo::getId, key).or().like(SpuInfo::getSpuName, key));
        Long catelogId = query.getCatelogId();
        wrapper.eq(catelogId != null && catelogId != 0, SpuInfo::getCatalogId, catelogId);
        Long brandId = query.getBrandId();
        wrapper.eq(brandId != null && brandId != 0, SpuInfo::getBrandId, brandId);
        Byte status = query.getStatus();
        wrapper.eq(status != null, SpuInfo::getPublishStatus, status);
        this.page(spuInfoPage, wrapper);
        return new PageResult(spuInfoPage);

    }

    /**
     * 商品上架
     *
     * @param id spuId
     */
    @Override
    public void up(Long id) {
        //0.开端-校验
        SpuInfo spuInfo = checkSpuInfo(id);
        List<SkuInfo> skuInfoList = checkSkuInfo(id);
        List<Long> skuIdList = skuInfoList.stream().map(SkuInfo::getSkuId).collect(Collectors.toList());
        //1.准备数据-根据brandId获取品牌信息
        Optional<Brand> brandOptional = Optional.ofNullable(brandMapper.selectById(spuInfo.getBrandId()));
        if (!brandOptional.isPresent()) {
            log.error("spuId[{}]的brandId[{}]对应的品牌信息为空", id, spuInfo.getBrandId());
        }
        //2.准备数据-根据categoryId获取分类信息
        Optional<Category> categoryOptional = Optional.ofNullable(categoryMapper.selectById(spuInfo.getCatalogId()));
        if (!categoryOptional.isPresent()) {
            log.error("spuId[{}]的catalogId[{}]对应的分类信息为空", id, spuInfo.getCatalogId());
        }
        //3.准备数据-sku库存信息，远程调用mall-ware服务
        //TODO feign远程调用 待优化
        Optional<List<Long>> hasStockIdsOptional = Optional.empty();
        try {
            ResponseEntity<List<Long>> responseEntity = wareClient.filterByStock(skuIdList);
            if (responseEntity.getCode().equals(ErrorCode.OK.getCode())) {
                hasStockIdsOptional = Optional.ofNullable(responseEntity.getData());
            }
        } catch (Exception e) {
            log.error("远程调用库存服务异常:原因{}", e);
        }
        //4.准备数据-构造SkuEsDTO.SpuAttr,是可被搜索的属性
        List<SkuEsDTO.SpuAttr> spuAttrs = getSpuAttrs(id);
        //5.准备数据-构造es传输类 skuEsDTOList
        Optional<List<Long>> finalHasStockIdsOptional = hasStockIdsOptional;
        List<SkuEsDTO.SpuAttr> finalSpuAttrs = spuAttrs;
        List<SkuEsDTO> skuEsDTOList = skuInfoList.stream().map(skuInfo -> {
            SkuEsDTO skuEsDTO = new SkuEsDTO();
            BeanUtils.copyProperties(skuInfo, skuEsDTO);
            skuEsDTO.setSkuId(skuInfo.getSkuId().toString());
            skuEsDTO.setCatalogId(skuInfo.getCatalogId().toString());
            skuEsDTO.setBrandId(skuInfo.getBrandId().toString());
            skuEsDTO.setSpuId(skuInfo.getSpuId().toString());
            //补全属性-价格、图片
            skuEsDTO.setSkuPrice(skuInfo.getPrice());
            skuEsDTO.setSkuImg(skuInfo.getSkuDefaultImg());
            //补全属性-热度值
            skuEsDTO.setHotScore(0L);
            //补全属性-品牌名字、品牌logo
            brandOptional.ifPresent(brand -> {
                skuEsDTO.setBrandName(brand.getName());
                skuEsDTO.setBrandImg(brand.getLogo());
            });
            //补全属性-分类名字
            categoryOptional.ifPresent(category -> {
                skuEsDTO.setCatalogName(category.getName());
            });
            //补全属性-是否有库存
            skuEsDTO.setHasStock(false);
            finalHasStockIdsOptional.ifPresent(hasStockIds -> {
                skuEsDTO.setHasStock(hasStockIds.contains(skuInfo.getSkuId()));
            });
            //补全属性- attrs
            skuEsDTO.setAttrs(finalSpuAttrs);
            return skuEsDTO;
        }).collect(Collectors.toList());
        //6.更新-向ES中插入文档，需要远程调用
        //TODO feign远程调用 待优化
        ResponseEntity<Void> responseEntity = searchClient.saveProduct(skuEsDTOList);
        if (responseEntity.getCode().equals(ErrorCode.OK.getCode())) {
            //7.更新-spu状态
            SpuInfo newSpuInfo = new SpuInfo();
            newSpuInfo.setId(id);
            newSpuInfo.setPublishStatus(ProductConstant.SPU_UP);
            this.updateById(newSpuInfo);
        } else {
            //TODO feign重试机制 如何保证接口幂等性？
        }
    }

    @Override
    public SpuInfo getBySkuId(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectOne(Wrappers.<SkuInfo>lambdaQuery().eq(SkuInfo::getSkuId, skuId));
        return this.getOne(Wrappers.<SpuInfo>lambdaQuery().eq(SpuInfo::getId,skuInfo.getSpuId()));
    }

    /**
     * 构造SkuEsDTO.SpuAttr
     * @param id spuId
     * @return List<SkuEsDTO.SpuAttr> 属性集合
     */
    private List<SkuEsDTO.SpuAttr> getSpuAttrs(Long id) {
        List<ProductAttrValue> attrList = productAttrValueService.getBySpuId(id);
        List<SkuEsDTO.SpuAttr> spuAttrs = new ArrayList<>();
        if (!CollectionUtils.isEmpty(attrList)) {
            List<Long> attrIdList = attrList.stream().map(ProductAttrValue::getAttrId).collect(Collectors.toList());
            List<Attr> attrs = attrMapper.selectBatchIds(attrIdList);
            if (!CollectionUtils.isEmpty(attrs)) {
                List<Long> canSearchAttrIds = attrs.stream()
                        .filter(attr -> attr.getSearchType() == ProductConstant.ATTR_CAN_SEARCH)
                        .map(Attr::getAttrId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(canSearchAttrIds)) {
                    spuAttrs = attrList.stream()
                            .filter(attr -> canSearchAttrIds.contains(attr.getAttrId()))
                            .map(attr -> {
                                SkuEsDTO.SpuAttr spuAttr = new SkuEsDTO.SpuAttr();
                                spuAttr.setAttrId(attr.getAttrId().toString());
                                spuAttr.setAttrName(attr.getAttrName());
                                spuAttr.setAttrValue(attr.getAttrValue());
                                return spuAttr;
                            }).collect(Collectors.toList());
                }
            } else {
                log.error("查不到对应的属性，attrIds{}", attrIdList);
            }
        } else {
            log.error("该商品没有规格参数，spuId[{}]", id);
        }
        return spuAttrs;
    }

    /**
     * 校验skuInfo
     *
     * @param id spuId
     * @return SkuInfo的集合
     */
    private List<SkuInfo> checkSkuInfo(Long id) {
        List<SkuInfo> skuInfoList = skuInfoMapper.selectList(Wrappers.<SkuInfo>lambdaQuery().eq(SkuInfo::getSpuId, id));
        if (CollectionUtils.isEmpty(skuInfoList)) {
            log.error("指定id[{}]的spu的sku信息为空", id);
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR, "指定id[" + id + "]的spu的sku信息为空");
        }
        return skuInfoList;
    }

    /**
     * 校验对应id的spuInfo
     *
     * @param id SpuId
     * @return 校验成功 spuInfo
     */
    private SpuInfo checkSpuInfo(Long id) {
        Assert.notNull(id, "id不能为null");
        Optional<SpuInfo> spuOptional = Optional.ofNullable(this.getById(id));
        if (spuOptional.isPresent()) {
            if (spuOptional.get().getPublishStatus() == ProductConstant.SPU_UP) {
                log.error("指定id[{}]的商品已上架", id);
                throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR, "指定id[" + id + "]的商品已上架");
            }
        } else {
            log.error("指定id[{}]的商品不存在", id);
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR, "指定id[" + id + "]的商品不存在");
        }
        return spuOptional.get();
    }
}
