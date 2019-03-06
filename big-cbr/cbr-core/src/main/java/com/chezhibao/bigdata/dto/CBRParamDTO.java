package com.chezhibao.bigdata.dto;

import com.chezhibao.bigdata.bo.CBRParamBO;
import com.chezhibao.bigdata.pojo.CBR;
import com.chezhibao.bigdata.pojo.CBRParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/15.
 */
@Slf4j
public class CBRParamDTO {
    /**
     * 将前端的数据转换策划给你后端参数数据
     * @param cbr
     * @return
     */
    public static List<CBRParamBO> transCbr2CBRParamBO(CBR cbr){
        return transCbr2CBRParamBO(cbr.getRealreportId(),cbr.getCbrParams());
    }
    public static List<CBRParamBO> transCbr2CBRParamBO(String realreportId,List<CBRParam> cbrParams){
        List<CBRParamBO> cbrParamBOS = new ArrayList<>();
        CBRParamBO cbrParamBO;
        for(int i=0;i<cbrParams.size();i++){
            cbrParamBO = new CBRParamBO();
            BeanUtils.copyProperties(cbrParams.get(i),cbrParamBO);
            try{
                String encode = URLEncoder.encode(cbrParamBO.getValue(), "UTF-8");
                cbrParamBO.setValue(encode);
            }catch (Exception e){
                log.error("【CBR】字符转换出错了",e);
            }
            cbrParamBO.setOrder(i);
            cbrParamBO.setRealreportId(realreportId);
            cbrParamBOS.add(cbrParamBO);
        }
        return cbrParamBOS;
    }

    public static List<CBRParam> transCBRParamBO2CBRParam(List<CBRParamBO> cbrParamBOs){
        List<CBRParam> cbrParams = new ArrayList<>();
        CBRParam cbrParam;
        for(CBRParamBO cbrParamBO:cbrParamBOs){
            cbrParam = transCBRParamBO2CBRParam(cbrParamBO);
            try {
                String decode = URLDecoder.decode(cbrParam.getValue(), "UTF-8");
                cbrParam.setValue(decode);
            }catch (Exception e){
                log.error("【CBR】字符转换出错了",e);
            }
            cbrParams.add(cbrParam);
        }
        return cbrParams;
    }

    public static CBRParam transCBRParamBO2CBRParam(CBRParamBO cbrParamBO){
        CBRParam cbrParam = new CBRParam();
        BeanUtils.copyProperties(cbrParamBO,cbrParam);
        return cbrParam;
    }
}
