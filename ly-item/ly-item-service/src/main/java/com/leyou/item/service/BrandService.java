package com.leyou.item.service;

import com.leyou.common.pojo.BrandQueryByPageParameter;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import java.util.List;

/**
* @author: furong
* @date: 2019/4/6
* @description:
*/

public interface BrandService {

    /**
      * @Description 分页查询
      * @Param [page, rows, sortBy, desc, key]
      * @return com.leyou.common.vo.PageResult<com.leyou.item.pojo.Brand>
      **/
    PageResult<Brand> queryBrandByPage(BrandQueryByPageParameter brandQueryByPageParameter);

    /**
      * @Description 新增品牌，维护中间表
      * @Param [brand, cids]
      * @return void
      **/
    void saveBrand(Brand brand, List<Long> cids);

    /**
      * @Description 更新品牌信息，维护中间表
      * @Param [brand, cids]
      * @return void
      **/
    void updateBrand(Brand brand, List<Long> cids);

    /**
      * @Description 删除品牌信息
      * @Param [id]
      * @return void
      **/
    void deleteBrand(Long id);

    /**
      * @Description 根据品牌id删除中间表信息
      * @Param [bid]
      * @return void
      **/
    void deleteByBrandIdInCategoryBrand(Long bid);

    /**
      * @Description 根据商品类目id查询商品品牌信息
      * @Param [cid]
      * @return java.util.List<com.leyou.item.pojo.Brand>
      **/
    List<Brand> queryBrandByCategoryId(Long cid);

    /**
      * @Description 根据品牌id集合查询品牌信息
      * @Param [ids]
      * @return java.util.List<com.leyou.item.pojo.Brand>
      **/
    List<Brand> queryBrandByBrandIds(List<Long> ids);
}
