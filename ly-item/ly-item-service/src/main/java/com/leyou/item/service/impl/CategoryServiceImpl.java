package com.leyou.item.service.impl;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 分类的业务层
 * @author: furong
 * @date: 2019/4/22 11:18
 * @Version: 1.0
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
      * @Description 根据父节点id查询分类
      * @Param [pid]
      * @return java.util.List<com.leyou.item.pojo.Category>
      **/
    @Override
    public List<Category> queryCategoryByPid(Long pid)  {
        //查询条件,会把mapper对象中的非空属性作为查询条件
        Category category = new Category();
        category.setParentId(pid);
        List<Category> list = categoryMapper.select(category);
        //判断结果是否为空
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FIND);
        }
        return list;
    }

    /**
      * @Description 根据品牌id查询商品类目
      * @Param [bid]
      * @return java.util.List<com.leyou.item.pojo.Category>
      **/
    @Override
    public List<Category> queryCategoryByBrandId(Long bid) {
        List<Category> list = categoryMapper.queryCategoryByBrandId(bid);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FIND);
        }
        return list;
    }

    /**
      * @Description 查询当前数据库最后一条信息
      * @Param []
      * @return java.util.List<com.leyou.item.pojo.Category>
      **/
    @Override
    public List<Category> queryLast() {
        List<Category> last = this.categoryMapper.selectLast();
        if (CollectionUtils.isEmpty(last)){
            throw new LyException(ExceptionEnum.LAST_CATEGORY_NOT_FOUND);
        }
        return last;
    }

    /**
      * @Description 保存
      * @Param [category]
      * @return void
      **/
    @Override
    public void saveCategory(Category category) {
        //首先设置id为空
        category.setId(null);
        //定义一个int类型的属性
        int count;
        //保存
        count = categoryMapper.insert(category);
        if(count != 1){
            throw new LyException(ExceptionEnum.CATEGORY_SAVE_ERROR);
        }
        //修改父节点
        Category parent = new Category();
        parent.setId(category.getParentId());
        parent.setIsParent(true);
        count = categoryMapper.updateByPrimaryKeySelective(parent);
        if(count != 1){
            throw new LyException(ExceptionEnum.CATEGORY_UPDATE_ERROR);
        }
    }

    /**
      * @Description 更新
      * @Param [category]
      * @return void
      **/
    @Override
    public void updateCategory(Category category) {
        int count = categoryMapper.updateByPrimaryKeySelective(category);
        if(count != 1){
            throw new LyException(ExceptionEnum.CATEGORY_UPDATE_ERROR);
        }
    }

    /**
      * @Description 删除
      * @Param [id]
      * @return void
      **/
    @Override
    public void deleteCategory(Long id) {
        /**
         * 先根据id查询要删除的对象，再做判断
         * 如果是父节点那么删除所有附带的子节点，然后维护中间表
         *  如果是子节点，那么只删除自己，然后判断父节点的孩子的个数，如果不为0不做修改，为0修改isParent为false
         *  维护中间表
         * */
        Category category = categoryMapper.selectByPrimaryKey(id);
        if(category.getIsParent()){
            //1.查找时所有的叶子节点
            List<Category> list = new ArrayList<>();
            queryAllLeafNode(category,list);
            //2.查找所有的子节点
            List<Category> list2 = new ArrayList<>();
            queryAllNode(category,list2);
            //3.删除tb_category表中的数据，使用list2
            for(Category c:list2){
                categoryMapper.delete(c);
            }
            //4.维护中间表tb_category_brand
            for(Category c:list){
                categoryMapper.deleteByCategoryIdInCategoryBrand(c.getId());
            }
        }else{
            //1.查询此节点的父节点的孩子个数 ==》查询还有多少兄弟
            Example example = new Example(Category.class);
            example.createCriteria().andEqualTo("parentId",category.getParentId());
            List<Category> list = categoryMapper.selectByExample(example);
            if(list.size()!=1){
                //有兄弟直接删除自己
                categoryMapper.deleteByPrimaryKey(category.getId());
                //维护中间表
                categoryMapper.deleteByCategoryIdInCategoryBrand(category.getId());
            }else{
                //没有兄弟，删除自己并将父节点isParent改为false
                categoryMapper.deleteByPrimaryKey(category.getId());
                Category parent = new Category();
                parent.setId(category.getParentId());
                parent.setIsParent(false);
                categoryMapper.updateByPrimaryKeySelective(parent);
                //维护中间表
                categoryMapper.deleteByCategoryIdInCategoryBrand(category.getId());
            }
        }
    }

    /**
      * @Description 根据id查询name
      * @Param [asList]
      * @return java.util.List<java.lang.String>
      **/
    @Override
    public List<String> queryNameByIds(List<Long> asList) {
        List<String> names = new ArrayList<>();
        if(asList != null && asList.size() != 0){
            for(Long id:asList){
                names.add(categoryMapper.queryNameById(id));
            }
        }
        if(CollectionUtils.isEmpty(names)){
            throw new LyException(ExceptionEnum.CATEGORY_NAME_NOT_FOUND);
        }
        return names;
    }

    /**
      * @Description 根据分类id集合查询分类信息
      * @Param [ids]
      * @return java.util.List<com.leyou.item.pojo.Category>
      **/
    @Override
    public List<Category> queryCategoryByIds(List<Long> ids) {
        List<Category> list = new ArrayList<>();
        for(Long id:ids){
            list.add(categoryMapper.selectByPrimaryKey(id));
        }
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FIND);
        }
        return list;
    }

    /** 
      * @Description 根据cid3查询所有分层分类
      * @Param [id]
      * @return java.util.List<com.leyou.item.pojo.Category>
      **/
    @Override
    public List<Category> queryAllCategoryLevelByCid3(Long id) {
        List<Category> categoryList = new ArrayList<>();
        Category category = categoryMapper.selectByPrimaryKey(id);
        while(category.getParentId() != 0){
            categoryList.add(category);
            category = categoryMapper.selectByPrimaryKey(category.getParentId());
        }
        categoryList.add(category);
        return categoryList;
    }

    /** 
      * @Description 查询本节点下包含的所有叶子结点，用于维护tb_category_brand中间表
      * @Param [category, leafNode]
      * @return void
      **/
    private void queryAllLeafNode(Category category,List<Category> leafNode){
        if(!category.getIsParent()){
            leafNode.add(category);
        }
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list = categoryMapper.selectByExample(example);
        for(Category category1:list){
            queryAllLeafNode(category1,leafNode);
        }
    }

    /**
      * @Description 查询本节点下的所有子节点
      * @Param [category, node]
      * @return void
      **/
    private void queryAllNode(Category category,List<Category> node){
        node.add(category);
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list = categoryMapper.selectByExample(example);
        for(Category category1:list){
            queryAllNode(category1,node);
        }
    }
}
