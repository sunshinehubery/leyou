package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/4 9:53
 * @Version: 1.0
 **/
@Table(name = "tb_spec_group")
@Data
public class SpecGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private String name;
    @Transient
    private List<SpecParam> params;
}
