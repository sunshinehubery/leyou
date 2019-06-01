package com.leyou.item.service;

import com.leyou.common.pojo.BrandQueryByPageParameter;
import com.leyou.common.pojo.SpuQueryByPageParameter;
import com.leyou.common.vo.PageResult;
import com.leyou.item.bo.SkuBo;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import java.util.List;

public interface GoodsService {

    /**
      * @Description 分页查询
      * @Param [spuQueryByPageParameter]
      * @return com.leyou.common.vo.PageResult<com.leyou.item.bo.SpuBo>
      **/
    PageResult<SpuBo> querySpuByPageAndSort(SpuQueryByPageParameter spuQueryByPageParameter);

    PageResult<SkuBo> querySkuByPage(BrandQueryByPageParameter brandQueryByPageParameter);

    /**
      * @Description 保存商品
      * @Param [spu]
      * @return void
      **/
    void saveGoods(SpuBo spu);

    /**
      * @Description 根据id查询商品
      * @Param [id]
      * @return com.leyou.item.bo.SpuBo
      **/
    SpuBo queryGoodsById(Long id);

    /**
      * @Description 根据spuId查询所有的sku
      * @Param [id]
      * @return java.util.List<com.leyou.item.pojo.Sku>
      **/
    List<Sku> querySkuBySpuId(Long id);

    /**
      * @Description 更新商品
      * @Param [spuBo]
      * @return void
      **/
    void updateGoods(SpuBo spuBo);

    /**
      * @Description 商品上架下架
      * @Param [id]
      * @return void
      **/
    void goodsSoldOutPut(Long id);

    /**
      * @Description 删除商品
      * @Param [id]
      * @return void
      **/
    void deleteGoods(Long id);

    List<SkuBo> lessSku();

    List<SkuBo> notSku();

    List<SkuBo> needSku();

    SkuBo getSkuBo(Long id);

    void addPurchase(Long id, Long amount);
}
