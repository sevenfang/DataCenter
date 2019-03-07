package com.chezhibao.bigdata.dbms.hbase.bo;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/28.
 */

public class HbaseParam<T> implements Serializable {
    /**
     * hbase表名
     */
    private String tableName;
    /**
     * hbase中的rowkey
     */
    private String rowKey;
    /**
     * hbase中的列族
     */
    private String cf;
    /**
     * 列族下的列
     */
    private String qualifier;
    /**
     * 存入hbase的数据
     */
    private T value;
    /**
     * 数据的类型
     */
    private Class<T> tClass;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Class<T> getTClass() {
        return tClass;
    }

    public void setTClass(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public String toString() {
        return "HbaseParam{" +
                "tableName='" + tableName + '\'' +
                ", rowKey='" + rowKey + '\'' +
                ", cf='" + cf + '\'' +
                ", qualifier='" + qualifier + '\'' +
                ", value=" + value +
                ", tClass=" + tClass +
                '}';
    }
}
