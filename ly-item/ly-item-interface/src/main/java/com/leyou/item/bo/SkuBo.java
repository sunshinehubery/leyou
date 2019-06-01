package com.leyou.item.bo;

import lombok.Data;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/31 0:28
 * @Version: 1.0
 **/
@Data
public class SkuBo {
    private  Long id;
    private String title;
    private Long price;
    private Long stock;
}
