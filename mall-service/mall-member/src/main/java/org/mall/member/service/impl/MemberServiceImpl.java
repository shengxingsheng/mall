package org.mall.member.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mall.common.constant.ErrorCode;
import org.mall.common.exception.BusinessException;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.member.constant.MemberConstant;
import org.mall.member.dto.GithubUserDTO;
import org.mall.member.entity.Member;
import org.mall.member.entity.MemberLevel;
import org.mall.member.interceptor.MemberInterceptor;
import org.mall.member.mapper.MemberLevelMapper;
import org.mall.member.mapper.MemberMapper;
import org.mall.member.service.MemberService;
import org.mall.order.feign.IOrderClient;
import org.mall.order.vo.OrderVO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * <p>
 * 会员 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    private final MemberLevelMapper memberLevelMapper;
    private final IOrderClient orderClient;

    public MemberServiceImpl(MemberLevelMapper memberLevelMapper, IOrderClient orderClient) {
        this.memberLevelMapper = memberLevelMapper;
        this.orderClient = orderClient;
    }

    @Override
    public void register(Member member) {
        String username = member.getUsername();
        String mobile = member.getMobile();
        long count = this.count(Wrappers.<Member>lambdaQuery().eq(Member::getMobile, mobile));
        if (count > 0) {
            throw new BusinessException(ErrorCode.USER_PHONE_REPEAT);
        }
        count = this.count(Wrappers.<Member>lambdaQuery().eq(Member::getUsername, username));
        if (count > 0) {
            throw new BusinessException(ErrorCode.USER_NAME_REPEAT);
        }
        //注册
        member.setPassword(new BCryptPasswordEncoder().encode(member.getPassword()));
        Optional<MemberLevel> memberLevelOptional = Optional.ofNullable( memberLevelMapper.selectOne(Wrappers.<MemberLevel>lambdaQuery().eq(MemberLevel::getDefaultStatus, MemberConstant.LEVEL_DEFAULT_STATUS)));
        memberLevelOptional.ifPresent(memberLevel -> member.setLevelId(memberLevel.getId()));
        member.setStatus(MemberConstant.STATUS);
        this.save(member);
    }


    @Override
    public Member loginByGithub(GithubUserDTO githubUser) {
        //登录和注册合并
        String id = githubUser.getId();
        Member member = this.getOne(Wrappers.<Member>lambdaQuery().eq(Member::getGithubId, id));
        if (member == null) {
            //注册
            member = new Member();
            member.setGithubId(id);
            member.setNickname(githubUser.getLogin());
            member.setStatus(MemberConstant.STATUS);
            Optional<MemberLevel> memberLevelOptional = Optional.ofNullable( memberLevelMapper.selectOne(Wrappers.<MemberLevel>lambdaQuery().eq(MemberLevel::getDefaultStatus, MemberConstant.LEVEL_DEFAULT_STATUS)));
            if (memberLevelOptional.isPresent()) {
                member.setLevelId(memberLevelOptional.get().getId());
            }
            this.save(member);
        }
        return member;
    }

    @Override
    public Member getByUsername(String username) {
        return this.getOne(Wrappers.<Member>lambdaQuery().eq(Member::getUsername, username));
    }

    @Override
    public Member getByPhone(String phone) {
        return this.getOne(Wrappers.<Member>lambdaQuery().eq(Member::getMobile,phone));
    }

    @Override
    public PageResult<OrderVO> getOrderPage(PageQuery pageQuery) {
        Member member = MemberInterceptor.loginUser.get();
        ResponseEntity<PageResult<OrderVO>> response = orderClient.getOrderPageByMemberId(member.getId(),pageQuery);
        if (response.nonOK()) {
           return new PageResult(Collections.EMPTY_LIST);
        }
        return response.getData();
    }
}
