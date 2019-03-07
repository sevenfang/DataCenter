package com.chezhibao.bigdata.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author WangCongJun
 * @date 2018/4/25
 * Created by WangCongJun on 2018/4/25.
 */
@ConfigurationProperties(prefix = "dbms")
@Data
public class DatasourcesConfigration {
    private Map<String,Map<String, String>> ds = new HashMap<>();
    public Map<String,Properties> getDatasourceProp(){
        Map<String,Properties> props = new HashMap<>();
        for(String key: ds.keySet()){
            Properties prop = new Properties();
            for(Map.Entry e: ds.get(key).entrySet()){
                prop.setProperty(e.getKey().toString(),e.getValue().toString());
            }
            props.put(key,prop);
        }
        return props;
    }

    public Map<String,Properties> getDruidDatasourceProp(){
        Map<String,Properties> props = new HashMap<>();
        Properties prop;
        for(String key: ds.keySet()){
            //加上默认配置(可以被配置文件中的实际配置覆盖)
            prop = addDruidDefaultProperties();
            prop.put("druid.name",key);
            for(Map.Entry e: ds.get(key).entrySet()){
                prop.setProperty("druid."+e.getKey(),e.getValue().toString());
            }
            props.put(key,prop);
        }
        return props;
    }

    /**
     * 添加的默认配置 (需要更改,在系统配置页面添加对应的配置即可)
     * 注:这个的druid.maxActive是druid的key,配置时用maxActive即可,前面的druid不需要的
     * 值统一用String类型
     */
    private Properties addDruidDefaultProperties(){
        Properties prop = new Properties();
        //根据url自动识别 这一项可配可不配，如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName(建议配置下)
        prop.put("druid.driverClassName","com.mysql.jdbc.Driver");
        //属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有： 监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
        prop.put("druid.filters","stat, wall, log4j");
        //用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
        prop.put("druid.validationQuery","select 1");
        //	已经不再使用，配置了也没效果
        prop.put("druid.maxIdle","20");
        //最大连接池数量
        prop.put("druid.maxActive","300");
        //获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
        prop.put("druid.maxWait","5000");
        //配置一个连接在池中最小生存的时间，单位是毫秒
        prop.put("druid.minEvictableIdleTimeMillis","300000");
        //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        prop.put("druid.testWhileIdle","true");
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        prop.put("druid.timeBetweenEvictionRunsMillis","60000");

        prop.put("druid.testOnReturn","false");
        //最小连接池数量
        prop.put("druid.minIdle","6");
        prop.put("druid.connectionProperties","druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");
        //这里建议配置为TRUE，防止取到的连接不可用
        prop.put("druid.testOnBorrow","true");
        //初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
        prop.put("druid.initialSize","10");
        //物理连接初始化的时候执行的sql
        prop.put("druid.connectionInitSqls","select 1");
        return prop;
    }
}
