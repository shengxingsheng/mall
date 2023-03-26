package org.mall.member.controller.front;

import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.member.service.MemberService;
import org.mall.order.vo.OrderVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author sxs
 * @since 2023/3/5
 */
@Controller("frontMemberController")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("memberOrder.html")
    public String memberOrder(@Validated PageQuery pageQuery, Model model) {
        PageResult<OrderVO> pageResult =  memberService.getOrderPage(pageQuery);
        model.addAttribute("result",pageResult);
        return "orderList";
    }

}
