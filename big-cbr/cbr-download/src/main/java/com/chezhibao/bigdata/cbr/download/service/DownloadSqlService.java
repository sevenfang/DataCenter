package com.chezhibao.bigdata.cbr.download.service;

import com.chezhibao.bigdata.cbr.download.bo.DownloadSqlBO;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/5.
 */
public interface DownloadSqlService {
    List<DownloadSqlBO> getAllSql(String realreportId);

    /**
     * 支持存在更新
     * @param downloadSqlBO
     * @return
     */
    Boolean saveSql(DownloadSqlBO downloadSqlBO);
    Boolean saveOrUpdateSql(DownloadSqlBO downloadSqlBO);
    Boolean updateSql(DownloadSqlBO downloadSqlBO);
    Boolean delSql(Integer id);
}
