package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @author: furong
* @date: 2019/4/4
* @description:
*/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    BRAND_NOT_FIND(404, "品牌不存在!"),
    CATEGORY_NOT_FIND(404,"商品分类未查询到!"),
    BRAND_SAVE_ERROR(500, "新增品牌失败!"),
    BRAND_UPDATE_ERROR(500,"更新品牌信息失败!"),
    BRAND_DELETE_ERROR(500,"删除品牌信息失败"),
    UPLOAD_FILE_ERROR(500, "文件上传失败!"),
    INVALID_FILE_TYPE(400, "无效的文件类型!"),
    LAST_CATEGORY_NOT_FOUND(404,"数据库不存在数据!"),
    CATEGORY_SAVE_ERROR(500,"新增商品类目失败!"),
    CATEGORY_UPDATE_ERROR(500,"更新商品类目信息失败!"),
    CATEGORY_NAME_NOT_FOUND(404,"商品类目名称没有找到!"),
    CATEGORY_BRAND_SAVE_ERROR(500,"新增中间表信息失败!"),
    CATEGORY_BRAND_DELETE_ERROR(500,"删除中间表信息失败!"),
    GOODS_NOT_FOUND(404,"未查询到商品!"),
    SKU_SAVE_ERROR(500,"sku添加失败!"),
    STOCK_SAVE_ERROR(500,"库存信息添加失败!"),
    GOODS_SAVE_ERROR(500,"商品添加失败!"),
    USER_NOT_FOUND(404,"查询用户失败"),
    ;
    private int code;
    private String msg;
}
