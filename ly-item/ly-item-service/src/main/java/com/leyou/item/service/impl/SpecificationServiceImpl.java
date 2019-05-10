package com.leyou.item.service.impl;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.mapper.SpecificationMapper;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.Specification;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/1 13:41
 * @Version: 1.0
 **/
@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    /**
      * @Description 根据category id查询规格参数模板
      * @Param [id]
      * @return com.leyou.item.pojo.Specification
      **/
    @Override
    public Specification queryById(Long id) {
        return specificationMapper.selectByPrimaryKey(id);
    }

    /**
      * @Description 添加规格参数模板
      * @Param [specification]
      * @return void
      **/
    @Override
    public void saveSpecification(Specification specification) {
        specificationMapper.insert(specification);
    }

    /**
      * @Description 修改规格参数模板
      * @Param [specification]
      * @return void
      **/
    @Override
    public void updateSpecification(Specification specification) {
        specificationMapper.updateByPrimaryKeySelective(specification);
    }

    /**
      * @Description 删除规格参数模板
      * @Param [specification]
      * @return void
      **/
    @Override
    public void deleteSpecification(Specification specification) {
        specificationMapper.deleteByPrimaryKey(specification);
    }

    /**
      * @Description 通过商品分类id查询商品规格参数组信息
      * @Param [cid]
      * @return java.util.List<com.leyou.item.pojo.SpecGroup>
      **/
    @Override
    public List<SpecGroup> queryByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return specGroupMapper.select(specGroup);
    }

    /**
      * @Description 添加商品规格参数组
      * @Param [specGroup]
      * @return void
      **/
    @Override
    public void saveSpecGroup(SpecGroup specGroup) {
        specGroupMapper.insert(specGroup);
    }

    @Override
    public void updateGroup(SpecGroup specGroup) {
        specGroupMapper.updateByPrimaryKeySelective(specGroup);
    }

    /**
      * @Description 根据参数组id查询具体参数信息
      * @Param [gid]
      * @return java.util.List<com.leyou.item.pojo.SpecParam>
      **/
    @Override
    public List<SpecParam> queryByGroupId(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        return specParamMapper.select(specParam);
    }

    @Override
    public void saveSpecParam(SpecParam specParam) {
        specParamMapper.select(specParam);
    }
}
