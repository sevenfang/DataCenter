package com.chezhibao.bigdata.dbms.server.download.export;

import com.chezhibao.bigdata.dbms.server.download.DataExportService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
public interface PrestoDataExportService extends DataExportService {
    ConcurrentHashMap<String,ExportServer> STATEMENT_CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();
}
