package com.chezhibao.bigdata.dbms.server.download.export.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.chezhibao.bigdata.dbms.server.auth.bo.DbAuthBO;
import com.chezhibao.bigdata.dbms.server.auth.service.SqlVerificationService;
import com.chezhibao.bigdata.dbms.server.download.bo.TaskInfoBO;
import com.chezhibao.bigdata.dbms.server.download.config.TaskConfig;
import com.chezhibao.bigdata.dbms.server.download.export.ExportServer;
import com.chezhibao.bigdata.dbms.server.download.export.PrestoDataExportService;
import com.chezhibao.bigdata.dbms.server.download.service.DataFileService;
import com.chezhibao.bigdata.dbms.server.download.service.DownloadTaskService;
import com.chezhibao.bigdata.dbms.server.enums.DbmsServerEnums;
import com.chezhibao.bigdata.dbms.server.exception.DbmsServerEception;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
@Service
@Slf4j
public class PrestoDataExportServiceImpl implements PrestoDataExportService {


    private DataFileService dataFileService;

    @Resource(name = "AllDs")
    private HashMap<String, Object> allDs;

    private ExecutorService executorService;

    private DownloadTaskService downloadTaskService;

    private SqlVerificationService sqlVerificationService;

    private TaskConfig taskConfig;

    public PrestoDataExportServiceImpl(DataFileService dataFileService,
                                       DownloadTaskService downloadTaskService,
                                       SqlVerificationService sqlVerificationService,
                                       TaskConfig taskConfig) {
        this.dataFileService = dataFileService;
        this.downloadTaskService = downloadTaskService;
        this.sqlVerificationService = sqlVerificationService;
        this.taskConfig = taskConfig;
        executorService = new ThreadPoolExecutor(   taskConfig.getCoreSize(),
                                                    taskConfig.getMaxSize(),
                                                    0L,
                                                    TimeUnit.MILLISECONDS,
                                                    new LinkedBlockingDeque<>(),
                                                    new PrestoThreadFactory());
    }
    @Override
    public void export(String fileName) {
        log.info("【数据查询服务】导出数据,文件名称：{}",fileName);
        TaskInfoBO taskByFileName = downloadTaskService.getTaskByFileName(fileName);
        String sql = taskByFileName.getSql();
        String datasource = taskByFileName.getDatasource();
        DbAuthBO dbAuthBO = new DbAuthBO();
        dbAuthBO.setSql(sql);
        dbAuthBO.setDatasource(datasource);
        dbAuthBO.setUserId(taskByFileName.getUserId());
        sqlVerificationService.verifySql(dbAuthBO);
        DruidDataSource druidDataSource = (DruidDataSource) allDs.get(datasource);

        Future<PrestoQueryResult> submit;
        PrestoQueryResult queryResult;
        try {
            DruidPooledConnection connection = druidDataSource.getConnection();
             Statement ps = connection.createStatement();
            ExportServer exportServer = new ExportServer();
            exportServer.setStatement(ps);
            exportServer.setStart(true);
            STATEMENT_CONCURRENT_HASH_MAP.put(fileName,exportServer);
            submit = executorService.submit(() -> {
               PrestoQueryResult result;
               try {
                   result = PrestoQueryResult.getInstance(ps.execute(sql));
               } catch (SQLException e) {
                   log.error("【数据查询服务】导出presto数据出错了!,sql:{}", sql,e);
                   try {
                       ps.close();
                   } catch (SQLException w) {
                       log.error("【数据查询服务】导出presto数据出错了！sql:{}", sql,e);
                   }
                   result = PrestoQueryResult.getInstance(false);
                   result.setE(e);
               }
               return result;
           });
            Thread.sleep(1000);
            queryResult = submit.get();
            if(queryResult!=null && queryResult.isSuccess){
                ResultSet resultSet = ps.getResultSet();
                dataFileService.writeData(fileName,resultSet);
            }else{
                throw new DbmsServerEception(DbmsServerEnums.SQL_HANDLER_EXCEPTION,queryResult.getE());
            }
        }catch (Exception e){
            log.error("【数据查询服务】导出数据出错了！！",e);
        }
    }
    /**
     * 任务创建工厂
     */
    class PrestoThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }

    @Data
    public static class PrestoQueryResult{
        private Boolean isSuccess;
        private Exception e;

        private PrestoQueryResult(Boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public static PrestoQueryResult getInstance(Boolean isSuccess){
            return new PrestoQueryResult(isSuccess);
        }

    }
}
