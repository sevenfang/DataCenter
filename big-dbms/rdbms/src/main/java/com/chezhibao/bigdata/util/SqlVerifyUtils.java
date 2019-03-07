package com.chezhibao.bigdata.util;

import com.chezhibao.bigdata.dbms.common.LoggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/26.
 */
public class SqlVerifyUtils {
    private static Logger log = LoggerUtil.RDBMS_LOG;

    private static Pattern GROUPBY = Pattern.compile(".*group\\s+by.*", Pattern.CASE_INSENSITIVE);
    private static Pattern JOIN = Pattern.compile(".*\\s+join\\s+.*", Pattern.CASE_INSENSITIVE);
    private static Pattern LIMIT = Pattern.compile(".*\\s+limit\\s+(.*?,?\\s*.*\\s*?).*;?", Pattern.CASE_INSENSITIVE);
    private static Pattern ISNUM = Pattern.compile("[0-9]*");

    public static Boolean hasGroupBy(String sql) {
        sql = sql.replace("\n" ," ").replace("\t","");
        Matcher matcher = GROUPBY.matcher(sql);
        return matcher.find();
    }

    public static Boolean hasJoin(String sql) {
        sql = sql.replace("\n" ," ").replace("\t","");
        Matcher matcher = JOIN.matcher(sql);
        return matcher.find();
    }

    public static Boolean isLegalLimit(String sql, Integer limit, Map<String, Object> params) {
        sql = sql.replace("\n" ," ").replace("\t","");
        Matcher matcher = LIMIT.matcher(sql);
        Integer inputLimitNum=-1;
        while (matcher.find()) {
            String group = matcher.group(1);
            log.debug("【数据库管理系统】sql中的limit值校验匹配字符串{}",group);
            String[] split = group.split(",");
            String trim;
            //判断是否分页 分页取后面的
            if (split.length > 1) {
                String s = split[1];
                trim = s.trim();
            } else {
                trim = split[0].trim();
            }
            //判断其中是否包含 空格 ;
            if (trim.contains(" ")) {
                trim = trim.split(" ")[0];
            }
            if (trim.contains(";")) {
                trim = trim.split(";")[0];
            }

            //判断是否为数字
            boolean isNum = ISNUM.matcher(trim).matches();

            if(isNum){
                inputLimitNum=Integer.parseInt(trim);
            }else {
                trim = trim.replace(":", "");
                inputLimitNum = Integer.parseInt(params.get(trim)+"");
            }
            log.debug("【数据库管理系统】sql中的limit值校验，limit输入值{}",inputLimitNum);
        }
        return inputLimitNum<=limit;
    }

    public static Boolean isLegalSql(String sql, Integer limit, Map<String, Object> params){
        if(hasGroupBy(sql)){
            return false;
        }
        if(hasJoin(sql)){
            return false;
        }
        return isLegalLimit(sql,limit,params);
    }
}
