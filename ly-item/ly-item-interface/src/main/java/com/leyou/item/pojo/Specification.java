package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description: 商品规格参数模板类，json格式
 * @author: furong
 * @date: 2019/4/29 18:19
 * @Version: 1.0
 **/
@Table(name="tb_specification")
@Data
public class Specification {
    @Id
    /**
     * 规格模板所属商品分类id
     */
    private Long categoryId;
    /**
     * 规格参数模板，json格式
     */
    private String specifications;
}
