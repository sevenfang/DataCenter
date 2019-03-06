package com.chezhibao.bigdata.cbr.download.service;

import com.chezhibao.bigdata.cbr.download.bo.DownloadColumnBO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/11.
 */
public interface DownloadColumnService {
    List<DownloadColumnBO> getAllColumns(String realreportId);
    Boolean saveColumn(DownloadColumnBO columnBO);
    Boolean saveOrUpdateColumn(DownloadColumnBO columnBO);
    Boolean updateColumn(DownloadColumnBO columnBO);
    Boolean delColumn(Integer id);
}
