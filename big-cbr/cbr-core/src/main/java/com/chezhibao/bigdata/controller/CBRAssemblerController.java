package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.bo.CBRBO;
import com.chezhibao.bigdata.bo.CBRColumnBO;
import com.chezhibao.bigdata.bo.CBRParamBO;
import com.chezhibao.bigdata.bo.QuerySqlBO;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.dto.CBRColumnDTO;
import com.chezhibao.bigdata.dto.CBRDTO;
import com.chezhibao.bigdata.dto.CBRParamDTO;
import com.chezhibao.bigdata.dto.QuerySqlDTO;
import com.chezhibao.bigdata.pojo.CBR;
import com.chezhibao.bigdata.pojo.CBRColumn;
import com.chezhibao.bigdata.pojo.CBRParam;
import com.chezhibao.bigdata.pojo.QuerySql;
import com.chezhibao.bigdata.service.CBRParamService;
import com.chezhibao.bigdata.service.CBRService;
import com.chezhibao.bigdata.service.ColumnService;
import com.chezhibao.bigdata.service.QuerySqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
@RestController
@Slf4j
@RequestMapping("cbr")
public class CBRAssemblerController {
    @Autowired
    private ColumnService columnService;

    @Autowired
    private QuerySqlService querySqlService;

    @Autowired
    private CBRService cbrService;

    @Autowired
    private CBRParamService cbrParamService;

    /**
     * 后台组装报表
     * @param realreportId 报表的唯一标识
     * @return
     */
    @RequestMapping("assembler")
    public BigdataResult assembler(String realreportId){
        CBRBO cbrById = cbrService.getCbrById(realreportId);
        //获取报表的基本信息
        CBR cbr  = CBRDTO.transCBRBO2CBR(cbrById);
        //获取报表的相关字段
        List<CBRColumnBO> cbrColumnBOS = columnService.getColumnByRealreportId(realreportId);
        List<CBRColumn> cbrColumns = CBRColumnDTO.transCBRColumnBOToCBRColumn(cbrColumnBOS);
        cbr.setColumns(cbrColumns);
        //获取相关sql
        List<QuerySqlBO> querySqlBOS = querySqlService.getSqlByRealreportId(realreportId);
        List<QuerySql> querySqls = QuerySqlDTO.transQuerySqlBOToQuerySql(querySqlBOS);
        cbr.setQuerySql(querySqls);
        //获取相关的参数
        List<CBRParamBO> cbrParamBOS = cbrParamService.getAllParamsByRealreportId(realreportId);
        List<CBRParam> cbrParams = CBRParamDTO.transCBRParamBO2CBRParam(cbrParamBOS);
        cbr.setCbrParams(cbrParams);
        return BigdataResult.ok(cbr);
    }
    /**
     * 获取报表骨架
     * @param realreportId 报表的唯一标识
     * @return
     */
    @RequestMapping("framework")
    public BigdataResult framework(String realreportId){
        CBRBO cbrById = cbrService.getCbrById(realreportId);
        //获取报表的基本信息
        CBR cbr  = CBRDTO.transCBRBO2CBR(cbrById);
        //获取报表的相关字段
        List<CBRColumnBO> cbrColumnBOS = columnService.getColumnByRealreportId(realreportId);
        List<CBRColumn> cbrColumns = CBRColumnDTO.transCBRColumnBOToCBRColumn(cbrColumnBOS);
        cbr.setColumns(cbrColumns);
        //获取相关的参数
        List<CBRParamBO> cbrParamBOS = cbrParamService.getAllParamsByRealreportId(realreportId);
        List<CBRParam> cbrParams = CBRParamDTO.transCBRParamBO2CBRParam(cbrParamBOS);
        cbr.setCbrParams(cbrParams);
        return BigdataResult.ok(cbr);
    }

    /**
     * 获取所有报表
     * @return
     */
    @RequestMapping("all")
    public BigdataResult getAllCbr(){
        List<CBRBO> allCBR = cbrService.getAllCBR();
        return BigdataResult.ok(allCBR);
    }

    /**
     * 删除指定报表
     * @return
     */
    @RequestMapping("del/{realreportId}")
    public BigdataResult getAllCbr(@PathVariable("realreportId") String realreportId){
        List<CBRBO> allCBR = cbrService.getAllCBR();
        return BigdataResult.ok(allCBR);
    }
}
