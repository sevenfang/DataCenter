package com.chezhibao.bigdata.datax.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
@Data
public class DataxVo {
    private JSONObject jobInfo;
    private JSONObject readerData;
    private JSONObject writerData;
}
