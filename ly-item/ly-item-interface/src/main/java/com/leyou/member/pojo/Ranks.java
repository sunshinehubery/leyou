package com.leyou.member.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/21 18:07
 * @Version: 1.0
 **/
@Table(name = "tb_ranks")
@Data
public class Ranks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rankName;//等级名称
    private Integer rankScore;//等级最低积分
}
