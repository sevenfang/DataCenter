package com.chezhibao.bigdata.cbr.download.util;

import com.chezhibao.bigdata.cbr.download.bo.DownloadSqlBO;
import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/12.
 */
@Slf4j
public class DownloadUtils {
    public static void encodeSql(DownloadSqlBO downloadSqlBO) {
        String sql = downloadSqlBO.getSql();
        try {
            downloadSqlBO.setSql(URLEncoder.encode(sql, "UTF-8"));
        } catch (Exception e) {
            log.error("【CBR】编码sql出错了！", e);
        }
    }

    public static void decodeSql(DownloadSqlBO downloadSqlBO) {
        String sql = downloadSqlBO.getSql();
        try {
            downloadSqlBO.setSql(URLDecoder.decode(sql, "UTF-8"));
        } catch (Exception e) {
            log.error("【CBR】解码sql出错了！", e);
        }
    }
}
