package com.leyou.item.web;

import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.Specification;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/1 13:53
 * @Version: 1.0
 **/
@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    /**
      * @Description 根据category id查询规格参数模板
      * @Param [id]
      * @return org.springframework.http.ResponseEntity<java.lang.String>
      **/
    @GetMapping("{id}")
    public ResponseEntity<String> querySpecificationByCategoryId(@PathVariable("id") Long id){
        Specification specification = specificationService.queryById(id);
        return ResponseEntity.ok(specification.getSpecifications());
    }

    /**
      * @Description 添加规格参数模板
      * @Param [specification]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @PostMapping
    public ResponseEntity<Void> saveSpecification(Specification specification){
        specificationService.saveSpecification(specification);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
      * @Description 修改规格参数模板
      * @Param [specification]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @PutMapping
    public ResponseEntity<Void> updateSpecification(Specification specification){
        specificationService.updateSpecification(specification);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
      * @Description 删除规格参数模板
      * @Param [id]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSpecification(@PathVariable Long id){
        Specification specification =new Specification();
        specification.setCategoryId(id);
        specificationService.deleteSpecification(specification);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
      * @Description 通过商品分类id查询商品规格参数组信息
      * @Param [cid]
      * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecGroup>>
      **/
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryByCid(@PathVariable Long cid){
        return ResponseEntity.ok(specificationService.queryByCid(cid));
    }

    /**
      * @Description 添加商品规格参数组
      * @Param [specGroup]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @PostMapping("group")
    public ResponseEntity<Void> saveSpecGroup(SpecGroup specGroup){
        specificationService.saveSpecGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("group")
    public ResponseEntity<Void> updateSpecGroup(SpecGroup specGroup){
        specificationService.updateGroup(specGroup);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
      * @Description 根据参数组id查询具体参数信息
      * @Param [gid]
      * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecParam>>
      **/
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryByGroupId(@RequestParam("gid") Long gid){
        List<SpecParam> specParams = specificationService.queryByGroupId(gid);
        return ResponseEntity.ok(specParams);
    }

    @PostMapping("param")
    public ResponseEntity<Void> saveSpecParam(SpecParam specParam){
        specificationService.saveSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
