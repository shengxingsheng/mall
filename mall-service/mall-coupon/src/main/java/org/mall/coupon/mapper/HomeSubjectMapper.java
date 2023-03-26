package org.mall.coupon.mapper;

import org.mall.coupon.entity.HomeSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】 Mapper 接口
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Mapper
public interface HomeSubjectMapper extends BaseMapper<HomeSubject> {

}
