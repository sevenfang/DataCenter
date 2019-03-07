package com.chezhibao.bigdata.dbms.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/19.
 */
public class LoggerUtil {
    public static Logger HBASE_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.dbms.hbase");
    public static Logger REDIS_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.dbms.redis");
    public static Logger NDBMS_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.dbms.ndbms");
    public static Logger RDBMS_LOG = LoggerFactory.getLogger("com.chezhibao.bigdata.dbms.rdbms");
}
