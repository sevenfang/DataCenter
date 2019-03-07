package com.chezhibao.bigdata.msg.controller;

import ch.qos.logback.core.util.TimeUtil;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.msg.bo.FtpClientBO;
import com.chezhibao.bigdata.msg.bo.FtpFileBO;
import com.chezhibao.bigdata.msg.config.FtpConfiguration;
import com.chezhibao.bigdata.msg.service.FtpService;
import com.chezhibao.bigdata.msg.utils.FtpUtils;
import com.chezhibao.bigdata.msg.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by jerry on 2018/10/11.
 */
//@RestController
@Slf4j
@RequestMapping("/msg/ftp")
public class FtpUploadController {

    @Autowired
    private FtpConfiguration ftpConfiguration;
    @Autowired
    private FtpService ftpService;

    /**
     * 查看目前未关闭的FTPclient有几个
     * @return
     */
    @RequestMapping("/getActiveFTP")
    public BigdataResult getActiveFTP() {
        return BigdataResult.build(200, "OK", FtpUtils.activeFtpClient);
    }

    /**
     * 给用户新增商户使用的端口
     * @param userId
     * @param buyerName 商户名称必须与数据库 t_buyer中的name完全一致，否则无法新增
     * @return
     */
    @RequestMapping("/addBuyer")
    public BigdataResult addBuyer(@CookieValue("LOGIN_USER_ID") Integer userId, String buyerName) {
        if (userId == null) {
            return BigdataResult.build(500, "找不到该用户");
        }
        FtpClientBO bo = new FtpClientBO(ftpConfiguration.getClient());
        Map<String, Object> map = ftpService.addBuyerByUser(bo, userId, buyerName);
        if ((boolean)map.get("result")) {
            return BigdataResult.build(200, "创建成功", map.get("data"));
        } else {
            return BigdataResult.build(404, "新增失败，请确认商户名称是否正确");
        }
    }

    /**
     * 获取当前用户所维护的商户信息
     * @param userId
     * @return  Map<String, String> buyers  key:商户ID,value:商户名称
     */
    @RequestMapping("/getBuyer")
    public BigdataResult getBuyer(@CookieValue("LOGIN_USER_ID") Integer userId) {
        if (userId == null) {
            return BigdataResult.build(500, "找不到该用户");
        }
        FtpClientBO bo = new FtpClientBO(ftpConfiguration.getClient());
        Map<String, String> buyers = ftpService.getBuyerByUser(bo, userId);
        return BigdataResult.build(200, "查询成功", buyers);
    }

    /**
     * 文件上传的http接口
     * @param multipartFile
     * @param buyerId
     * @return
     */
    @RequestMapping("/upload")
    public BigdataResult upload(MultipartFile multipartFile, int buyerId, @CookieValue("LOGIN_USER_ID") Integer userId) {

        String originalFilename = null;
        InputStream inputStream = null;
        if (userId == null) {
            return BigdataResult.build(500, "找不到该用户");
        }
        String dateStr = TimeUtils.getStringFromDate(new Date(), TimeUtils.FORMAT_DATE);
        try {
            try {
                //判断文件名，为了不影响文件路径的创建
                originalFilename = multipartFile.getOriginalFilename();
                if (originalFilename.indexOf("/") != -1 && originalFilename.indexOf("\\") != -1) {
                    return BigdataResult.build(500, "文件名不能包含/或者\\");
                }
                inputStream = multipartFile.getInputStream();
            } catch (Exception e) {
                return BigdataResult.build(500, "获取上传文件的信息错误");
            }

            FtpClientBO bo = new FtpClientBO(ftpConfiguration.getClient());

            StringBuilder FtpPath = new StringBuilder();
            FtpPath.append(userId).append(FtpUtils.FTP_SEPARATOR)
                    .append(buyerId).append(FtpUtils.FTP_SEPARATOR)
                    .append(dateStr.substring(0, 6)).append(FtpUtils.FTP_SEPARATOR)
                    .append(dateStr.substring(6, 8)).append(FtpUtils.FTP_SEPARATOR)
                    .append(originalFilename);

            if (ftpService.upload(bo, FtpPath.toString(), inputStream)) {
                return BigdataResult.ok();
            } else {
                return BigdataResult.build(500, "上传失败");
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
        }
    }

    /**
     * 展示FTP文件使用 userId+path为实际路径
     * @param path 以/开头   传null时，默认查询userId下属路径（必须为null，非null时不会查询buyerId对应的buyerName）
     * @param userId
     * @return List<FtpFileBO>  内含showName和fileName showName展示前端 fileName前后端交互使用。其中isFile:是否文件（否则表示：是文件夹）
     */
    @RequestMapping("/select")
    public BigdataResult select(String path, @CookieValue("LOGIN_USER_ID") Integer userId) {
        if (userId == null) {
            return BigdataResult.build(500, "找不到该用户");
        }
        FtpClientBO bo = new FtpClientBO(ftpConfiguration.getClient());
        List<FtpFileBO> ftpFileBOS;
        if (path == null) {
            ftpFileBOS = ftpService.selectByUser(bo, userId);
        } else {
            ftpFileBOS = ftpService.selectByPath(bo, userId + path);
        }
        return BigdataResult.build(200, "查询成功", ftpFileBOS);
    }


    /**
     * 下载
     * @param response
     * @param path 开头不可添加 /
     * @param userId
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response, String path, @CookieValue("LOGIN_USER_ID") Integer userId) {
        if (userId == null) {
            return;
        }
        FtpClientBO bo = new FtpClientBO(ftpConfiguration.getClient());
        ServletOutputStream output = null;
        //如:   2072/123/201810/23/推荐埋点日志dubbo接口.txt
        String ftpPath = userId + path;
        try {
            String name = URLEncoder.encode(ftpPath.substring(ftpPath.lastIndexOf('/')+1, ftpPath.length()), "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+name);
            response.setContentType("application/force-download");
            output = response.getOutputStream();
            ftpService.download(bo, ftpPath, output);
        } catch (Exception e) {
            log.error("FTP文件下载出错：{}" , ftpPath,e);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {

                }
            }
        }
    }

    /**
     * 下载
     * @param response
     * @param path 开头不可添加 /
     * @param userId
     */
    @RequestMapping("/delete")
    public BigdataResult delete(HttpServletResponse response, String path, @CookieValue("LOGIN_USER_ID") Integer userId) {
        if (userId == null) {
            return BigdataResult.build(500, "找不到该用户");
        }
        FtpClientBO bo = new FtpClientBO(ftpConfiguration.getClient());
        ServletOutputStream output = null;
        //如:   2072/123/201810/23/推荐埋点日志dubbo接口.txt
        String ftpPath = userId + path;
        if (ftpService.delete(bo, ftpPath)) {
            return BigdataResult.ok();
        } else {
            return BigdataResult.build(500, "删除出错");

        }
    }

}
