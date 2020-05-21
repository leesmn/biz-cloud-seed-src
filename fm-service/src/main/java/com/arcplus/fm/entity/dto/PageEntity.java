package com.arcplus.fm.entity.dto;

import lombok.Data;

// 分页对象
public class PageEntity<T>{

    // 数据总数
    private int totalCount;
    // 当前页数
    private int pageIndex;
    // 页面尺寸  显示条数
    private int pageSize;
    // 当前开始
    private int startIndex;
    // 当前结束
    private int endIndex;
    // 是否分页
    private Boolean page;

    //条件对象
    private T checkModelData;

    //条件对象
    private OrderObj[] orderModelData;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getCheckModelData() {
        return checkModelData;
    }

    public void setCheckModelData(T checkModelData) {
        this.checkModelData = checkModelData;
    }

    public int getStartIndex() {
        return ((this.pageIndex - 1) * pageSize);
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return (this.pageIndex * pageSize);
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public Boolean getPage() {
        return page;
    }

    public void setPage(Boolean page) {
        this.page = page;
    }

    public OrderObj[] getOrderModelData() {
        return orderModelData;
    }

    public void setOrderModelData(OrderObj[] orderModelData) {
        this.orderModelData = orderModelData;
    }

    @Data
    public static class OrderObj{

        private String method;
        private String field;

    }
}