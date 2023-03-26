package org.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.member.dto.GithubUserDTO;
import org.mall.member.entity.Member;

/**
 * <p>
 * 会员 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
public interface MemberService extends IService<Member> {

    /**
     * 注册用户
     * @param member
     */
    void register(Member member);
    /**
     * 通过github账号登录
     * @param githubUser
     */
    Member loginByGithub(GithubUserDTO githubUser);

    Member getByUsername(String username);

    Member getByPhone(String phone);


    /**
     * 分页查询会员的订单数据
     * @param pageQuery
     * @return
     */
    PageResult getOrderPage(PageQuery pageQuery);
}
