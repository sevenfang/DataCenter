package com.chezhibao.bigdata.vo;

import com.chezhibao.bigdata.pojo.CBRParam;
import lombok.Data;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/13.
 */
@Data
public class CbrParamVO {
    private List<CBRParam> params;
    private List<Integer> delParamIds;
}
