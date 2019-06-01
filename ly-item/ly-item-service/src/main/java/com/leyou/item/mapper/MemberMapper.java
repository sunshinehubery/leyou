package com.leyou.item.mapper;

import com.leyou.member.bo.MemberBo;
import com.leyou.member.pojo.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MemberMapper extends Mapper<Member> {

    @Insert("insert into tb_member_rank (member_id, rank_id) values (#{mid}, #{rid})")
    int insertMemberRank(@Param("mid") Long mid, @Param("rid") Long rid);

    @Delete("delete from tb_member_rank where member_id = #{id}")
    int deleteMemberRankByMemberId(@Param("id") Long id);

    @Select("select * from (SELECT * FROM `tb_member` ORDER BY member_Score DESC)members limit 0,5")
    List<MemberBo> selectMembers();

    /*@Select("select * from tb_member where login_Name = #{loginName} and login_Pwd = #{loginPwd}")
    Member login(@Param("loginName") String loginName, @Param("loginPwd") String LoginPwd);*/
}
