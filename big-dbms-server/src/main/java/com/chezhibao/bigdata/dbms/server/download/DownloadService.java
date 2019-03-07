package com.chezhibao.bigdata.dbms.server.download;

import com.chezhibao.bigdata.dbms.server.download.bo.DownloadBO;
import com.chezhibao.bigdata.dbms.server.download.bo.TaskInfoBO;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
public interface DownloadService {
    /**
     * 下载文件
     * @param taskInfoBO
     */
    void download(TaskInfoBO taskInfoBO);
}
