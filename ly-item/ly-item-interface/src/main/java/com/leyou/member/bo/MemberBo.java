package com.leyou.member.bo;

import lombok.Data;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/30 11:22
 * @Version: 1.0
 **/
@Data
public class MemberBo {
    private String true_Name;//真实姓名
    private String phone;//手机号码
    private Integer member_Score;//用户积分（消费积分一块钱一分）
}
