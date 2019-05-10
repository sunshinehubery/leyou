package com.leyou.item.service;

import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.Specification;
import java.util.List;
/**
* @author: furong
* @date: 2019/5/5
* @description:
*/

public interface SpecificationService {
    /** 
      * @Description 根据category id查询规格参数模板
      * @Param [id]
      * @return com.leyou.item.pojo.Specification
      **/
    Specification queryById(Long id);

    /** 
      * @Description 添加规格参数模板
      * @Param [specification]
      * @return void
      **/
    void saveSpecification(Specification specification);

    /**
      * @Description 修改规格参数模板
      * @Param [specification]
      * @return void
      **/
    void updateSpecification(Specification specification);

    /**
      * @Description 删除规格参数模板
      * @Param [specification]
      * @return void
      **/
    void deleteSpecification(Specification specification);

    /**
      * @Description 通过商品分类id查询商品规格参数组信息
      * @Param [cid]
      * @return java.util.List<com.leyou.item.pojo.SpecGroup>
      **/
    List<SpecGroup> queryByCid(Long cid);

    /**
      * @Description 添加商品规格参数组
      * @Param [specGroup]
      * @return void
      **/
    void saveSpecGroup(SpecGroup specGroup);
    
    void updateGroup(SpecGroup specGroup);

    /**
      * @Description 根据参数组id查询具体参数信息
      * @Param [gid]
      * @return java.util.List<com.leyou.item.pojo.SpecParam>
      **/
    List<SpecParam> queryByGroupId(Long gid);
    
    void saveSpecParam(SpecParam specParam);

}
