package org.mall.member.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
@RestController
@RequestMapping("/member")
@Tag(name = "MemberController",description = "会员管理")
public class MemberController {

}
