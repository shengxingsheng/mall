package org.mall.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author sxs
 * @since 2023/2/16
 */
@Setter
@Getter
@ToString
@Schema(description = "用户登录信息")
public class UserInfoDTO {
    private Long userId;
    private String userKey;
//    private Boolean hasLogged;
}
