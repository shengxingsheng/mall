package org.mall.coupon.dto;

import lombok.Data;
import org.mall.coupon.entity.SeckillSession;
import org.mall.coupon.entity.SeckillSkuRelation;

import java.util.List;

/**
 * @author sxs
 * @since 2023/3/7
 */
@Data
public class SeckillSessionDTO extends SeckillSession {
    List<SeckillSkuRelation> relations;
}
