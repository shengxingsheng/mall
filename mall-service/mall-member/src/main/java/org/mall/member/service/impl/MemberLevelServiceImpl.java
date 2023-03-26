package org.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.member.entity.MemberLevel;
import org.mall.member.mapper.MemberLevelMapper;
import org.mall.member.service.MemberLevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员等级 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements MemberLevelService {

    @Override
    public PageResult getPage(PageQuery pageQuery) {
        Page<MemberLevel> page = new Page<>(pageQuery.getPage(),pageQuery.getLimit());
        LambdaQueryWrapper<MemberLevel> wrapper = new LambdaQueryWrapper<>();
        String key = pageQuery.getKey();
        if (StringUtils.isNotBlank(key)) {
            wrapper.and(w -> w.eq(MemberLevel::getId, key).or().like(MemberLevel::getName, key));
        }
        this.page(page, wrapper);
        return new PageResult(page);
    }
}
