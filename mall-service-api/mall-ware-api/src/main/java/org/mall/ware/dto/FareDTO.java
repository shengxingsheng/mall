package org.mall.ware.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.member.entity.MemberReceiveAddress;

/**
 * @author sxs
 * @since 2023/2/22
 */
@Setter
@Getter
@ToString
public class FareDTO {
    private Integer fare;
    private MemberReceiveAddress address;
}
