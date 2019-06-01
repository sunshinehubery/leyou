package com.leyou.member.bo;

import com.leyou.member.pojo.Ranks;
import lombok.Data;

/**
 * @description:
 * @author: furong
 * @date: 2019/5/29 23:25
 * @Version: 1.0
 **/
@Data
public class RankBo extends Ranks {
    private long amount;
}
