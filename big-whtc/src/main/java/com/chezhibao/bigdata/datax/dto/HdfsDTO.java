package com.chezhibao.bigdata.datax.dto;

import com.chezhibao.bigdata.common.message.MessageFactory;
import com.chezhibao.bigdata.common.message.MessageList;
import com.chezhibao.bigdata.common.message.MessageObject;
import com.chezhibao.bigdata.common.message.MessageUtils;
import com.chezhibao.bigdata.datax.constant.Constant;
import com.chezhibao.bigdata.datax.constant.HdfsConstant;

import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
public class HdfsDTO {
    public static MessageObject readerWeb2db(Map<String,Object> webData){
        //hdfsreader
        MessageObject hdfsReader = MessageFactory.getMessageObject();
        hdfsReader.put("name",Constant.READER_HDFS);
        //parameter配置参数
        MessageObject parameter = MessageFactory.getMessageObject();
        parameter.put(HdfsConstant.PATH,webData.get(HdfsConstant.PATH));
        parameter.put(HdfsConstant.DEFAULTFS,"hdfs://"+webData.get(HdfsConstant.DEFAULTFS));
        parameter.put(HdfsConstant.FILETYPE,webData.get(HdfsConstant.FILETYPE));
        parameter.put(HdfsConstant.ENCODING,webData.get(HdfsConstant.ENCODING));
        parameter.put(HdfsConstant.FIELDDELIMITER,webData.get(HdfsConstant.FIELDDELIMITER));
        //处理column
        parameter.put(HdfsConstant.COLUMN,MessageUtils.json2ML(webData.get(HdfsConstant.COLUMN).toString()));
        //TODO 处理hadoopConfig



        hdfsReader.put("parameter",parameter);
        return hdfsReader;
    }

    public static MessageObject writerWeb2db(Map<String,Object> webData){
        //mysqlReader主体
        MessageObject hdfsWriter = MessageFactory.getMessageObject();
        hdfsWriter.put("name",Constant.WRITER_HDFS);
        //parameter配置参数
        MessageObject parameter = MessageFactory.getMessageObject();
        parameter.put(HdfsConstant.PATH,webData.get(HdfsConstant.PATH));
        parameter.put(HdfsConstant.DEFAULTFS,"hdfs://"+webData.get(HdfsConstant.DEFAULTFS));
        parameter.put(HdfsConstant.FILETYPE,webData.get(HdfsConstant.FILETYPE));
        parameter.put(HdfsConstant.FILENAME,webData.get(HdfsConstant.FILENAME));
        parameter.put(HdfsConstant.FIELDDELIMITER,webData.get(HdfsConstant.FIELDDELIMITER));
        parameter.put(HdfsConstant.WRITEMODE,webData.get(HdfsConstant.WRITEMODE));
        parameter.put(HdfsConstant.COMPRESS,webData.get(HdfsConstant.COMPRESS));
        //处理column
        parameter.put(HdfsConstant.COLUMN,MessageUtils.json2ML(webData.get(HdfsConstant.COLUMN).toString()));
        //TODO 处理hadoopConfig
        hdfsWriter.put("parameter",parameter);
        return hdfsWriter;
    }
}
