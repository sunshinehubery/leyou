package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.pojo.BrandQueryByPageParameter;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.MemberMapper;
import com.leyou.item.mapper.RankMapper;
import com.leyou.member.bo.MemberBo;
import com.leyou.member.bo.RankBo;
import com.leyou.member.pojo.Member;
import com.leyou.item.service.MemberService;
import com.leyou.member.pojo.Ranks;
import com.leyou.member.vo.MemberVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/22 11:14
 * @Version: 1.0
 **/
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private RankMapper rankMapper;

    /**
      * @Description 分页查询
      * @Param [brandQueryByPageParameter]
      * @return com.leyou.common.vo.PageResult<com.leyou.member.pojo.Member>
      **/
    @Override
    public PageResult<Member> queryMemberByPage(BrandQueryByPageParameter brandQueryByPageParameter) {
        //分页
        PageHelper.startPage(brandQueryByPageParameter.getPage(),brandQueryByPageParameter.getRows());
        //过滤
        Example example = new Example(Member.class);
        if(StringUtils.isNotBlank(brandQueryByPageParameter.getKey())){
            example.createCriteria().orLike("memberName","*"+brandQueryByPageParameter.getKey()+"*")
                    .orLike("trueName","*"+brandQueryByPageParameter.getKey()+"*");
        }
        //排序
        if(StringUtils.isNotBlank(brandQueryByPageParameter.getSortBy())){
            String orderByClause = brandQueryByPageParameter.getSortBy() + (brandQueryByPageParameter.getDesc() ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Member> list = memberMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.MEMBER_NOT_FOUND);
        }
        //解析分页结果
        PageInfo<Member> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(),list);
    }

    /**
      * @Description 添加member
      * @Param [member]
      * @return void
      **/
    @Override
    public void saveMember(Member member) {
        member.setId(null);
        member.setLoginPwd("123456");
        member.setMemberScore(0);
        if(member.getSex() == null){
            member.setSex(0);
        }
        member.setStatus(true);
        int count = memberMapper.insert(member);
        if(count != 1){
            throw new LyException(ExceptionEnum.MEMBER_SAVE_ERROR);
        }
        //新增中间表
        count = memberMapper.insertMemberRank(member.getId(),1L);
        if(count != 1){
            throw new LyException(ExceptionEnum.MEMBER_RANK_SAVE_ERROR);
        }
    }

    /**
      * @Description 主键获取member
      * @Param [id]
      * @return com.leyou.member.pojo.Member
      **/
    @Override
    public Member getMemberById(Long id) {
        Member member = memberMapper.selectByPrimaryKey(id);
        return member;
    }

    /**
      * @Description 修改member信息
      * @Param [member]
      * @return void
      **/
    @Override
    public void editMember(Member member){
        int count = memberMapper.updateByPrimaryKeySelective(member);
        if(count != 1){
            throw new LyException(ExceptionEnum.MEMBER_UPDATE_ERROR);
        }
    }

    @Override
    public void deleteMember(Long id) {
        int count = memberMapper.deleteByPrimaryKey(id);
        if(count != 1){
            throw new LyException(ExceptionEnum.MEMBER_DELETE_ERROR);
        }
        deleteMemberRankByMemberId(id);
    }

    @Override
    public void deleteMemberRankByMemberId(Long id) {
        int count = memberMapper.deleteMemberRankByMemberId(id);
    }

    @Override
    public List<RankBo> queryRankBo() {
        List<Ranks> list = rankMapper.selectAll();
        List<RankBo> listRankBo = new ArrayList<>();
        for (Ranks rank:list){
            RankBo rankBo = new RankBo();
            rankBo.setId(rank.getId());
            rankBo.setRankName(rank.getRankName());
            rankBo.setRankScore(rank.getRankScore());
            rankBo.setAmount(rankMapper.countRanks(rank.getId()));
            listRankBo.add(rankBo);
        }
        return listRankBo;
    }

    @Override
    public List<MemberBo> queryMember() {
        List<MemberBo> list;
        list = memberMapper.selectMembers();
        for(MemberBo member:list){
            System.out.println(member.toString());
        }
        return list;
    }

    @Override
    public Member login(Member member) {
        Member member1=memberMapper.selectOne(member);
        return member1;
    }
}
