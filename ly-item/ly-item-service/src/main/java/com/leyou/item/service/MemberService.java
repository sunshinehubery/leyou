package com.leyou.item.service;

import com.leyou.common.pojo.BrandQueryByPageParameter;
import com.leyou.common.vo.PageResult;
import com.leyou.member.bo.MemberBo;
import com.leyou.member.bo.RankBo;
import com.leyou.member.pojo.Member;
import com.leyou.member.vo.MemberVo;

import java.text.ParseException;
import java.util.List;

/**
 * @author: furong
 * @date: 2019/5/22
 * @description:
 */
public interface MemberService {

    /**
      * @Description 分页查询
      * @Param [brandQueryByPageParameter]
      * @return com.leyou.common.vo.PageResult<com.leyou.member.pojo.Member>
      **/
    PageResult<Member> queryMemberByPage(BrandQueryByPageParameter brandQueryByPageParameter);

    /**
      * @Description 新增会员，并维护中间表
      * @Param [member]
      * @return void
      **/
    void saveMember(Member member);

    /**
      * @Description 主键获取member
      * @Param [id]
      * @return com.leyou.member.pojo.Member
      **/
    Member getMemberById(Long id);

    /**
      * @Description 修改member信息
      * @Param [member]
      * @return void
      **/
    void editMember(Member member);

    void deleteMember(Long id);

    void deleteMemberRankByMemberId(Long id);

    List<RankBo> queryRankBo();

    List<MemberBo> queryMember();

    Member login(Member member);
}
