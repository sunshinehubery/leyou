package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

/**
* @author: furong
* @date: 2019/4/4
* @description:
*/
@Data
public class ExceptionResult {
    private int code;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em){
        this.code = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
