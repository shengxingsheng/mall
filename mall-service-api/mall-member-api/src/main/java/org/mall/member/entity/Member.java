package org.mall.member.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员
 * </p>
 *
 * @author sxs
 * @since 2023-01-18
 */
@Getter
@Setter
@ToString
@TableName("ums_member")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "Member", description = "$!{table.comment}")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "会员等级id")
    private Long levelId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像")
    private String header;

    @Schema(description = "性别")
    private Byte gender;

    @Schema(description = "生日")
    private LocalDate birth;

    @Schema(description = "所在城市")
    private String city;

    @Schema(description = "职业")
    private String job;

    @Schema(description = "个性签名")
    private String sign;

    @Schema(description = "用户来源")
    private Byte sourceType;

    @Schema(description = "积分")
    private Integer integration;

    @Schema(description = "成长值")
    private Integer growth;

    @Schema(description = "启用状态")
    private Byte status;

    @Schema(description = "github账号id")
    private String githubId;

    @Schema(description = "逻辑删除 0未删除 1为删除")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @Schema(description = "创建人id")
    private Long createBy;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "创建人id")
    private Long updateBy;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
