package com.chezhibao.bigdata.vo;

import com.chezhibao.bigdata.pojo.CBRColumn;
import lombok.Data;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/13.
 */
@Data
public class CBRColumnVo {
    private List<CBRColumn> columns;
    private List<Integer> delColumnIds ;
}
