package com.chezhibao.bigdata.datax.builder;

import com.chezhibao.bigdata.common.message.MessageFactory;
import com.chezhibao.bigdata.common.message.MessageList;
import com.chezhibao.bigdata.common.message.MessageObject;
import com.chezhibao.bigdata.datax.constant.Constant;
import com.chezhibao.bigdata.datax.pojo.DataxConfigDS;
import com.chezhibao.bigdata.datax.service.DataxConfigDSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
@Slf4j
@Component
public class JobBuilder implements Builder {

    private Integer channel = 1;

    @Autowired
    private DataxConfigDSService dataxConfigDSService;


    @Override
    public Map translateWriterDatasource(Map configData) {
        Map<String, Object> param = (Map<String, Object>) configData.get(Constant.PARAMETER);
        if(!param.containsKey(Constant.DATASOURCE)){
            return configData;
        }
        String dsName = param.get(Constant.DATASOURCE).toString();
        param.remove(Constant.DATASOURCE);
        //TODO 根据数据名称 查询数据链接类型与地址
        DataxConfigDS ds = dataxConfigDSService.getDS(dsName);
        if (ds == null) {
            return configData;
        }
        switch (ds.getType()) {
            case 0:
                param.put("password",ds.getPassword());
                param.put("username",ds.getUsername());
                List connection = (List) param.get("connection");
                Map map = (Map) connection.get(0);
                map.put("jdbcUrl",ds.getUrl());
                break;
            case 1:
                param.put("defaultFS", ds.getUrl());
                break;
            default:
        }
        return configData;
    }

    @Override
    public Map translateReaderDatasource(Map configData) {
        Map<String, Object> param = (Map<String, Object>) configData.get(Constant.PARAMETER);
        if(!param.containsKey(Constant.DATASOURCE)){
            return configData;
        }
        String dsName = param.get(Constant.DATASOURCE).toString();
        param.remove(Constant.DATASOURCE);
        //TODO 根据数据名称 查询数据链接类型与地址
        DataxConfigDS ds = dataxConfigDSService.getDS(dsName);
        if (ds == null) {
            return configData;
        }
        switch (ds.getType()) {
            case 0:
                param.put("password",ds.getPassword());
                param.put("username",ds.getUsername());
                List connection = (List) param.get("connection");
                Map map = (Map) connection.get(0);
                List jdbcUrl = new ArrayList();
                jdbcUrl.add(ds.getUrl());
                map.put("jdbcUrl",jdbcUrl);
                break;
            case 1:
                param.put("defaultFS", ds.getUrl());
                break;
            default:
        }
        return configData;
    }

    @Override
    public MessageObject build(Map reader, Map writer) {
        MessageObject data = MessageFactory.getMessageObject();
        MessageObject job = MessageFactory.getMessageObject();
        MessageObject setting = MessageFactory.getMessageObject();
        MessageObject speed = MessageFactory.getMessageObject();
        speed.put(Constant.CHANNEL, channel);
        setting.put(Constant.SPEED, speed);
        job.put(Constant.SETTING, setting);

        MessageList contents = MessageFactory.getMessageList();
        MessageObject content = MessageFactory.getMessageObject();
        content.put(Constant.READER, reader);
        content.put(Constant.WRITER, writer);

        contents.add(content);
        job.put(Constant.CONTENT, contents);
        data.put(Constant.JOB, job);
        return data;
    }


}
