package com.zhiliao.hotel.common;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分页结果集
 * Created by xiegege on 2020/4/17.
 */
public class PageInfoResult {

    // 当前页
    private int pageNo;
    // 当前页的数量
    private int currentPageNumber;
    // 总记录数
    private long totalItem;
    // 总页数
    private int totalPages;
    // 结果集
    private List<Object> list;

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

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
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