package com.chezhibao.bigdata.dbms.server.download.service;

import com.chezhibao.bigdata.dbms.server.download.DownloadService;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
public interface DataFileService extends  DownloadService {

    /**
     * 追加数据到文件
     * @return
     */
    Boolean appendData( ResultSet resultSet, List<String> title,String fileName)throws Exception;


    /**
     * 追加数据到文件
     * @param fileName 写入的文件
     * @return
     */
    Boolean writeData(String fileName,ResultSet resultSet);

    /**
     * 创建文件
     * @param filePath 文件的路径
     * @return
     */
    File createFile(String filePath);

    /**
     * 删除文件
     * @param path
     * @return
     */
    Boolean deleteFile(String path);
}
