package com.chezhibao.bigdata.dao;



import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/27
 * Created by WangCongJun on 2018/4/27.
 */
public interface IBaseDao {
    /**
     * 存储
     * @param dataSource 对应的数据源key
     * @param sql 对应的语句
     * @param params 传入的参数
     */
    void insert(String dataSource,String sql, Map<String,Object> params);

    /**
     * 删除
     * @param dataSource 对应的数据源key
     * @param sql 对应的语句
     * @param params 传入的参数
     */

    void delete(String dataSource,String sql, Map<String,Object> params);

    /**
     * 更新
     * @param dataSource 对应的数据源key
     * @param sql 对应的语句
     * @param params 传入的参数
     *
     */

    void update(String dataSource,String sql, Map<String,Object> params);

    /**
     * 查找
     * @param dataSource 对应的数据源key
     * @param sql 对应的语句
     * @param params 传入的参数
     * @return
     */

    List<Map<String,Object>> select(String dataSource,String sql,Map<String,Object> params);

    /**
     * 查找 顺序
     * @param dataSource 对应的数据源key
     * @param sql 对应的语句
     * @param params 传入的参数
     * @return
     */
    List<LinkedHashMap<String,Object>> query(String dataSource, String sql, Map<String,Object> params);
}
