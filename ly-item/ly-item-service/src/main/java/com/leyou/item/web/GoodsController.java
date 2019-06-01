package com.leyou.item.web;

import com.leyou.common.pojo.BrandQueryByPageParameter;
import com.leyou.common.pojo.SpuQueryByPageParameter;
import com.leyou.common.vo.PageResult;
import com.leyou.item.bo.SkuBo;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.service.GoodsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/1 9:46
 * @Version: 1.0
 **/
@RestController
@RequestMapping("goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
      * @Description 分页查询
      * @Param [page, rows, sortBy, desc, key, saleable]
      * @return org.springframework.http.ResponseEntity<com.leyou.common.vo.PageResult<com.leyou.item.bo.SpuBo>>
      **/
    @RequestMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") boolean desc,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", defaultValue = "true") Boolean saleable){
        SpuQueryByPageParameter spuQueryByPageParameter = new SpuQueryByPageParameter(page,rows,sortBy,desc,key,saleable);
        return ResponseEntity.ok(this.goodsService.querySpuByPageAndSort(spuQueryByPageParameter));
    }

    @GetMapping("sku/page")
    public ResponseEntity<PageResult<SkuBo>> querySkuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key){
        BrandQueryByPageParameter brandQueryByPageParameter = new BrandQueryByPageParameter(page,rows,sortBy,desc,key);
        return ResponseEntity.ok(goodsService.querySkuByPage(brandQueryByPageParameter));
    }

    /**
      * @Description 创建保存商品
      * @Param [spu]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @PostMapping
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spu){
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
      * @Description 通过spuId查询单个商品信息
      * @Param [id]
      * @return org.springframework.http.ResponseEntity<com.leyou.item.bo.SpuBo>
      **/
    @GetMapping("spu/{id}")
    public ResponseEntity<SpuBo> queryGoodsById(@PathVariable("id") Long id){
        SpuBo spuBo = goodsService.queryGoodsById(id);
        if (spuBo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(spuBo);
    }

    /**
      * @Description 通过spuId查询实际商品集sku
      * @Param [id]
      * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Sku>>
      **/
    @GetMapping("sku/list/{id}")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@PathVariable("id") Long id){
        List<Sku> skuList = goodsService.querySkuBySpuId(id);
        if(skuList == null || skuList.size()<1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else {
            return ResponseEntity.ok(skuList);
        }
    }

    /**
     * 修改商品
     * @param spuBo
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spuBo){
        this.goodsService.updateGoods(spuBo);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
      * @Description 商品上架与下架
      * @Param [id]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @PutMapping("spu/outPut/{id}")
    public ResponseEntity<Void> goodsSoldOutPut(@PathVariable("id") String id){
        String separator = "-";
        if(id.contains(separator)){
            String[] ids = id.split(separator);
            for(String sid:ids){
                goodsService.goodsSoldOutPut(Long.parseLong(sid));
            }
        }else{
            goodsService.goodsSoldOutPut(Long.parseLong(id));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
      * @Description  删除商品
      * @Param [id]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @DeleteMapping("spu/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") String id){
        String separator = "-";
        if(id.contains(separator)){
            String[] ids = id.split(separator);
            for(String sid:ids){
                goodsService.deleteGoods(Long.parseLong(sid));
            }
        }else {
            goodsService.deleteGoods(Long.parseLong(id));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("lessStock")
    public ResponseEntity<List<SkuBo>> lessStock(){
        return ResponseEntity.ok(goodsService.lessSku());
    }

    @GetMapping("notStock")
    public ResponseEntity<List<SkuBo>> notStock(){
        return ResponseEntity.ok(goodsService.notSku());
    }

    @GetMapping("needStock")
    public ResponseEntity<List<SkuBo>> needStock(){
        return ResponseEntity.ok(goodsService.needSku());
    }

    @GetMapping("getStock")
    public ResponseEntity<SkuBo> getSkuBo(@Param("id") Long id){
        return ResponseEntity.ok(goodsService.getSkuBo(id));
    }

    @PostMapping("purchase")
    public ResponseEntity<Void> addPurchase(@RequestParam(value ="id") String id,@RequestParam(value = "stock") String stock){
        goodsService.addPurchase(Long.parseLong(id),Long.parseLong(stock));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
