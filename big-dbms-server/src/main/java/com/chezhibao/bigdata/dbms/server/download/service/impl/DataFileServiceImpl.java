package com.chezhibao.bigdata.dbms.server.download.service.impl;

import com.chezhibao.bigdata.dbms.server.download.bo.TaskInfoBO;
import com.chezhibao.bigdata.dbms.server.download.config.TaskConfig;
import com.chezhibao.bigdata.dbms.server.download.export.ExportServer;
import com.chezhibao.bigdata.dbms.server.download.export.PrestoDataExportService;
import com.chezhibao.bigdata.dbms.server.download.service.DataFileService;
import com.chezhibao.bigdata.dbms.server.download.service.DownloadTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
@Service
@Slf4j
public class DataFileServiceImpl implements DataFileService {

    @Autowired
    private TaskConfig taskConfig;

    @Autowired
    private DownloadTaskService downloadTaskService;

    @Override
    public Boolean appendData( ResultSet resultSet, List<String> title, String fileName) throws Exception {
        ExportServer exportServer = PrestoDataExportService.STATEMENT_CONCURRENT_HASH_MAP.get(fileName);
        if (exportServer == null) {
            return false;
        }
        String filePath = taskConfig.getFilePath(fileName) + ".csv";
        //初始化csvformat
        CSVFormat csvformat = CSVFormat.DEFAULT.withRecordSeparator("\n");
        //加上Bom头  不然csv文件内容会乱码
        FileOutputStream out = new FileOutputStream(filePath,true);
        out.write(new   byte []{( byte ) 0xEF ,( byte ) 0xBB ,( byte ) 0xBF });
        out.flush();
        //创建FileWriter对象
        FileWriter fileWriter = new FileWriter(filePath, true);
        //创建CSVPrinter对象
        CSVPrinter printer = new CSVPrinter(fileWriter, csvformat);
        //写入列头数据
        printer.printRecord(title);
        List<String> lineData;
        while (exportServer.getStart() && resultSet.next()) {
            lineData = new ArrayList<>();
            for (String col : title) {
                Object object = resultSet.getObject(col);
                String temp = object == null ? "" : object.toString();
                lineData.add(temp);
            }
            printer.printRecord(lineData);
            printer.flush();
        }
        printer.close(true);
        fileWriter.close();
        //删除内存中的任务信息
        PrestoDataExportService.STATEMENT_CONCURRENT_HASH_MAP.remove(fileName);
        return true;
    }

    @Override
    public Boolean writeData(String fileName, ResultSet resultSet) {
        List<String> title = new ArrayList<>();
//        FileOutputStream fileOutputStream = null;
        if (resultSet == null) {
            return false;
        }
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                title.add(columnName);
            }
            createFile(taskConfig.getFilePath(fileName));
            appendData(resultSet, title, fileName);
            downloadTaskService.complete(fileName);
        } catch (Exception e) {
            log.error("【数据查询服务】查询出错了！", e);
        }
        return true;
    }

    @Override
    public File createFile(String filePath) {
        File f = new File(filePath + ".csv");
        if (!f.exists()) {
            log.info("【数据查询服务】文件不存在！创建文件：{}", f.getPath());
            try {
                boolean newFile = f.createNewFile();
            } catch (Exception e) {
                log.error("【数据查询服务】创建下载文件失败了！！path：{}", filePath, e);
            }
        }
        return f;
    }

    @Override
    public Boolean deleteFile(String name) {
        String filePath = taskConfig.getFilePath(name) + ".csv";
        File f = new File(filePath);
        if (f.exists()) {
            return f.delete();
        }
        return false;
    }

    @Override
    public void download(TaskInfoBO taskInfoBO) {

    }
}
