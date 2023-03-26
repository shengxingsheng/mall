package org.mall.member.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.mall.member.entity.MemberReceiveAddress;
import org.mall.member.mapper.MemberReceiveAddressMapper;
import org.mall.member.service.MemberReceiveAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员收货地址 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
@Service
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressMapper, MemberReceiveAddress> implements MemberReceiveAddressService {

    @Override
    public List<MemberReceiveAddress> listByMemberId(Long memberId) {
        return this.list(Wrappers.<MemberReceiveAddress>lambdaQuery().eq(MemberReceiveAddress::getMemberId,memberId));
    }
}
