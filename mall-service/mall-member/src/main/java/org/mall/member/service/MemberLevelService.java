package org.mall.member.service;

import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.member.entity.MemberLevel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员等级 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
public interface MemberLevelService extends IService<MemberLevel> {

    /**
     * 分页查询
     * @param pageQuery
     * @return
     */
    PageResult getPage(PageQuery pageQuery);
}
