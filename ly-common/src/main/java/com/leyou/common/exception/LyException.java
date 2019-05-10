package com.leyou.common.exception;

import com.leyou.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
* @author: furong
* @date: 2019/4/4
* @description:
*/
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LyException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
