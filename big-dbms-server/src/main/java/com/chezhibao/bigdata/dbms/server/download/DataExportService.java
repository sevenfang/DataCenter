package com.chezhibao.bigdata.dbms.server.download;
/**
 * 数据导出服务
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
public interface DataExportService {
    /**
     * 导出数据
     * @param fileName
     * @return
     */
    void export(String fileName);
}
