package org.mall.member.feign;

import org.mall.common.pojo.ResponseEntity;
import org.mall.member.dto.GithubUserDTO;
import org.mall.member.entity.Member;
import org.mall.member.entity.MemberReceiveAddress;
import org.mall.member.service.MemberReceiveAddressService;
import org.mall.member.service.MemberService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author sxs
 * @since 2023/1/10
 */
@RestController
public class MemberClient implements IMemberClient {
    private final MemberService memberService;
    private final MemberReceiveAddressService memberReceiveAddressService;

    public MemberClient(MemberService memberService, MemberReceiveAddressService memberReceiveAddressService) {
        this.memberService = memberService;
        this.memberReceiveAddressService = memberReceiveAddressService;
    }


    @Override
    public ResponseEntity<Void> register(Member member) {
        memberService.register(member);
        return ResponseEntity.ok();
    }

    @Override
    public ResponseEntity<Member> loginByGithub(GithubUserDTO githubUser) {
        return ResponseEntity.ok(memberService.loginByGithub(githubUser));
    }

    @Override
    public ResponseEntity<Member> getByUsername(String username) {
        return ResponseEntity.ok(memberService.getByUsername(username));
    }

    @Override
    public ResponseEntity<Member> getByPhone(String phone) {
        return ResponseEntity.ok(memberService.getByPhone(phone));
    }

    @Override
    public ResponseEntity<List<MemberReceiveAddress>> getAddress(Long memberId) {
        return ResponseEntity.ok(memberReceiveAddressService.listByMemberId(memberId));
    }

    @Override
    public ResponseEntity<MemberReceiveAddress> getAddressById(Long addrId) {

        return ResponseEntity.ok(memberReceiveAddressService.getById(addrId));
    }

}
