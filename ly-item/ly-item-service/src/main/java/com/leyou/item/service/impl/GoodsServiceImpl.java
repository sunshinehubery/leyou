package com.leyou.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.pojo.SpuQueryByPageParameter;
import com.leyou.common.vo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import com.leyou.item.service.CategoryService;
import com.leyou.item.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: furong
 * @date: 2019/4/30 17:20
 * @Version: 1.0
 **/
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    /**
      * @Description 分页查询
      * @Param [spuQueryByPageParameter]
      * @return com.leyou.common.vo.PageResult<com.leyou.item.bo.SpuBo>
      **/
    @Override
    public PageResult<SpuBo> querySpuByPageAndSort(SpuQueryByPageParameter spuQueryByPageParameter) {
       //1.查询spu，分页查询，最多查询100条记录
        PageHelper.startPage(spuQueryByPageParameter.getPage(), Math.min(spuQueryByPageParameter.getRows(),100));

        //2.创建查询条件
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        //3.条件过滤
        //3.1是否过滤上下架
        if(spuQueryByPageParameter.getSaleable() != null){
            criteria.orEqualTo("saleable",spuQueryByPageParameter.getSaleable());
        }
        //3.2是否模糊查询
        if(StringUtils.isNotBlank(spuQueryByPageParameter.getKey())){
            criteria.andLike("title","%" + spuQueryByPageParameter.getKey() + "%");
        }
        //3.3是否排序
        if(StringUtils.isNotBlank(spuQueryByPageParameter.getSortBy())){
            example.setOrderByClause(spuQueryByPageParameter.getSortBy()+(spuQueryByPageParameter.getDesc()? " DESC":" ASC"));
        }
        Page<Spu> pageInfo = (Page<Spu>) spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(pageInfo)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        //将spu变为spubo
        List<SpuBo> list = pageInfo.getResult().stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            //1.属性拷贝
            BeanUtils.copyProperties(spu,spuBo);
            //2.查询商品分类名称，各级分类
            List<String> nameList = categoryService.queryNameByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));
            //3.拼接名字，并存入
            spuBo.setCname(StringUtils.join(nameList,"/"));
            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());
            return spuBo;
        }).collect(Collectors.toList());
        return new PageResult<>(pageInfo.getTotal(),list);
    }

    /**
      * @Description 保存商品
      * @Param [spu]
      * @return void
      **/
    @Transactional
    @Override
    public void saveGoods(SpuBo spu) {
        System.out.println(spu.toString());
        //保存spu
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spuMapper.insert(spu);
        //保存spu的详情
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());

        int count = spuDetailMapper.insert(spuDetail);
        if(count != 1){
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }
        //保存sku和库存信息
        saveSkuAndStock(spu.getSkus(),spu.getId());
    }

    /**
      * @Description 根据id查询商品
      * @Param [id]
      * @return com.leyou.item.bo.SpuBo
      **/
    @Override
    public SpuBo queryGoodsById(Long id) {
        /**
         * 第一页所需信息如下：
         * 1.商品的分类信息、所属品牌、商品标题、商品卖点（子标题）
         * 2.商品的包装清单、售后服务
         */
        Spu spu = spuMapper.selectByPrimaryKey(id);
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spu.getId());
        Example example = new Example(Sku.class);
        example.createCriteria().orEqualTo("spuId",spu.getId());
        List<Sku> skuList = skuMapper.selectByExample(example);
        List<Long> skuIdList = new ArrayList<>();
        for(Sku sku:skuList){
            skuIdList.add(sku.getId());
        }
        List<Stock> stockList = stockMapper.selectByIdList(skuIdList);
        for(Sku sku:skuList){
            for(Stock stock:stockList){
                if(sku.getId().equals(stock.getSkuId())){
                    sku.setStock(stock.getStock());
                }
            }
        }
        SpuBo spuBo = new SpuBo(spu.getBrandId(),spu.getCid1(),spu.getCid2(),spu.getCid3(),spu.getTitle(),
                spu.getSubTitle(),spu.getSaleable(),spu.getValid(),spu.getCreateTime(),spu.getLastUpdateTime());
        spuBo.setSpuDetail(spuDetail);
        spuBo.setSkus(skuList);
        return spuBo;
    }

    /**
      * @Description 根据spu的id查询所有的sku
      * @Param [id]
      * @return java.util.List<com.leyou.item.pojo.Sku>
      **/
    @Override
    public List<Sku> querySkuBySpuId(Long id) {
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId",id);
        List<Sku> skuList = this.skuMapper.selectByExample(example);
        for (Sku sku : skuList){
            Example temp = new Example(Stock.class);
            temp.createCriteria().andEqualTo("skuId", sku.getId());
            Stock stock = this.stockMapper.selectByExample(temp).get(0);
            sku.setStock(stock.getStock());
        }
        return skuList;
    }

    @Transactional
    @Override
    public void updateGoods(SpuBo spuBo) {
        /**
         * 更新策略：
         *      1.判断tb_spu_detail中的spec_template字段新旧是否一致
         *      2.如果一致说明修改的只是库存、价格和是否启用，那么就使用update
         *      3.如果不一致，说明修改了特有属性，那么需要把原来的sku全部删除，然后添加新的sku
         */

        //更新spu
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setLastUpdateTime(new Date());
        this.spuMapper.updateByPrimaryKeySelective(spuBo);

        //更新spu详情
        SpuDetail spuDetail = spuBo.getSpuDetail();
        String oldTemp = this.spuDetailMapper.selectByPrimaryKey(spuBo.getId()).getSpecTemplate();
        if (spuDetail.getSpecTemplate().equals(oldTemp)){
            //相等，sku update
            //更新sku和库存信息
            updateSkuAndStock(spuBo.getSkus(),spuBo.getId(),true);
        }else {
            //不等，sku insert
            //更新sku和库存信息
            updateSkuAndStock(spuBo.getSkus(),spuBo.getId(),false);
        }
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.updateByPrimaryKeySelective(spuDetail);
    }

    /**
      * @Description 商品上架下架
      * @Param [id]
      * @return void
      **/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void goodsSoldOutPut(Long id) {
        //根据id获取商品的信息
        Spu oldSpu = spuMapper.selectByPrimaryKey(id);
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId",id);
        List<Sku> skuList = skuMapper.selectByExample(example);
        if(oldSpu.getSaleable()){
            //下架
            oldSpu.setSaleable(false);
            spuMapper.updateByPrimaryKeySelective(oldSpu);
            //下架sku中具体的商品
            for(Sku sku:skuList){
                sku.setEnable(false);
                skuMapper.updateByPrimaryKeySelective(sku);
            }
        }else{
            //上架
            oldSpu.setSaleable(true);
            spuMapper.updateByPrimaryKeySelective(oldSpu);
            //上架sku中具体的商品
            for(Sku sku:skuList){
                sku.setEnable(true);
                skuMapper.updateByPrimaryKeySelective(sku);
            }
        }
    }

    /**
      * @Description 删除商品
      * @Param [id]
      * @return void
      **/
    @Override
    public void deleteGoods(Long id) {
        spuMapper.deleteByPrimaryKey(id);
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId",id);
        spuDetailMapper.deleteByExample(example);
        List<Sku> skuList = skuMapper.selectByExample(example);
        for(Sku sku:skuList){
            skuMapper.deleteByPrimaryKey(sku.getId());
            stockMapper.deleteByPrimaryKey(sku.getId());
        }
    }

    private void updateSkuAndStock(List<Sku> skus,Long id,boolean tag) {
        //通过tag判断是insert还是update
        //获取当前数据库中spu_id = id的sku信息
        Example e = new Example(Sku.class);
        e.createCriteria().andEqualTo("spuId",id);
        //oldList中保存数据库中spu_id = id 的全部sku
        List<Sku> oldList = this.skuMapper.selectByExample(e);
        if (tag){
            /**
             * 判断是更新时是否有新的sku被添加：如果对已有数据更新的话，则此时oldList中的数据和skus中的ownSpec是相同的，否则则需要新增
             */
            int count = 0;
            for (Sku sku : skus){
                if (!sku.getEnable()){
                    continue;
                }
                for (Sku old : oldList){
                    if (sku.getOwnSpec().equals(old.getOwnSpec())){
                        System.out.println("更新");
                        //更新
                        List<Sku> list = this.skuMapper.select(old);
                        if (sku.getPrice() == null){
                            sku.setPrice(0L);
                        }
                        if (sku.getStock() == null){
                            sku.setStock(0L);
                        }
                        sku.setId(list.get(0).getId());
                        sku.setCreateTime(list.get(0).getCreateTime());
                        sku.setSpuId(list.get(0).getSpuId());
                        sku.setLastUpdateTime(new Date());
                        this.skuMapper.updateByPrimaryKey(sku);
                        //更新库存信息
                        Stock stock = new Stock();
                        stock.setSkuId(sku.getId());
                        stock.setStock(sku.getStock());
                        this.stockMapper.updateByPrimaryKeySelective(stock);
                        //从oldList中将更新完的数据删除
                        oldList.remove(old);
                        break;
                    }else{
                        //新增
                        count ++ ;
                    }
                }
                if (count == oldList.size() && count != 0){
                    //当只有一个sku时，更新完因为从oldList中将其移除，所以长度变为0，所以要需要加不为0的条件
                    List<Sku> addSku = new ArrayList<>();
                    addSku.add(sku);
                    saveSkuAndStock(addSku,id);
                    count = 0;
                }else {
                    count =0;
                }
            }
            //处理脏数据
            if (oldList.size() != 0){
                for (Sku sku : oldList){
                    this.skuMapper.deleteByPrimaryKey(sku.getId());
                    Example example = new Example(Stock.class);
                    example.createCriteria().andEqualTo("skuId",sku.getId());
                    this.stockMapper.deleteByExample(example);
                }
            }
        }else {
            List<Long> ids = oldList.stream().map(Sku::getId).collect(Collectors.toList());
            //删除以前的库存
            Example example = new Example(Stock.class);
            example.createCriteria().andIn("skuId",ids);
            this.stockMapper.deleteByExample(example);
            //删除以前的sku
            Example example1 = new Example(Sku.class);
            example1.createCriteria().andEqualTo("spuId",id);
            this.skuMapper.deleteByExample(example1);
            //新增sku和库存
            saveSkuAndStock(skus,id);
        }
    }

    /**
      * @Description 保存sku和库存信息
      * @Param [skus, id]
      * @return void
      **/
    private void saveSkuAndStock(List<Sku> skus, Long id){
        for (Sku sku : skus){
            if (!sku.getEnable()){
                continue;
            }
            //保存sku
            sku.setSpuId(id);
            //默认不参加任何促销
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insert(sku);

            //保存库存信息
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insert(stock);
        }
    }
}
