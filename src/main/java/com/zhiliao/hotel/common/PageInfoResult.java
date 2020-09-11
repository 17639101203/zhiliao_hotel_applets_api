package com.zhiliao.hotel.common;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author xiegege
 * @date 2020/4/17
 */

@ApiModel("分页结果集")
@Data
public class PageInfoResult<T> {

    @ApiModelProperty(value = "当前页")
    private int pageNo;

    @ApiModelProperty(value = "当前页的数量")
    private int currentPageNumber;

    @ApiModelProperty(value = "总记录数")
    private long totalItem;

    @ApiModelProperty(value = "总页数")
    private int totalPages;

    @ApiModelProperty(value = "结果集")
    private List<T> list;

    public static PageInfoResult getPageInfoResult(PageInfo pageInfo) {
        PageInfoResult result = new PageInfoResult();
        result.setPageNo(pageInfo.getPageNum());
        result.setCurrentPageNumber(pageInfo.getSize());
        result.setTotalItem(pageInfo.getTotal());
        result.setTotalPages(pageInfo.getPages());
        result.setList(pageInfo.getList());
        return result;
    }
}