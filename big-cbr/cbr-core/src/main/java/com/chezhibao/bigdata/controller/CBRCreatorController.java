package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.bo.CBRBO;
import com.chezhibao.bigdata.bo.CBRColumnBO;
import com.chezhibao.bigdata.bo.CBRParamBO;
import com.chezhibao.bigdata.bo.QuerySqlBO;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.dao.BigdataDao;
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
import com.chezhibao.bigdata.vo.CBRColumnVo;
import com.chezhibao.bigdata.vo.CbrParamVO;
import com.chezhibao.bigdata.vo.QuerySqlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 创建报表
 * @author WangCongJun
 * Created by WangCongJun on 2018/10/11.
 */
@RestController
@Slf4j
@RequestMapping("cbr")
public class CBRCreatorController {
    @Autowired
    private ColumnService columnService;

    @Autowired
    private QuerySqlService querySqlService;

    @Autowired
    private CBRService cbrService;

    @Autowired
    private CBRParamService cbrParamService;

    /**
     * 创建报表
     * @return
     */
    @RequestMapping("create")
    public BigdataResult create(@RequestBody CBR cbr){
        if(StringUtils.isEmpty(cbr.getRealreportId())){
        }
        log.debug("【实时报表系统】创建报表请求参数{}",cbr);
        CBRBO cbrbo = CBRDTO.transCbr2CBRBO(cbr);
        //存储报表的的
        cbrService.saveOrUpdateCBR(cbrbo);
        //存储字段
        columnService.saveOrUpdateColumn(CBRColumnDTO.transCbr2CBRColumnBO(cbr));
        //存储sql
        querySqlService.saveOrupdateSql(QuerySqlDTO.transCbr2QuerySqlBO(cbr));
        //
        cbrParamService.saveOrupdateParam(CBRParamDTO.transCbr2CBRParamBO(cbr));
        return BigdataResult.ok();
    }

    /**
     * 存储更新报表基本信息
     * @param cbr
     * @return
     */
    @PostMapping("/define")
    public BigdataResult defineCbr(@RequestBody CBR cbr){
        log.debug("【实时报表系统】创建报表请求参数{}",cbr);
        CBRBO cbrbo = CBRDTO.transCbr2CBRBO(cbr);
        //存储报表的基本信息
        Boolean aBoolean = cbrService.saveOrUpdateCBR(cbrbo);
        if(aBoolean){
            CBRBO cbr1 = cbrService.getCbrById(cbr.getRealreportId());
            CBR cbr2 = CBRDTO.transCBRBO2CBR(cbr1);
            return BigdataResult.ok(cbr2);
        }else{
            return BigdataResult.build(500,"创建失败！！");
        }
    }

    /**
     * 存储更新报表字段
     * @param realreportId
     * @param cbrColumnVo
     * @return
     */
    @PostMapping("/{realreportId}/columns")
    public BigdataResult saveCbrColoumns(@PathVariable String realreportId
            ,@RequestBody CBRColumnVo cbrColumnVo){
        //删除字段
        List<Integer> delColumnIds = cbrColumnVo.getDelColumnIds();
        Boolean flag = columnService.deleteColumnById(delColumnIds);
        if(!flag){
            return BigdataResult.build(500,"操作失败！！");
        }
        //存储字段
        List<CBRColumn> columns = cbrColumnVo.getColumns();
        Boolean aBoolean = columnService.saveOrUpdateColumn(CBRColumnDTO.transCbr2CBRColumnBO(realreportId, columns));
        if(aBoolean){
            //查询存入的值
            List<CBRColumnBO> columnByRealreportId = columnService.getColumnByRealreportId(realreportId);
            List<CBRColumn> cbrColumns = CBRColumnDTO.transCBRColumnBOToCBRColumn(columnByRealreportId);
            return BigdataResult.ok(cbrColumns);
        }else{
            return BigdataResult.build(500,"操作失败！！");
        }

    }

    /**
     * 保存更新报表查询sql
     * @param realreportId
     * @param querySqlVo
     * @return
     */
    @PostMapping("/{realreportId}/sqls")
    public BigdataResult saveCbrSqls(@PathVariable String realreportId
            , @RequestBody QuerySqlVo querySqlVo){
        List<Integer> delSqlIndex = querySqlVo.getDelSqlIndex();
        Boolean flag = querySqlService.delSqlLevel(realreportId, delSqlIndex);
        if(!flag){
            return BigdataResult.build(500,"操作失败！！");
        }
        //存储sql
        List<QuerySql> value = querySqlVo.getValue();
        int size = value.size();
        cbrService.changeLevelById(realreportId,size/2);
        Boolean aBoolean = querySqlService.saveOrupdateSql(QuerySqlDTO.transCbr2QuerySqlBO(realreportId, value));
        if(aBoolean){
            List<QuerySqlBO> sqlByRealreportId = querySqlService.getSqlByRealreportId(realreportId);
            List<QuerySql> querySqls = QuerySqlDTO.transQuerySqlBOToQuerySql(sqlByRealreportId);
            return BigdataResult.ok(querySqls);
        }
        return BigdataResult.build(500,"创建失败！！");
    }

    /**
     * 保存更新报表查询参数
     * @param realreportId
     * @param cbrParamVO
     * @return
     */
    @PostMapping("/{realreportId}/params")
    public BigdataResult saveCbrParams(@PathVariable String realreportId
            , @RequestBody CbrParamVO cbrParamVO){
        //删除指定参数
        List<Integer> delParamIds = cbrParamVO.getDelParamIds();
        Boolean flag = cbrParamService.deleteParamById(delParamIds);
        if(!flag){
            return BigdataResult.build(500,"操作失败！！");
        }
        //存储查询参数
        List<CBRParam> cbrParams = cbrParamVO.getParams();
        Boolean aBoolean = cbrParamService.saveOrupdateParam(CBRParamDTO.transCbr2CBRParamBO(realreportId, cbrParams));
        if(aBoolean){
            List<CBRParamBO> allParamsByRealreportId = cbrParamService.getAllParamsByRealreportId(realreportId);
            List<CBRParam> cbrParams1 = CBRParamDTO.transCBRParamBO2CBRParam(allParamsByRealreportId);
            return BigdataResult.ok(cbrParams1);
        }
        return BigdataResult.build(500,"操作失败！！");
    }

    /**
     * 切换报表状态
     * @param realreportId
     * @param status
     * @return
     */
    @RequestMapping("/{realreportId}/status")
    public BigdataResult changeStatusById(@PathVariable String realreportId,@RequestParam(defaultValue = "0") Integer status){
        Boolean aBoolean = cbrService.changeStatusById(realreportId, status);
        return BigdataResult.ok();
    }
}

