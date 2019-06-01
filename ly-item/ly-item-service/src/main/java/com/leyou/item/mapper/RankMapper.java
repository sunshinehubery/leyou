package com.leyou.item.mapper;

import com.leyou.member.pojo.Ranks;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface RankMapper extends Mapper<Ranks> {

    @Select("select count(*) from tb_member_rank where rank_id = #{id}")
    int countRanks(@Param("id") Long id);
}
