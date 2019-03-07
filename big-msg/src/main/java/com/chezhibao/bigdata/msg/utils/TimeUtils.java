package com.chezhibao.bigdata.msg.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jerry on 2018/10/12.
 */
public class TimeUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat();

    public final static String FORMAT_DATE = "yyyyMMdd";
    public final static String FORMAT_DATE_HOR = "yyyy-MM-dd";

    /**
     * 将Date以指定格式转换为日期时间字符串
     *
     * @param date
     *            日期
     * @param format
     *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getStringFromDate(Date date, String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(date);
    }


}
