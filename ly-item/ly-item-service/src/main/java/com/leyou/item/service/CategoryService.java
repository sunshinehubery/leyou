package com.leyou.item.service;

import com.leyou.common.exception.LyException;
import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * @description: 分类的业务层
 * @author: furong
 * @date: 2019/4/22 11:18
 * @Version: 1.0
 **/
public interface CategoryService {

    /** 
      * @Description 根据id查询分类
      * @Param [pid]
      * @return java.util.List<com.leyou.item.pojo.Category>
      **/
    List<Category> queryCategoryByPid(Long pid);

    /**
      * @Description 根据brand id查询分类信息
      * @Param [bid]
      * @return java.util.List<com.leyou.item.pojo.Category>
      **/
    List<Category> queryCategoryByBrandId(Long bid);
    
    /** 
      * @Description 查询当前数据库最后一条信息
      * @Param []
      * @return java.util.List<com.leyou.item.pojo.Category>
      **/
    List<Category> queryLast();

    /**
      * @Description 保存
      * @Param [category]
      * @return void
      **/
    void saveCategory(Category category);

    /**
      * @Description 更新
      * @Param [category]
      * @return void
      **/
    void updateCategory(Category category);

    /**
      * @Description 删除
      * @Param [id]
      * @return void
      **/
    void deleteCategory(Long id);

    /**
      * @Description 根据id查询name
      * @Param [asList]
      * @return java.util.List<java.lang.String>
      **/
    List<String> queryNameByIds(List<Long> asList);

    /**
      * @Description 根据分类id查询分类信息
      * @Param [ids]
      * @return java.util.List<com.leyou.item.pojo.Category>
      **/
    List<Category> queryCategoryByIds(List<Long> ids);

    List<Category> queryAllCategoryLevelByCid3(Long id);
}
