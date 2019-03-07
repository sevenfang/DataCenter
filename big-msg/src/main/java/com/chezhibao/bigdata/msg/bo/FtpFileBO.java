package com.chezhibao.bigdata.msg.bo;

import lombok.Data;
import org.apache.commons.net.ftp.FTPFile;

import java.util.Date;

/**
 * Created by jerry on 2018/10/11.
 * FTP文件信息BO
 */
@Data
public class FtpFileBO {
    /**
     * 系统实际的文件名
     */
    private String fileName;

    /**
     * 前端展示的文件名
     */
    private String showName;

    /**
     * 是否文件（否则表示：是文件夹）
     */
    private boolean isFile;

    /**
     * 文件大小
     */
    private long size;

    /**
     * 文件创建时间
     */
    private Date createTime;

    public FtpFileBO(FTPFile ftpFile, String showName) {
        this.showName = showName;
        this.fileName = ftpFile.getName();
        this.isFile = ftpFile.isFile();
        this.size = ftpFile.getSize();
        this.createTime = ftpFile.getTimestamp().getTime();
    }
}
