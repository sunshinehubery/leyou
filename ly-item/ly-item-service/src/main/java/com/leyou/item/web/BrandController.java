package com.leyou.item.web;

import com.leyou.common.pojo.BrandQueryByPageParameter;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
* @author: furong
* @date: 2019/4/6
* @description:
*/
@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /** 
      * @Description 分页查询品牌
      * @Param [page, rows, sortBy, desc, key]
      * @return org.springframework.http.ResponseEntity<com.leyou.common.vo.PageResult<com.leyou.item.pojo.Brand>>
      **/
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key
    ){
        BrandQueryByPageParameter brandQueryByPageParameter = new BrandQueryByPageParameter(page,rows,sortBy,desc,key);
        return ResponseEntity.ok(brandService.queryBrandByPage(brandQueryByPageParameter));
    }

    /**
      * @Description 新增品牌
      * @Param [brand, cid]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cid") List<Long> cid){
        brandService.saveBrand(brand, cid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
      * @Description 更新品牌信息
      * @Param [brand, cid]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @PutMapping
    public ResponseEntity<Void> updateBrand(Brand brand,@RequestParam("cid") List<Long> cid){
        brandService.updateBrand(brand,cid);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
      * @Description 删除tb_category_brand中的数据
      * @Param [bid]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @DeleteMapping("cid_bid/{bid}")
    public ResponseEntity<Void> deleteByBrandIdInCategoryBrand(@PathVariable("bid") Long bid){
        brandService.deleteByBrandIdInCategoryBrand(bid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /** 
      * @Description 删除tb_brand中的数据,单个删除、多个删除二合一
      * @Param [bid]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @DeleteMapping("bid/{bid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable("bid") String bid){
        String separator = "-";
        if(bid.contains(separator)){
            String[] ids = bid.split(separator);
            for(String id:ids){
                brandService.deleteBrand(Long.parseLong(id));
            }
        }else{
            brandService.deleteBrand(Long.parseLong(bid));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /** 
      * @Description 根据category的id查询品牌信息
      * @Param [cid]
      * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Brand>>
      **/
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCategoryId(@PathVariable("cid") Long cid){
        List<Brand> list = brandService.queryBrandByCategoryId(cid);
        if(list == null || list.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }

    /**
      * @Description 根据品牌id结合，查询品牌信息
      * @Param [ids]
      * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Brand>>
      **/
    @GetMapping("list")
    public ResponseEntity<List<Brand>> queryBrandByIds(@RequestParam("ids") List<Long> ids){
        List<Brand> list = brandService.queryBrandByBrandIds(ids);
        if(list == null || list.size() < 1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }
}
