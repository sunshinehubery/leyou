package com.leyou.common.pojo;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/9 17:05
 * @Version: 1.0
 **/
public class UserQueryByPageParameter {

    private Integer page;
    private Integer rows;
    private String sortBy;
    private Boolean desc;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Boolean getDesc() {
        return desc;
    }

    public void setDesc(Boolean desc) {
        this.desc = desc;
    }
    public UserQueryByPageParameter(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        this.page = page;
        this.rows = rows;
        this.sortBy = sortBy;
        this.desc = desc;
    }

    public UserQueryByPageParameter(){
        super();
    }

    @Override
    public String toString() {
        return "BrandQueryByPageParameter{" +
                "page=" + page +
                ", rows=" + rows +
                ", sortBy='" + sortBy + '\'' +
                ", desc=" + desc + '\'' +
                '}';
    }
}
