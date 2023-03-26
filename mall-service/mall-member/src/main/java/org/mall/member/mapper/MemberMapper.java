package org.mall.member.mapper;

import org.mall.member.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员 Mapper 接口
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}
