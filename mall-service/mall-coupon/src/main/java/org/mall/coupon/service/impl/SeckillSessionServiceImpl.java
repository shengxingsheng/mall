package org.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.coupon.dto.SeckillSessionDTO;
import org.mall.coupon.entity.SeckillSession;
import org.mall.coupon.entity.SeckillSkuRelation;
import org.mall.coupon.mapper.SeckillSessionMapper;
import org.mall.coupon.mapper.SeckillSkuRelationMapper;
import org.mall.coupon.service.SeckillSessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 秒杀活动场次 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Service
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionMapper, SeckillSession> implements SeckillSessionService {
    private final SeckillSkuRelationMapper seckillSkuRelationMapper;

    public SeckillSessionServiceImpl(SeckillSkuRelationMapper seckillSkuRelationMapper) {
        this.seckillSkuRelationMapper = seckillSkuRelationMapper;
    }

    @Override
    public PageResult<SeckillSession> getPage(PageQuery pageQuery) {
        Page<SeckillSession> page = new Page<>(pageQuery.getPage(), pageQuery.getLimit());
        LambdaQueryWrapper<SeckillSession> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageQuery.getKey())) {
            wrapper.eq(SeckillSession::getId, pageQuery.getKey())
                    .or().like(SeckillSession::getName,pageQuery.getKey());
        }
        wrapper.orderByDesc(SeckillSession::getStartTime,SeckillSession::getEndTime);
        this.page(page,wrapper);
        return new PageResult<SeckillSession>(page);
    }

    @Override
    public List<SeckillSessionDTO> getLatest3DaySession() {
        LambdaQueryWrapper<SeckillSession> wrapper = new LambdaQueryWrapper<>();
        LocalDate date = LocalDate.now();
        LocalTime min = LocalTime.MIN;
        LocalTime max = LocalTime.MAX;
        LocalDateTime startTime = LocalDateTime.of(date, min);
        LocalDateTime endTime = LocalDateTime.of(date.plusDays(2), max);
        wrapper.eq(SeckillSession::getStatus, true)
                .between(SeckillSession::getStartTime, startTime, endTime)
                .orderByAsc(SeckillSession::getStartTime);
        List<SeckillSession> list = this.list(wrapper);
        return list.stream().map(seckillSession -> {
            List<SeckillSkuRelation> relations = seckillSkuRelationMapper.selectList(Wrappers.<SeckillSkuRelation>lambdaQuery().eq(SeckillSkuRelation::getPromotionSessionId, seckillSession.getId()));
            SeckillSessionDTO seckillSessionDTO = new SeckillSessionDTO();
            BeanUtils.copyProperties(seckillSession,seckillSessionDTO);
            seckillSessionDTO.setRelations(relations);
            return seckillSessionDTO;
        }).collect(Collectors.toList());
    }
}
