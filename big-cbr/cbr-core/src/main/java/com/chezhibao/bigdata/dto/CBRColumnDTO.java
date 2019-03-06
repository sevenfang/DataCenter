package com.chezhibao.bigdata.dto;

import com.chezhibao.bigdata.bo.CBRColumnBO;
import com.chezhibao.bigdata.pojo.CBR;
import com.chezhibao.bigdata.pojo.CBRColumn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
@Slf4j
public class CBRColumnDTO {
    /**
     * 将cbr中的字段转换成数据库字段
     * @param cbr
     * @return
     */
    public static List<CBRColumnBO> transCbr2CBRColumnBO(CBR cbr){
        return transCbr2CBRColumnBO(cbr.getRealreportId(),cbr.getColumns());
    }
    public static List<CBRColumnBO> transCbr2CBRColumnBO(String realreportId,List<CBRColumn> columns){
        List<CBRColumnBO> cbrColumnBOS = new ArrayList<>();
        CBRColumnBO cbrColumnBO;
        for(int i = 0;i<columns.size();i++){
            cbrColumnBO=new CBRColumnBO();
            BeanUtils.copyProperties(columns.get(i),cbrColumnBO);
            cbrColumnBO.setRealreportId(realreportId);
            cbrColumnBO.setOrder(i);
            //编码数据
            try {
                cbrColumnBO.setFunc(URLEncoder.encode(cbrColumnBO.getFunc(), "UTF-8"));
                cbrColumnBO.setText(URLEncoder.encode(cbrColumnBO.getText(), "UTF-8"));
                cbrColumnBO.setStyle(URLEncoder.encode(cbrColumnBO.getStyle(), "UTF-8"));
            }catch (Exception e){
                log.error("【CBR】字符转换出错了",e);
            }
            cbrColumnBOS.add(cbrColumnBO);
        }
        return cbrColumnBOS;
    }

    /**
     * 将数据库中的字段转换成前端的字段
     * @param cbrColumnBOS
     * @return
     */
    public static List<CBRColumn> transCBRColumnBOToCBRColumn(List<CBRColumnBO> cbrColumnBOS){
        List<CBRColumn> cbrColumns = new ArrayList<>();
        CBRColumn cbrColumn;
        for(CBRColumnBO cbrColumnBO :cbrColumnBOS){
            cbrColumn = new CBRColumn();
            BeanUtils.copyProperties(cbrColumnBO,cbrColumn);
            //解码数据
            try {
                cbrColumn.setFunc(URLDecoder.decode(cbrColumn.getFunc(), "UTF-8"));
                cbrColumn.setText(URLDecoder.decode(cbrColumn.getText(), "UTF-8"));
                cbrColumn.setStyle(URLDecoder.decode(cbrColumn.getStyle(), "UTF-8"));
            }catch (Exception e){
                log.error("【CBR】字符转换出错了",e);
            }
            cbrColumns.add(cbrColumn);
        }
          return  cbrColumns;
    }
}
