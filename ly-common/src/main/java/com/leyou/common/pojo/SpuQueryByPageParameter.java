package com.leyou.common.pojo;

import lombok.Data;

/**
 * @description:
 * @author: furong
 * @date: 2019/4/30 17:13
 * @Version: 1.0
 **/
public class SpuQueryByPageParameter extends BrandQueryByPageParameter {
    //是否上架
    private Boolean saleable;

    public Boolean getSaleable() {
        return saleable;
    }

    public void setSaleable(Boolean saleable) {
        this.saleable = saleable;
    }

    public SpuQueryByPageParameter(Integer page, Integer rows, String sortBy, Boolean desc, String key, Boolean saleable) {
        super(page, rows, sortBy, desc, key);
        this.saleable = saleable;
    }

    public SpuQueryByPageParameter(Boolean saleable) {
        this.saleable = saleable;
    }

    @Override
    public String toString() {
        return "SpuQueryByPageParameter{" +
                "saleable=" + saleable +
                '}';
    }
}
