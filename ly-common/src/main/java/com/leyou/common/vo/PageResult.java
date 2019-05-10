package com.leyou.common.vo;

import lombok.Data;

import java.util.List;

/**
* @author: furong
* @date: 2019/4/6
* @description:
*/

@Data
public class PageResult<T> {
    private Long total; //记录总条数
    private Long totalPage; //记录总页数
    private List<T> items;

    public PageResult(){

    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
