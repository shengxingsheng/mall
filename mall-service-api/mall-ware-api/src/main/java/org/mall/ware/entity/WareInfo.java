package org.mall.ware.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mall.common.validation.AddGroup;
import org.mall.common.validation.UpdateGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 仓库信息
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Getter
@Setter
@ToString
@TableName("wms_ware_info")
@JsonIgnoreProperties({"deleted", "createBy", "createTime", "updateBy", "updateTime"})
@Schema(name = "WareInfo", description = "$!{table.comment}")
public class WareInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空",groups = {UpdateGroup.class})
    @Schema(description = "id")
    @TableId(value = "id")
    private Long id;

    @NotBlank(message = "仓库名不能为空",groups = {AddGroup.class})
    @Schema(description = "仓库名")
    private String name;

    @NotBlank(message = "仓库地址不能为空",groups = {AddGroup.class})
    @Schema(description = "仓库地址")
    private String address;

    @NotBlank(message = "区域编码不能为空",groups = {AddGroup.class})
    @Schema(description = "区域编码")
    private String areacode;

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
