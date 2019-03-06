package com.chezhibao.bigdata.cbr.download.vo;

import lombok.Data;

import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/12.
 */
@Data
public class DownloadDataVO {
    private String realreportId;
    private Map<String,Object> params;
}
