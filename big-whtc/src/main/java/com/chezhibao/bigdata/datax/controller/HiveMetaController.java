package com.chezhibao.bigdata.datax.controller;

import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.datax.service.HiveMetaService;
import com.chezhibao.bigdata.datax.vo.FormItemVo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/8.
 */
@RestController
@RequestMapping("/whtc")
public class HiveMetaController {
    private HiveMetaService hiveMetaService;

    public HiveMetaController(HiveMetaService hiveMetaService) {
        this.hiveMetaService = hiveMetaService;
    }

    /**
     * 获取所有的数据库信息
     * @return
     */
    @RequestMapping("/hive/dbs")
    public BigdataResult getDbs(){
        List<FormItemVo> dbInfo = hiveMetaService.getDbInfo();
        return BigdataResult.ok(dbInfo);
    }

    /**
     * 获取数据库结构信息
     * @return
     */
    @RequestMapping("/hive/schema")
    public BigdataResult getSchema(){
        List<FormItemVo> dbInfo = hiveMetaService.getDBSchema();
        return BigdataResult.ok(dbInfo);
    }

    /**
     * 获取所有的表信息
     * @return
     */
    @RequestMapping("/hive/{dbId}/tables")
    public BigdataResult getDbs(@PathVariable("dbId")Integer dbId){
        List<FormItemVo> dbInfo = hiveMetaService.getTables(dbId);
        return BigdataResult.ok(dbInfo);
    }

    @RequestMapping("/hive/{tableId}/cols")
    public BigdataResult getCols(@PathVariable("tableId") Integer tableId){
        List<FormItemVo> dbInfo = hiveMetaService.getColes(tableId);
        return BigdataResult.ok(dbInfo);
    }

    @RequestMapping("/hive/{tableId}/querySql")
    public BigdataResult getQuerySql(@PathVariable("tableId") Integer tableId){
        String querySql = hiveMetaService.getMysqlQuerySql(tableId);
        if(StringUtils.isEmpty(querySql)){
            BigdataResult.ok("自动生成sql失败！请自行拼装！");
        }
        return BigdataResult.ok(querySql);
    }

}
