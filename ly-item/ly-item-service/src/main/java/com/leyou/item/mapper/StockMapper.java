package com.leyou.item.mapper;

import com.leyou.item.bo.SkuBo;
import com.leyou.item.bo.StockBo;
import com.leyou.item.pojo.Stock;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StockMapper extends Mapper<Stock>, SelectByIdListMapper<Stock,Long> {
    @Select("select * from tb_stock where stock < 100 and stock > 0")
    List<StockBo> lessStock();

    @Select("select * from tb_stock where stock = 0")
    List<StockBo> notStock();

    @Select("select * from tb_stock where stock < 50")
    List<StockBo> needStock();
}
