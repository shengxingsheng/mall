package org.mall.product.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品评价
 * </p>
 *
 * @author sxs
 * @since 2023-01-13
 */
@Getter
@Setter
@ToString
@TableName("pms_spu_comment")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "SpuComment", description = "$!{table.comment}")
public class SpuComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @Schema(description = "sku_id")
    private Long skuId;

    @Schema(description = "spu_id")
    private Long spuId;

    @Schema(description = "商品名字")
    private String spuName;

    @Schema(description = "会员昵称")
    private String memberNickName;

    @Schema(description = "星级")
    private Boolean star;

    @Schema(description = "会员ip")
    private String memberIp;

    @Schema(description = "显示状态[0-不显示，1-显示]")
    private Boolean showStatus;

    @Schema(description = "购买时属性组合")
    private String spuAttributes;

    @Schema(description = "点赞数")
    private Integer likesCount;

    @Schema(description = "回复数")
    private Integer replyCount;

    @Schema(description = "评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]")
    private String resources;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "用户头像")
    private String memberIcon;

    @Schema(description = "评论类型[0 - 对商品的直接评论，1 - 对评论的回复]")
    private Byte commentType;

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
