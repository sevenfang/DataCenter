package com.chezhibao.bigdata.dbms.server.controller;

import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.dbms.server.auth.annotation.Admin;
import com.chezhibao.bigdata.dbms.server.bo.DBInstanceSchemaBO;
import com.chezhibao.bigdata.dbms.server.datasource.DynamicDataSource;
import com.chezhibao.bigdata.dbms.server.dto.DataSourceDTO;
import com.chezhibao.bigdata.dbms.server.service.DataBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 管理员管理接口
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/23.
 */
@RestController
@RequestMapping("/dbms/server/instance")
@Slf4j
public class AdminController {

    private DataBaseService dataBaseService;

    private DynamicDataSource dynamicDataSource;

    public AdminController(DataBaseService dataBaseService,
                           DynamicDataSource dynamicDataSource) {
        this.dataBaseService = dataBaseService;
        this.dynamicDataSource = dynamicDataSource;
    }

    @PostConstruct
    public void init() {
        Map<Object, Object> allCustomerDataSource = dataBaseService.getAllCustomerDataSource();
        dynamicDataSource.addDataSource(allCustomerDataSource);
    }

    @RequestMapping("/save/update")
    @Admin
    public BigdataResult saveOrUpdateDB(@RequestBody DBInstanceSchemaBO dbInstanceSchemaBO) {
        Integer id = dbInstanceSchemaBO.getId();
        dataBaseService.saveOrUpdateDBInstance(dbInstanceSchemaBO);
        if (ObjectUtils.isEmpty(id)) {
            String name = dbInstanceSchemaBO.getName();
            log.debug("【数据库管理系统】添加新数据源，name:{}", name);
            dynamicDataSource.addDataSource(name, DataSourceDTO.transDBInstanceSchemaBOToDS(dbInstanceSchemaBO));
        }
        return BigdataResult.ok();
    }

    @RequestMapping("/get/all")
    @Admin
    public BigdataResult getAllDataSources() {
        List<LinkedHashMap<String, Object>> dbInstances = dataBaseService.getDBInstancesAndName();
        return BigdataResult.ok(dbInstances);
    }


    @RequestMapping("/del/{id}")
    @Admin
    public BigdataResult delDBInstance(@PathVariable("id") Integer id) {
        //TODO 检查是否存在
        DBInstanceSchemaBO dbInstanceById = dataBaseService.getDBInstanceById(id);

        if (ObjectUtils.isEmpty(dbInstanceById)) {
            return BigdataResult.build(2004, "数据库实例不存在！！");
        }
        dataBaseService.delDBInstance(id);
        String name = dbInstanceById.getName();
        //删除数据源
        dynamicDataSource.delDataSource(name);
        return BigdataResult.ok();
    }

    @RequestMapping("/get/{id}")
    @Admin
    public BigdataResult getDBInstance(@PathVariable("id") Integer id) {

        DBInstanceSchemaBO dbInstanceById = dataBaseService.getDBInstanceById(id);
        if (ObjectUtils.isEmpty(dbInstanceById)) {
            return BigdataResult.build(2004, "数据库实例不存在！！");
        }
        return BigdataResult.ok(dbInstanceById);
    }
}
