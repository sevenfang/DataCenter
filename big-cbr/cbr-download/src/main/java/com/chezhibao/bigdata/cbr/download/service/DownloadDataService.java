package com.chezhibao.bigdata.cbr.download.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/12.
 */
public interface DownloadDataService {
    /**
     * 导出数据
     * @param realreportId 报表ID
     * @param params 下载参数
     * @return
     */
    List<LinkedHashMap<String,Object>> download(String realreportId, Map<String,Object> params);
}
