package com.zhiliao.hotel.common;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author xiegege
 * @date 2020/4/17
 */

@ApiModel("分页结果集")
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

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(long totalItem) {
        this.totalItem = totalItem;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

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