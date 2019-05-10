package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

/**
* @author: furong
* @date: 2019/4/5
* @description:
*/
public interface CategoryMapper extends Mapper<Category> {

    /** 
      * @Description 查询最后一条数据
      * @Param []
      * @return java.util.List<com.leyou.item.pojo.Category>
      **/
    @Select("select * from tb_category where id = (select max(id) from tb_category)")
    List<Category> selectLast();
    
    /** 
      * @Description 根据品牌id查询商品分类
      * @Param [bid]
      * @return java.util.List<com.leyou.item.pojo.Category>
      **/
    @Select("select * from tb_category where id in (select category_id from tb_category_brand where brand_id = #{bid})")
    List<Category> queryCategoryByBrandId(@Param("bid") Long bid);

    /**
      * @Description 根据商品类目id删除中间表信息
      * @Param [cid]
      * @return void
      **/
    @Select("delete from tb_category_brand where category_id = #{cid}")
    void deleteByCategoryIdInCategoryBrand(@Param("cid") Long cid);

    /**
      * @Description 根据id查询name
      * @Param [id]
      * @return java.lang.String
      **/
    @Select("select name from tb_category where id = #{id}")
    String queryNameById(@Param("id") Long id);
}
