package org.mall.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author sxs
 * @since 2023/1/21
 */
@Getter
@Setter
@ToString
@Schema(name = "PageQuery", description = "分页请求参数")
public class PageQuery {
    //排序类型
    public static final String ORDER_TYPE_ASC = "asc";
    public static final String ORDER_TYPE_DESC = "desc";
    @Schema(description = "当前页码")
    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码不能为负")
    private Integer page;
    @Schema(description = "每页记录数")
    @NotNull(message = "页大小不能为空")
    @Min(value = 0, message = "页大小不能为负")
    private Integer limit;
    @Schema(description = "排序字段")
    private String sidx;
    @Schema(description = "排序方式")
    private String order;
    @Schema(description = "检索关键字")
    private String key;

}
