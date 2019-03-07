package com.chezhibao.bigdata.msg.utils;

import com.chezhibao.bigdata.msg.bo.FtpClientBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jerry on 2018/10/11.
 */
@Slf4j
public class FtpUtils {
    public static AtomicInteger activeFtpClient = new AtomicInteger();
    public static String FTP_KEY = "ftp";
    public static String FLAG_KEY = "flag";
    public static String FTP_SEPARATOR = "/";

    /**
     * 获取ftp连接
     * @param f
     * @return
     * @throws Exception
     */
    public static Map<String, Object> connectFtp(FtpClientBO f){
        Map<String, Object> result = new HashMap<>();
        try {
            result.put(FLAG_KEY, false);
            result.put(FTP_KEY, new FTPClient());
            FTPClient ftp = (FTPClient)result.get(FTP_KEY);
            int reply;
            if (f.getPort()==null) {
                ftp.connect(f.getIpAddr(),21);
            }else{
                ftp.connect(f.getIpAddr(),f.getPort());
            }
            ftp.login(f.getUserName(), f.getPwd());
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                log.error("FTP客户端连接不可用：" + f.toString());
                return result;
            }
            ftp.setControlEncoding("UTF-8");
            result.put(FLAG_KEY, true);
            //开启了一个客户端
            activeFtpClient.addAndGet(1);
        } catch (IOException e) {
            log.error("FTP客户端连接异常：{}" , f.toString(),e);
        }
        return result;
    }

    /**
     * 关闭ftp连接
     */
    public static void closeFtp(FTPClient ftp){
        if (ftp!=null && ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
                //关闭了一个客户端
                activeFtpClient.decrementAndGet();
            } catch (IOException e) {
                log.error("关闭客户端失败",e);
            }
        }
    }

    /**
     * 跳转到ftp目录下，若路径不存在，则创建
     * @param ftp
     * @param ftpPath
     * @return
     */
    public static boolean changeOrMakeDir(FTPClient ftp, String ftpPath) {
        String[] ftpPaths = ftpPath.split(FTP_SEPARATOR);
        try {
            for (String path : ftpPaths) {
                if (!StringUtils.isEmpty(path)) {
                    if (!ftp.changeWorkingDirectory(path)) {    //跳转目录，失败则创建
                        if (!ftp.makeDirectory(path)) {         //创建目录，失败则返回失败
                            log.error("创建FTP目录失败：{}" , ftpPath);
                            return false;
                        }
                        //目录创建成功之后，继续跳转目录，失败则返回失败
                        if (!ftp.changeWorkingDirectory(path)) {
                            log.error("跳转FTP目录失败：{}" , ftpPath);
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (IOException e) {
            log.error("跳转FTP目录失败：{}" , ftpPath);
            return false;
        }
    }
}
