package com.chezhibao.bigdata.datax.dto;


import com.chezhibao.bigdata.common.message.MessageFactory;
import com.chezhibao.bigdata.common.message.MessageList;
import com.chezhibao.bigdata.common.message.MessageObject;
import com.chezhibao.bigdata.common.message.MessageUtils;
import com.chezhibao.bigdata.datax.constant.Constant;

import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
public class MysqlDTO {
    public static MessageObject readerWeb2db(Map<String,Object> webData){
        //mysqlReader主体
        MessageObject mysqlReader = MessageFactory.getMessageObject();
        mysqlReader.put("name",Constant.READER_MYSQL);
        //parameter配置参数
        MessageObject parameter = MessageFactory.getMessageObject();
        parameter.put("username",webData.get("username"));
        parameter.put("password",webData.get("password"));
        //链接信息
        MessageList connections = MessageFactory.getMessageList();
        MessageObject connection = MessageFactory.getMessageObject();
        MessageList querySql = MessageFactory.getMessageList();
        querySql.add(webData.get("querySql"));
        MessageList jdbcUrl = MessageFactory.getMessageList();
        //多个URL
        String jdbcUrls = webData.get("jdbcUrl").toString();
        for(String url:jdbcUrls.split(",")){
            jdbcUrl.add("jdbc:mysql://"+url);
        }
        connection.put("querySql",querySql);
        connection.put("jdbcUrl",jdbcUrl);
        connections.add(connection);
        parameter.put("connection",connections);
        mysqlReader.put("parameter",parameter);
        return mysqlReader;
    }

    public static MessageObject writerWeb2db(Map<String,Object> webData){
        //mysqlReader主体
        MessageObject mysqlWriter = MessageFactory.getMessageObject();
        mysqlWriter.put("name",Constant.WRITER_MYSQL);
        //parameter配置参数
        MessageObject parameter = MessageFactory.getMessageObject();
        parameter.put("writeMode",webData.get("writeMode"));
        parameter.put("username",webData.get("username"));
        parameter.put("password",webData.get("password"));
        parameter.put("column",MessageUtils.string2ML(webData.get("column")));
        parameter.put("preSql",MessageUtils.string2ML(webData.get("preSql"),"#"));
        parameter.put("postSql",MessageUtils.string2ML(webData.get("postSql"),"#"));

        //链接信息
        MessageList connections = MessageFactory.getMessageList();
        MessageObject connection = MessageFactory.getMessageObject();

        connection.put("table",MessageUtils.string2ML(webData.get("table")));
        connection.put("jdbcUrl","jdbc:mysql://"+webData.get("jdbcUrl"));

        //拼装数据
        connections.add(connection);
        parameter.put("connection",connections);
        mysqlWriter.put("parameter",parameter);
        return mysqlWriter;
    }
}
