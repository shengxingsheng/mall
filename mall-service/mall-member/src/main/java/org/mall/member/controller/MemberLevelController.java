package org.mall.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;
import org.mall.member.entity.MemberLevel;
import org.mall.member.service.MemberLevelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员等级 前端控制器
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
@Tag(name="MemberLevelController",description = "会员等级管理")
@RestController
@RequestMapping("/member/memberlevel")
public class MemberLevelController {

    private final MemberLevelService memberLevelService;

    public MemberLevelController(MemberLevelService memberLevelService) {
        this.memberLevelService = memberLevelService;
    }

    @Operation(summary = "分页查询会员等级")
    @GetMapping("/list")
    public ResponseEntity<PageResult> getPage(@Validated PageQuery pageQuery) {
        return ResponseEntity.ok(memberLevelService.getPage(pageQuery));
    }

    @Operation(summary = "根据id查询")
    @GetMapping("/{id}")
    public ResponseEntity<MemberLevel> getById(@PathVariable Long id) {
        return ResponseEntity.ok(memberLevelService.getById(id));
    }
    @Operation(summary = "新增会员等级")
    @PostMapping()
    public ResponseEntity<Void> save(@Validated(AddGroup.class) @RequestBody MemberLevel memberLevel) {
        memberLevelService.save(memberLevel);
        return ResponseEntity.ok();
    }
    @Operation(summary = "修改会员等级")
    @PutMapping()
    public ResponseEntity<Void> updateById(@Validated(UpdateGroup.class) @RequestBody MemberLevel memberLevel) {
        memberLevelService.updateById(memberLevel);
        return ResponseEntity.ok();
    }
}
