package org.mall.coupon.service.impl;

import org.mall.coupon.entity.HomeSubject;
import org.mall.coupon.mapper.HomeSubjectMapper;
import org.mall.coupon.service.HomeSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Service
public class HomeSubjectServiceImpl extends ServiceImpl<HomeSubjectMapper, HomeSubject> implements HomeSubjectService {

}
