package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

/**
* @author: furong
* @date: 2019/4/7
* @description:
*/
public interface BrandMapper extends Mapper<Brand> {
    /**
      * @Description 插入中间表信息
      * @Param [cid, bid]
      * @return int
      **/
    @Insert("insert into tb_category_brand (category_id, brand_id) values (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
      * @Description 根据品牌id删除中间表信息
      * @Param [bid]
      * @return int
      **/
    @Delete("delete from tb_category_brand where brand_id = #{bid}")
    int deleteByBrandIdInCategoryBrand(@Param("bid") Long bid);

    /**
      * @Description 根据category id查询brand,左连接
      * @Param [cid]
      * @return java.util.List<com.leyou.item.pojo.Brand>
      **/
    @Select("select b.* from tb_brand b left join tb_category_brand cb on b.id = cb.brand_id where cb.category_id = #{cid}")
    List<Brand> queryBrandByCategoryId(@Param("cid") Long cid);
}
