package com.leyou.member.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @description: 会员信息
 * @author: furong
 * @date: 2019/5/21 17:39
 * @Version: 1.0
 **/
@Table(name = "tb_member")
@Data
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginName;//登录账号
    private String loginPwd;//登录密码
    private Integer sex;//性别(0：保密 1：男 2：女)
    private String memberName;//会员名称
    private String trueName;//真实姓名
    private String phone;//手机号码
    private Integer memberScore;//用户积分（消费积分一块钱一分）
    private Boolean status;//会员状态：0禁用；1启用
}
