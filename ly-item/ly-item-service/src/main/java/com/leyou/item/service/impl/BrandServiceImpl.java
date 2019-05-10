package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.pojo.BrandQueryByPageParameter;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: furong
 * @date: 2019/4/26 14:23
 * @Version: 1.0
 **/
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    /**
      * @Description 分页查询
      * @Param [page, rows, sortBy, desc, key]
      * @return com.leyou.common.vo.PageResult<com.leyou.item.pojo.Brand>
      **/
    public PageResult<Brand> queryBrandByPage(BrandQueryByPageParameter brandQueryByPageParameter) {
        //分页
        PageHelper.startPage(brandQueryByPageParameter.getPage(), brandQueryByPageParameter.getRows());
        //过滤
        /**
         * where 'name' like "*x*" or letter == "x"
         * order by id desc
         * */
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(brandQueryByPageParameter.getKey())){
            //过滤条件
            example.createCriteria().orLike("name", "*" + brandQueryByPageParameter.getKey() + "*")
                    .orEqualTo("letter", brandQueryByPageParameter.getKey().toUpperCase());
        }
        //排序
        if(StringUtils.isNotBlank(brandQueryByPageParameter.getSortBy())){
            String orderByClause = brandQueryByPageParameter.getSortBy() + (brandQueryByPageParameter.getDesc() ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Brand> list = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FIND);
        }
        //解析分页结果
        PageInfo<Brand> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(), list);
    }

    /**
      * @Description 新增品牌，维护中间表
      * @Param [brand, cids]
      * @return void
      **/
    @Transactional
    @Override
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增品牌
        brand.setId(null);
        int count = brandMapper.insert(brand);
        if (count != 1){
            throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        //新增中间表
        for(Long cid : cids){
            count = brandMapper.insertCategoryBrand(cid, brand.getId());
            if (count != 1){
                throw new LyException(ExceptionEnum.CATEGORY_BRAND_SAVE_ERROR);
            }
        }
    }

    /**
      * @Description 更新品牌信息，维护中间表
      * @Param [brand, cids]
      * @return void
      **/
    @Override
    public void updateBrand(Brand brand, List<Long> cids) {
        //删除中间表原来的信息
        deleteByBrandIdInCategoryBrand(brand.getId());
        //修改品牌信息
        int count = brandMapper.updateByPrimaryKeySelective(brand);
        if(count != 1){
            throw new LyException(ExceptionEnum.BRAND_UPDATE_ERROR);
        }
        //维护中间表信息
        for(Long cid:cids){
            count = brandMapper.insertCategoryBrand(cid,brand.getId());
            if (count != 1){
                throw new LyException(ExceptionEnum.CATEGORY_BRAND_SAVE_ERROR);
            }
        }
    }

    @Transactional
    @Override
    public void deleteBrand(Long id) {
        int count = brandMapper.deleteByPrimaryKey(id);
        if(count != 1){
            throw new LyException(ExceptionEnum.BRAND_DELETE_ERROR);
        }
        //维护中间表
        deleteByBrandIdInCategoryBrand(id);
    }

    /**
      * @Description 根据品牌信息删除中间表信息
      * @Param [bid]
      * @return void
      **/
    @Override
    public void deleteByBrandIdInCategoryBrand(Long bid) {
            int count = brandMapper.deleteByBrandIdInCategoryBrand(bid);
            if(count != 1){
                throw new LyException(ExceptionEnum.CATEGORY_BRAND_DELETE_ERROR);
            }
    }

    /**
      * @Description 根据商品类目id查询商品品牌信息
      * @Param [cid]
      * @return java.util.List<com.leyou.item.pojo.Brand>
      **/
    @Override
    public List<Brand> queryBrandByCategoryId(Long cid) {
        List<Brand> list = brandMapper.queryBrandByCategoryId(cid);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FIND);
        }
        return list;
    }

    /**
      * @Description 根据品牌id集合查询品牌信息
      * @Param [ids]
      * @return java.util.List<com.leyou.item.pojo.Brand>
      **/
    @Override
    public List<Brand> queryBrandByBrandIds(List<Long> ids) {
        List<Brand> list = new ArrayList<>();
        for(Long id:ids){
            list.add(brandMapper.selectByPrimaryKey(id));
        }
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FIND);
        }
        return list;
    }
}
