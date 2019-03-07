package com.chezhibao.bigdata.msg.service;

import com.chezhibao.bigdata.msg.bo.FtpClientBO;
import com.chezhibao.bigdata.msg.bo.FtpFileBO;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by jerry on 2018/10/11.
 */
public interface FtpService {
    /**
     * upload 上传文件到FTP，注意：此处用InputStream
     */
    boolean upload(FtpClientBO f, String ftpPath, InputStream input);

    boolean download(FtpClientBO f, String ftpPath, OutputStream output);

    List<FtpFileBO> selectByUser(FtpClientBO f, int userId);

    List<FtpFileBO> selectByPath(FtpClientBO f, String path);

    Map<String, String> getBuyerByUser(FtpClientBO f, int userId);

    Map<String, Object> addBuyerByUser(FtpClientBO f, int userId, String buyerName);

    boolean delete(FtpClientBO f, String ftpPath);
}
