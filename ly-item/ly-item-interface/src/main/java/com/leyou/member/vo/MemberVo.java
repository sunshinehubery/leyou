package com.leyou.member.vo;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/24 11:03
 * @Version: 1.0
 **/
@Data
public class MemberVo {
    private Long id;
    private String loginName;//登录账号
    private String loginPwd;//登录密码
    private Integer sex;//性别(0：保密 1：男 2：女)
    private String memberName;//会员名称
    private String trueName;//真实姓名
    private String phone;//手机号码
    private Integer memberScore;//用户积分（消费积分一块钱一分）
    private String lastTime;//最后登录时间
}
