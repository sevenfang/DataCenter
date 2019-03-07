package com.chezhibao.bigdata.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @author WangCongJun
 * @date 2018/4/25
 * Created by WangCongJun on 2018/4/25.
 */
@Mapper
@Component
public interface BaseDao {
    /**
     * 存储
     * @param sql 对应的语句
     * @param params 传入的参数
     */
    @InsertProvider(type = SqlProvider.class,method = "insert")
    void insert(@Param("sql") String sql, @Param("params")Map<String,Object> params);

    /**
     * 删除
     * @param sql 对应的语句
     * @param params 传入的参数
     */
    @DeleteProvider(type = SqlProvider.class,method = "delete")
    void delete(@Param("sql") String sql, @Param("params")Map<String,Object> params);

    /**
     * 更新
     * @param sql 对应的语句
     * @param params 传入的参数
     */
    @UpdateProvider(type = SqlProvider.class,method = "update")
    void update(@Param("sql") String sql,  @Param("params")Map<String,Object> params);

    /**
     * 查找
     * @param sql 对应的语句
     * @param params 传入的参数
     */
    @SelectProvider(type = SqlProvider.class,method = "select")
    List<Map<String,Object>> select(@Param("sql")String sql, @Param("params")Map<String,Object> params);


    /**
     * 查找(按照查询的字段顺序)
     * @param sql 对应的语句
     * @param params 传入的参数
     */
    @SelectProvider(type = SqlProvider.class,method = "select")
    List<LinkedHashMap<String,Object>> query(@Param("sql")String sql, @Param("params")Map<String,Object> params);

}
