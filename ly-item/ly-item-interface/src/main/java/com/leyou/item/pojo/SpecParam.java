package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/4 10:46
 * @Version: 1.0
 **/
@Table(name = "tb_spec_param")
@Data
public class SpecParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cid")
    private Long categoryId;
    private Long groupId;
    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;



}
