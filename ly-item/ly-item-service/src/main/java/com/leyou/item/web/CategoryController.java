package com.leyou.item.web;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @author: furong
* @date: 2019/4/5
* @description:
*/
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父节点查询商品分类
     * @param pid
     * @return
     * */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryByPid(@RequestParam("pid") Long pid){
        //如果pid=-1需要获取数据库最后一条信息
        if(pid == -1) {
            List<Category> last = categoryService.queryLast();
            return ResponseEntity.ok(last);
        }else {
            List<Category> list = categoryService.queryCategoryByPid(pid);
            if (list == null) {
                //没有找到返回404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(list);
        }
    }

    /**
      * @Description 根据品牌id查询商品分类
      * @Param [bid]
      * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Category>>
      **/
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryCategoryByBrandId(@PathVariable("bid") Long bid){
        List<Category> list = this.categoryService.queryCategoryByBrandId(bid);
        if(list == null || list.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }

    /**
      * @Description 保存
      * @Param [category]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @PostMapping
    public ResponseEntity<Void> saveCategory(Category category){
        this.categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
      * @Description 更新
      * @Param [category]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @PutMapping
    public ResponseEntity<Void> updateCategory(Category category){
        this.categoryService.updateCategory(category);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
      * @Description 删除
      * @Param [id]
      * @return org.springframework.http.ResponseEntity<java.lang.Void>
      **/
    @DeleteMapping("cid/{cid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("cid") Long id){
        this.categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
      * @Description 根据分类id集合查询类目名称
      * @Param [ids]
      * @return org.springframework.http.ResponseEntity<java.util.List<java.lang.String>>
      **/
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids") List<Long> ids){
        List<String> list = this.categoryService.queryNameByIds(ids);
        if(list == null || list.size() <1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.ok(list);
        }
    }

    /**
      * @Description 根据分类id集合查询分类信息
      * @Param [ids]
      * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Category>>
      **/
    @GetMapping("all")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){
        List<Category> list = categoryService.queryCategoryByIds(ids);
        if(list == null || list.size() <1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("all/level/{cid3}")
    public ResponseEntity<List<Category>> queryAllCategoryLevelByCid3(@RequestParam("cid3") Long id){
        List<Category> list = this.categoryService.queryAllCategoryLevelByCid3(id);
        if(list == null || list.size() <1){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.ok(list);
        }
    }
}
