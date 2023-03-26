package org.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.exception.BusinessException;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.util.ErrorCodeUtil;
import org.mall.member.entity.MemberReceiveAddress;
import org.mall.member.feign.IMemberClient;
import org.mall.ware.dto.FareDTO;
import org.mall.ware.entity.WareInfo;
import org.mall.ware.mapper.WareInfoMapper;
import org.mall.ware.service.WareInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 仓库信息 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class WareInfoServiceImpl extends ServiceImpl<WareInfoMapper, WareInfo> implements WareInfoService {
    private final IMemberClient memberClient;

    public WareInfoServiceImpl(IMemberClient memberClient) {
        this.memberClient = memberClient;
    }

    @Override
    public PageResult page(PageQuery pageQuery) {
        Page<WareInfo> wareInfoPage = new Page<>(pageQuery.getPage(),pageQuery.getLimit());
        LambdaQueryWrapper<WareInfo> wrapper = new LambdaQueryWrapper<>();
        String key = pageQuery.getKey();
        wrapper.and(StringUtils.isNotBlank(key),
                w->w.eq(WareInfo::getId,key)
                        .or().like(WareInfo::getName,key)
                        .or().like(WareInfo::getAddress,key)
                        .or().eq(WareInfo::getAreacode,key));
        this.page(wareInfoPage, wrapper);
        return new PageResult(wareInfoPage);
    }

    @Override
    public FareDTO getFare(Long addrId) {
        //feign远程调用
        ResponseEntity<MemberReceiveAddress> responseEntity = memberClient.getAddressById(addrId);
        if (responseEntity.nonOK()) {
            log.error("code:{},msg:{}",responseEntity.getCode(),responseEntity.getMsg());
            throw new BusinessException(ErrorCodeUtil.getErrorCode(responseEntity));
        }
        //估算运费
        Integer fare = 8;
        FareDTO fareDTO = new FareDTO();
        fareDTO.setFare(fare);
        fareDTO.setAddress(responseEntity.getData());
        return fareDTO;
    }
}
