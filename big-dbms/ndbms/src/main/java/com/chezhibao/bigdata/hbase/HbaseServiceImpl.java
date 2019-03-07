package com.chezhibao.bigdata.hbase;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.aspect.annotation.InvokeTime;
import static com.chezhibao.bigdata.dbms.common.LoggerUtil.*;
import com.chezhibao.bigdata.dbms.hbase.HbaseService;
import com.chezhibao.bigdata.dbms.hbase.bo.HbaseParam;
import org.apache.hadoop.hbase.client.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;


import static com.chezhibao.bigdata.dbms.common.LoggerUtil.RDBMS_LOG;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/25.
 */
@Repository
@Service(interfaceClass = HbaseService.class)
public class HbaseServiceImpl implements HbaseService {
    @Autowired
    private HbaseTemplate hbaseTemplate;

    @Override
    @InvokeTime
    public <T> Boolean save(HbaseParam<T> param) {
        String tableName = param.getTableName();
        String rowKey = param.getRowKey();
        String cf = param.getCf() == null ? "info" : param.getCf();
        String qualifier = param.getQualifier() == null ? "data" : param.getQualifier();
        T value = param.getValue();
        Assert.notNull(value,"can not save null!!!");
        String s;
        if(value instanceof String) {
            s = value+"";
        }else{
            s = JSON.toJSONString(value);
        }
        try {
            hbaseTemplate.put(tableName, rowKey, cf, qualifier, s.getBytes());
        } catch (Exception e) {
            HBASE_LOG.error("【查询系统】存入Hbase出错了！param：{}", param,e);
            return false;
        }
        return true;
    }

    @Override
    @InvokeTime
    public <T> T get(HbaseParam<T> param) {
        String tableName = param.getTableName();
        String rowKey = param.getRowKey();
        String cf = param.getCf() == null ? "info" : param.getCf();
        String qualifier = param.getQualifier() == null ? "data" : param.getQualifier();
        Class<T> tClass = param.getTClass();
        T result = null;
        try {
            result = hbaseTemplate.get(tableName, rowKey, cf, qualifier, new RowMapper<T>() {
                @Override
                public T mapRow(Result result, int i) throws Exception {
                    byte[] value = result.value();
                    String s = new String(value);
                    return JSON.parseObject(s, tClass);
                }
            });
        } catch (Exception e) {
            HBASE_LOG.error("【查询系统】获取Hbase数据出错了！rowkey：{}", rowKey,e);
        }
        return result;
    }

    /** Fallback 函数，函数签名与原函数一致.
     *
     * @param param
     * @return
     */
    public <T> T hbaseGetfallback(HbaseParam<T> param) {
        RDBMS_LOG.error("【dbms】熔断处理......获取hbase数据出错！！{}",param);
        return null;
    }
    public <T> T hbaseGetExceptionHandler(HbaseParam<T> param, BlockException ex) {
        RDBMS_LOG.error("【dbms】熔断处理......获取hbase数据出错！！{}",param);
        return null;
    }

    @Override
    @InvokeTime
    public Boolean deleteByrowKey(HbaseParam param) {
        String tableName = param.getTableName();
        String rowKey = param.getRowKey();
        String cf = param.getCf() == null ? "info" : param.getCf();
        String qualifier = param.getQualifier();
        //判断qualifier是否为空
        if (StringUtils.isEmpty(qualifier)) {
            //为空直接删除整个列族
            hbaseTemplate.delete(tableName,rowKey,cf);
        }else{
            //不为空删除指定列
            hbaseTemplate.delete(tableName,rowKey,cf,qualifier);
        }
        return true;
    }

    @Override
    @InvokeTime
    public String getString(HbaseParam<String> param) {
        String tableName = param.getTableName();
        String rowKey = param.getRowKey();
        String cf = param.getCf() == null ? "info" : param.getCf();
        String qualifier = param.getQualifier() == null ? "data" : param.getQualifier();
        String result = null;
        HBASE_LOG.info("【查询系统】查询Hbase数据参数:{}！",param);
        try {
            result = hbaseTemplate.get(tableName, rowKey, cf, qualifier, new RowMapper<String>() {
                @Override
                public String mapRow(Result result, int i) throws Exception {
                    byte[] value = result.value();
                    if (ObjectUtils.isEmpty(value)) {
                        return "";
                    }
                    return new String(value);
                }
            });
        } catch (Exception e) {
            HBASE_LOG.error("【查询系统】获取Hbase数据出错了！rowkey：{}", rowKey,e);
        }
        HBASE_LOG.info("【查询系统】获取Hbase数据:{}！rowkey：{}", result,rowKey);
        return result;
    }
}
