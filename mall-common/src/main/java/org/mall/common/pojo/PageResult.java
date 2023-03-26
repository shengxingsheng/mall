package org.mall.common.pojo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author sxs
 * @since 2023/1/18
 */
@Setter
@Getter
@ToString
@Schema(name = "PageResult",description = "分页返回数据")
public class PageResult<T> {
    @Schema(description = "当前页所有数据")
    private List<T> list;
    @Schema(description = "总记录数")
    private Long totalCount;
    @Schema(description = "当前页码")
    private Long currPage;
    @Schema(description = "总页码")
    private Long totalPage;
    @Schema(description = "每页大小")
    private Long pageSize;

    public PageResult() {
    }
    public PageResult(IPage page) {
        this.list=page.getRecords();
        this.totalCount=page.getTotal();
        this.currPage=page.getCurrent();
        this.totalPage=page.getPages();
        this.pageSize=page.getSize();
    }
    public PageResult(IPage page,List listDto) {
        this.list=listDto;
        this.totalCount=page.getTotal();
        this.currPage=page.getCurrent();
        this.totalPage=page.getPages();
        this.pageSize=page.getSize();
    }
    public PageResult(List listDto) {
        this.list=listDto;
        this.totalCount=0L;
        this.currPage=0L;
        this.totalPage=0L;
        this.pageSize=0L;
    }
    public static PageResult create(IPage page) {
        return new PageResult(page);
    }
    public static PageResult create(IPage page,List list) {
        return new PageResult(page,list);
    }
}
