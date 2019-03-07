package com.chezhibao.bigdata.dbms.server.service.download;

import com.chezhibao.bigdata.dbms.server.constants.DbTypeConstants;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/10.
 */
@Component
public class DownloadFactory {

    @Autowired
    private SimpleDownloadService simpleDownloadService;
    @Autowired
    private PrestoDownloadService prestoDownloadService;

    public  DataDownload getInstance(Integer dbType) {
        switch (dbType) {
            case DbTypeConstants.MYSQL:
                return simpleDownloadService;
            case DbTypeConstants.Presto:
                return prestoDownloadService;
            default:
                return null;
        }
    }
}
