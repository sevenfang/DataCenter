package com.chezhibao.bigdata.dto;

import com.chezhibao.bigdata.bo.CBRBO;
import com.chezhibao.bigdata.pojo.CBR;
import com.chezhibao.bigdata.pojo.QuerySql;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
public class CBRDTO {
    public static CBRBO transCbr2CBRBO(CBR cbr){
        CBRBO cbrbo = new CBRBO();
        List<QuerySql> querySql = cbr.getQuerySql();
        BeanUtils.copyProperties(cbr,cbrbo);
        cbrbo.setLevel(querySql.size()/2);
        return cbrbo;
    }

    public static CBR transCBRBO2CBR(CBRBO cbrbo){
        CBR cbr = new CBR();
        BeanUtils.copyProperties(cbrbo,cbr);
        return cbr;
    }
    public static List<CBR> transCBRBO2CBR(List<CBRBO> cbrbos){
        List<CBR> cbrs = new ArrayList<>();
        CBR cbr ;
        for(CBRBO cbrbo : cbrbos){
            cbr = transCBRBO2CBR(cbrbo);
            cbrs.add(cbr);
        }
        return cbrs;
    }
}
