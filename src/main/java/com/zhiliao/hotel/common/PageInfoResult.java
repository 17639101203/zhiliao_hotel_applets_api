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
    // 每页的数量
    private int pageSize;
    // 当前页的数量
    private int currentPageSize;
    // 总记录数
    private long total;
    // 总页数
    private int pages;
    // 结果集
    private List<Object> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageSize() {
        return currentPageSize;
    }

    public void setCurrentPageSize(int currentPageSize) {
        this.currentPageSize = currentPageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
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
        result.setPageSize(pageInfo.getPageSize());
        result.setCurrentPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotal());
        result.setPages(pageInfo.getPages());
        result.setList(pageInfo.getList());
        return result;
    }
}