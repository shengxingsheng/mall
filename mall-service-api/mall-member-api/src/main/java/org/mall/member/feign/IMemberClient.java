package org.mall.member.feign;

import io.swagger.v3.oas.annotations.Operation;
import org.mall.common.pojo.ResponseEntity;
import org.mall.member.dto.GithubUserDTO;
import org.mall.member.entity.Member;
import org.mall.member.entity.MemberReceiveAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author sxs
 * @since 2023/1/10
 */
@FeignClient("mall-member")
public interface IMemberClient {

    /**
     * 注册用户
     * @param member
     * @return
     */
    @PostMapping("/feign/member")
    ResponseEntity register(@RequestBody Member member);

    @PostMapping("/feign/member/login/github")
    ResponseEntity<Member> loginByGithub(@RequestBody GithubUserDTO githubUser);

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    @GetMapping("/feign/member/username/{username}")
    ResponseEntity<Member> getByUsername(@PathVariable("username") String username);
    /**
     * 通过手机号查找用户
     * @param phone
     * @return
     */
    @GetMapping("/feign/member/phone/{phone}")
    ResponseEntity<Member> getByPhone(@PathVariable("phone") String phone);

    /**
     * 获取指定用户的地址列表
     * @param memberId
     * @return
     */
    @GetMapping("/feign/member/{memberId}/address")
    ResponseEntity<List<MemberReceiveAddress>> getAddress(@PathVariable("memberId") Long memberId);

    @Operation(summary = "获取指定id的地址")
    @GetMapping("/feign/member/address/{addrId}")
    ResponseEntity<MemberReceiveAddress> getAddressById(@PathVariable("addrId")Long addrId);
}
