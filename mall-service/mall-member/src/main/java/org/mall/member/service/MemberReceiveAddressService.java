package org.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.mall.member.entity.MemberReceiveAddress;

import java.util.List;

/**
 * <p>
 * 会员收货地址 服务类
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddress> {

    /**
     * 获取指定memberId的地址列表
     * @param memberId
     * @return
     */
    List<MemberReceiveAddress> listByMemberId(Long memberId);
}
