package com.chezhibao.bigdata.msg.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.dao.NewWareHouseDao;
import com.chezhibao.bigdata.msg.bo.FtpClientBO;
import com.chezhibao.bigdata.msg.bo.FtpFileBO;
import com.chezhibao.bigdata.msg.dao.FtpBuyerDao;
import com.chezhibao.bigdata.msg.service.FtpService;
import com.chezhibao.bigdata.msg.utils.FtpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jerry on 2018/10/11.
 */
//@Service
@Slf4j
public class FtpServiceImpl implements FtpService {
    @Autowired
    private FtpBuyerDao ftpBuyerDao;

    @Override
    public boolean upload(FtpClientBO f, String ftpPath, InputStream input) {
        Map<String, Object> ftpMap = FtpUtils.connectFtp(f);
        try {
            if ((boolean)ftpMap.get(FtpUtils.FLAG_KEY)) {   //客户端连接成功，则开始上传
                //上传文件
                FTPClient ftpClient = (FTPClient) ftpMap.get(FtpUtils.FTP_KEY);
                int index = ftpPath.lastIndexOf(FtpUtils.FTP_SEPARATOR);
                String ftpDir = ftpPath.substring(0, index);
                String ftpFile = ftpPath.substring(index + 1, ftpPath.length());
                if (FtpUtils.changeOrMakeDir(ftpClient, ftpDir)) {
                    return ftpClient.storeFile(new String(ftpFile.getBytes("UTF-8"),"iso-8859-1"), input);
                } else {
                    return false;
                }
            } else {    //如果客户端连接为false，则直接返回false，上传失败
                return false;
            }
        } catch (IOException e) {
            log.error("FTP上传文件失败：{}" , ftpPath,e);
            return false;
        } finally {
            //关闭FTP连接
            FtpUtils.closeFtp((FTPClient)ftpMap.get(FtpUtils.FTP_KEY));
        }
    }

    @Override
    public boolean download(FtpClientBO f, String ftpPath, OutputStream output) {
        Map<String, Object> ftpMap = FtpUtils.connectFtp(f);
        try {
            if ((boolean)ftpMap.get(FtpUtils.FLAG_KEY)) {   //客户端连接成功，则开始上传
                //上传文件
                FTPClient ftpClient = (FTPClient) ftpMap.get(FtpUtils.FTP_KEY);
                ftpClient.retrieveFile(new String(ftpPath.getBytes("UTF-8"),"iso-8859-1"), output);
                return true;
            } else {    //如果客户端连接为false，则直接返回false，上传失败
                return false;
            }
        } catch (IOException e) {
            log.error("FTP下载文件失败：{}" , ftpPath,e);
            return false;
        } finally {
            //关闭FTP连接
            FtpUtils.closeFtp((FTPClient)ftpMap.get(FtpUtils.FTP_KEY));
        }
    }

    @Override
    public List<FtpFileBO> selectByUser(FtpClientBO f, int userId) {
        Map<String, Object> ftpMap = FtpUtils.connectFtp(f);
        List<FtpFileBO> result = new ArrayList<>();
        try {
            if ((boolean)ftpMap.get(FtpUtils.FLAG_KEY)) {   //客户端连接成功，则开始上传
                //上传文件
                FTPClient ftpClient = (FTPClient) ftpMap.get(FtpUtils.FTP_KEY);
                FTPFile[] ftpFiles = ftpClient.listFiles(userId + "");
                List<String> ids = new ArrayList<>();
                for (FTPFile ftpFile : ftpFiles) {
                    String id = ftpFile.getName();
                    //查数据
                    ids.add(id);
                    result.add(new FtpFileBO(ftpFile, id));
                }
                //通过ids查询names
                List<Map<String, Object>> maps = ftpBuyerDao.selectBuyerByUser(ids);
                Map<String, Object> idNames = new HashMap<>();
                for (Map<String, Object> map : maps) {
                    idNames.put(map.get("id").toString(), map.get("name"));
                }
                //替换result中的值
                for (FtpFileBO ftpFileBO : result) {
                    Object o = idNames.get(ftpFileBO.getShowName());
                    if (o != null) {
                        ftpFileBO.setShowName(o.toString());
                    }
                }
                return result;
            } else {    //如果客户端连接为false，则直接返回false，上传失败
                return result;
            }
        } catch (IOException e) {
            log.error("FTP查询用户文件夹失败：{}" , userId,e);
            return result;
        } finally {
            //关闭FTP连接
            FtpUtils.closeFtp((FTPClient)ftpMap.get(FtpUtils.FTP_KEY));
        }
    }

    @Override
    public List<FtpFileBO> selectByPath(FtpClientBO f, String path) {
        Map<String, Object> ftpMap = FtpUtils.connectFtp(f);
        List<FtpFileBO> result = new ArrayList<>();
        try {
            if ((boolean)ftpMap.get(FtpUtils.FLAG_KEY)) {   //客户端连接成功，则开始上传
                //上传文件
                FTPClient ftpClient = (FTPClient) ftpMap.get(FtpUtils.FTP_KEY);
                FTPFile[] ftpFiles = ftpClient.listFiles(path);
                for (FTPFile ftpFile : ftpFiles) {
                    result.add(new FtpFileBO(ftpFile, ftpFile.getName()));
                }
                return result;
            } else {    //如果客户端连接为false，则直接返回false，上传失败
                return result;
            }
        } catch (IOException e) {
            log.error("FTP查询文件夹失败：{}" , path,e);
            return result;
        } finally {
            //关闭FTP连接
            FtpUtils.closeFtp((FTPClient)ftpMap.get(FtpUtils.FTP_KEY));
        }
    }

    @Override
    public Map<String, String> getBuyerByUser(FtpClientBO f, int userId) {
        Map<String, Object> ftpMap = FtpUtils.connectFtp(f);
        Map<String, String> result = new HashMap<>();
        try {
            if ((boolean)ftpMap.get(FtpUtils.FLAG_KEY)) {   //客户端连接成功，则开始上传
                //上传文件
                FTPClient ftpClient = (FTPClient) ftpMap.get(FtpUtils.FTP_KEY);
                FTPFile[] ftpFiles = ftpClient.listFiles(userId + "");
                List<String> ids = new ArrayList<>();
                for (FTPFile ftpFile : ftpFiles) {
                    String id = ftpFile.getName();
                    //准备查数据
                    ids.add(id);
                    result.put(id, id);
                }
                //通过ids查询names，并替换result中的值
                List<Map<String, Object>> maps = ftpBuyerDao.selectBuyerByUser(ids);
                for (Map<String, Object> map : maps) {
                    if (map.get("id") != null && map.get("name") != null) {
                        result.put(map.get("id").toString(), map.get("name").toString());
                    }
                }
                return result;
            } else {    //如果客户端连接为false，则直接返回false，上传失败
                return result;
            }
        } catch (IOException e) {
            log.error("FTP商户失败：{}" , userId,e);
            return result;
        } finally {
            //关闭FTP连接
            FtpUtils.closeFtp((FTPClient)ftpMap.get(FtpUtils.FTP_KEY));
        }
    }

    @Override
    public Map<String, Object> addBuyerByUser(FtpClientBO f, int userId, String buyerName) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", false);
        List<Map<String, Object>> maps = ftpBuyerDao.selectBuyerByBuyerName(buyerName);
        if (maps.size() != 1 || maps.get(0).get("id") == null) {
            return result;
        }
        Object id = maps.get(0).get("id");

        Map<String, Object> ftpMap = FtpUtils.connectFtp(f);
        try {
            if ((boolean)ftpMap.get(FtpUtils.FLAG_KEY)) {   //客户端连接成功，则开始上传
                //上传文件
                FTPClient ftpClient = (FTPClient) ftpMap.get(FtpUtils.FTP_KEY);
                boolean b = FtpUtils.changeOrMakeDir(ftpClient, userId + FtpUtils.FTP_SEPARATOR + id);
                HashMap<Object, Object> data = new HashMap<>();
                data.put("value", id);
                data.put("title", buyerName);
                result.put("data", data);
                result.put("result", b);
                return result;
            } else {    //如果客户端连接为false，则直接返回false，上传失败
                return result;
            }
        } catch (Exception e) {
            log.error("FTP新建商户失败：{}" , userId,e);
            return result;
        } finally {
            //关闭FTP连接
            FtpUtils.closeFtp((FTPClient)ftpMap.get(FtpUtils.FTP_KEY));
        }
    }

    @Override
    public boolean delete(FtpClientBO f, String ftpPath) {
        Map<String, Object> ftpMap = FtpUtils.connectFtp(f);
        try {
            if ((boolean)ftpMap.get(FtpUtils.FLAG_KEY)) {   //客户端连接成功，则开始上传
                //上传文件
                FTPClient ftpClient = (FTPClient) ftpMap.get(FtpUtils.FTP_KEY);
                return ftpClient.deleteFile(ftpPath);
            } else {    //如果客户端连接为false，则直接返回false，上传失败
                return false;
            }
        } catch (Exception e) {
            log.error("FTP下载文件失败：{}" , ftpPath,e);
            return false;
        } finally {
            //关闭FTP连接
            FtpUtils.closeFtp((FTPClient)ftpMap.get(FtpUtils.FTP_KEY));
        }
    }
}
