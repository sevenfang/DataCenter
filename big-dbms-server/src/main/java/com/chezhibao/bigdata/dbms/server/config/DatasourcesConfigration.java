package com.chezhibao.bigdata.dbms.server.config;

import com.chezhibao.bigdata.dbms.server.utils.DBUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
            prop = DBUtils.addDruidDefaultProperties();
            prop.put("druid.name",key);
            for(Map.Entry e: ds.get(key).entrySet()){
                prop.setProperty("druid."+e.getKey(),e.getValue().toString());
            }
            props.put(key,prop);
        }
        return props;
    }


}
