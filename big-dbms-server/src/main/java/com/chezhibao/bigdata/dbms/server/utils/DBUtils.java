package com.chezhibao.bigdata.dbms.server.utils;

import com.chezhibao.bigdata.common.pojo.ItemVo;
import com.chezhibao.bigdata.dbms.server.bo.DBInstanceSchemaBO;
import com.chezhibao.bigdata.dbms.server.constants.DbTypeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/24.
 */
@Slf4j
public class DBUtils {

    private static final String MYSQL_PREFIX = "jdbc:mysql://";
    private static final String MYSQL_SUFFIX = "/information_schema?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false";

    private static final String PRESTO_SUFFIX = "";
    private static final String PRESTO_PREFIX = "jdbc:presto://";


    /**
     * 添加的默认配置 (需要更改,在系统配置页面添加对应的配置即可)
     * 注:这个的druid.maxActive是druid的key,配置时用maxActive即可,前面的druid不需要的
     * 值统一用String类型
     */
    public static Properties addDruidDefaultProperties() {
        Properties prop = new Properties();
        //根据url自动识别 这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName(建议配置下)
        prop.put("druid.driverClassName", "com.mysql.jdbc.Driver");
        //属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有： 监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
        prop.put("druid.filters", "stat");
        //用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
        prop.put("druid.validationQuery", "select 1");
        //	已经不再使用，配置了也没效果
        prop.put("druid.maxIdle", "20");
        //最大连接池数量
        prop.put("druid.maxActive", "100");
        //获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
        prop.put("druid.maxWait", "10000");
        //配置一个连接在池中最小生存的时间，单位是毫秒
        prop.put("druid.minEvictableIdleTimeMillis", "300000");
        //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        prop.put("druid.testWhileIdle", "true");
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        prop.put("druid.timeBetweenEvictionRunsMillis", "60000");

        prop.put("druid.testOnReturn", "false");
        //最小连接池数量
        prop.put("druid.minIdle", "6");
        prop.put("druid.connectionProperties", "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");
        //这里建议配置为TRUE，防止取到的连接不可用
        prop.put("druid.testOnBorrow", "true");
        //初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
        prop.put("druid.initialSize", "10");
        //物理连接初始化的时候执行的sql
        prop.put("druid.connectionInitSqls", "select 1");
        return prop;
    }

    public static String getUrl(DBInstanceSchemaBO dbInstanceSchemaBO) {
        Integer dbType = dbInstanceSchemaBO.getDbType();
        String host = dbInstanceSchemaBO.getHost();
        Integer port = dbInstanceSchemaBO.getPort();
        String extraUrl = dbInstanceSchemaBO.getExtraUrl();
        extraUrl = StringUtils.isEmpty(extraUrl) ? "" : extraUrl;
        StringBuilder builder = new StringBuilder();
        switch (dbType) {
            case DbTypeConstants.MYSQL:
                builder.append(MYSQL_PREFIX).append(host).append(":").append(port).append(MYSQL_SUFFIX);
                if (!extraUrl.startsWith("&") && extraUrl.equals("")) {
                    extraUrl = "&" + extraUrl;
                }
                builder.append(extraUrl);
                break;
            case DbTypeConstants.Presto:
                builder.append(PRESTO_PREFIX).append(host).append(":").append(port).append(PRESTO_SUFFIX);
                builder.append(extraUrl);
                break;
            default:
        }
        return builder.toString();
    }


    public static Map<String, ItemVo> generDbItem(List<LinkedHashMap<String, Object>> select, String dbItemLabel, String dbItemValue) {
        Map<String, ItemVo> result = new HashMap<>();
        ItemVo item;
        //数据库中的视图，表的汇总对象
        ItemVo dbItemVo;
        //数据库中的视图，表的集合
        List<ItemVo> itemChildren;
        //数据库中的视图，表的子对象
        ItemVo subDbItemVo;
        for (Map<String, Object> m : select) {
            String schema = m.get("schema").toString();
            String itemName = m.get("name").toString();
            //获取数据库
            item = result.get(schema);
            if (ObjectUtils.isEmpty(item)) {
                //初始化
                item = new ItemVo();
                item.setValue(schema);
                item.setLabel(schema);
                itemChildren = new ArrayList<>();
                dbItemVo = new ItemVo();
                dbItemVo.setValue(dbItemValue);
                dbItemVo.setLabel(dbItemLabel);
                dbItemVo.setChildren(new ArrayList<>());
                itemChildren.add(dbItemVo);
                item.setChildren(itemChildren);
                result.put(schema, item);
            }
            //将数据库的子对象添加到集合中
            subDbItemVo = new ItemVo();
            subDbItemVo.setValue(itemName);
            subDbItemVo.setLabel(itemName);
            item.getChildren().get(0).getChildren().add(subDbItemVo);
        }
        log.debug("【数据库管理系统】获取数据库schema中{}结果为：{}", dbItemLabel, result);
        return result;
    }


    public static Map<String, Set<String>> getColumnsSchema(List<LinkedHashMap<String, Object>> select) {
        Map<String, Set<String>> result = new HashMap<>();
        for (Map<String, Object> m : select) {
            String schema = m.get("schema").toString();
            String table = m.get("table").toString();
            String column = m.get("column").toString();
            //先检查schema是否存在
            if (!result.containsKey(schema)) {
                result.put(schema, new HashSet<>());
            }
            result.get(schema).add(table);
            //再检查table是否存在
            if (!result.containsKey(table)) {
                result.put(table, new HashSet<>());
            }
            result.get(table).add(column);
        }
        return result;
    }
}
