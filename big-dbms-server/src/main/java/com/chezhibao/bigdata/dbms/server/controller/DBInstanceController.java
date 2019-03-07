package com.chezhibao.bigdata.dbms.server.controller;

import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.common.pojo.ItemVo;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dbms.server.DBInstanceDTO;
import com.chezhibao.bigdata.dbms.server.RequestHelper;
import com.chezhibao.bigdata.dbms.server.auth.AdminAspect;
import com.chezhibao.bigdata.dbms.server.auth.bo.DbAuthBO;
import com.chezhibao.bigdata.dbms.server.auth.service.AuthenticationService;
import com.chezhibao.bigdata.dbms.server.bo.DBInstanceSchemaBO;
import com.chezhibao.bigdata.dbms.server.constants.DbTypeConstants;
import com.chezhibao.bigdata.dbms.server.service.DataBaseService;
import com.chezhibao.bigdata.dbms.server.service.DbSchemaService;
import com.chezhibao.bigdata.dbms.server.vo.ColumnVO;
import com.chezhibao.bigdata.dbms.server.vo.DBInstanceSchemaVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/23.
 */
@RestController
@RequestMapping("/dbms/server")
@Slf4j
public class DBInstanceController {

    private DataBaseService dataBaseService;

    private DbSchemaService dbSchemaService;

    private AuthenticationService authenticationService;

    public DBInstanceController(DataBaseService dataBaseService,
                                AuthenticationService authenticationService,
                                DbSchemaService dbSchemaService) {
        this.dataBaseService = dataBaseService;
        this.dbSchemaService = dbSchemaService;
        this.authenticationService = authenticationService;
    }

    /**
     * 查询获取到的数据库实例
     *
     * @return
     */
    @RequestMapping("/instance/get/menu")
    public BigdataResult getAllDataSourcesMenu() {
        Map<String, Object> params = new HashMap<>();
        //校验权限
        List<Integer> adminWhitelist = AdminAspect.getAdminWhitelist();
        Integer user = RequestHelper.getAndVerifyUserId();
        if (!adminWhitelist.contains(user)) {
            params.put("names", authenticationService.getDbInstance(new DbAuthBO()));
        }
        List<LinkedHashMap<String, Object>> dbInstances = dataBaseService.getDBInstances(params);
        List<DBInstanceSchemaBO> dbInstanceSchemaBOS = new ArrayList<>();
        for (Map<String, Object> m : dbInstances) {
            DBInstanceSchemaBO dbInstanceSchemaBO = ObjectCommonUtils.map2Object(m, DBInstanceSchemaBO.class);
            dbInstanceSchemaBOS.add(dbInstanceSchemaBO);
        }
        if (ObjectUtils.isEmpty(dbInstanceSchemaBOS)) {
            return BigdataResult.build(2004, "数据库实例不存在！！");
        }
        List<DBInstanceSchemaVO> dbInstanceSchemaVOS = DBInstanceDTO.transBO2VO(dbInstanceSchemaBOS);
        return BigdataResult.ok(dbInstanceSchemaVOS);
    }

    /**
     * 查询到数据库实例中的所有数据schema结构，包括表结构
     *
     * @param schemaVO
     * @return
     */
    @RequestMapping("/instance/get/schema")
    public BigdataResult getDBInstanceSchema(@RequestBody DBInstanceSchemaVO schemaVO) {
        BigdataResult result;
        Integer dbType = schemaVO.getDbType();
        String dsName = schemaVO.getName();
        Map<String, Object> params = new HashMap<>();
        //校验权限
        List<Integer> adminWhitelist = AdminAspect.getAdminWhitelist();
        Integer user = RequestHelper.getAndVerifyUserId();
        if (!adminWhitelist.contains(user)) {
            params.put("schemas", authenticationService.getSchema(new DbAuthBO()));
            params.put("tables", authenticationService.getDbTable(new DbAuthBO()));
            params.put("forbid", "%_cre");
        }

        Collection<ItemVo> dbSchema;
        switch (dbType) {
            case DbTypeConstants.MYSQL:
                dbSchema = dbSchemaService.getDbSchema(dsName,params);
                result = BigdataResult.ok(dbSchema);
                break;
            case DbTypeConstants.Presto:
                dbSchema = dbSchemaService.getHiveDbSchema(dsName,params);
                result = BigdataResult.ok(dbSchema);
                break;
            default:
                result = BigdataResult.build(2005, "没有找到对应的数据源类型！");
        }
        return result;
    }

    /**
     * 查询自动提示信息
     *
     * @param schemaVO
     * @return
     */
    @RequestMapping("/instance/get/schema/hint")
    public BigdataResult getDBInstanceSchemaHint(@RequestBody DBInstanceSchemaVO schemaVO) {
        BigdataResult result;
        Map<String, Object> params = new HashMap<>();
        //校验权限
        List<Integer> adminWhitelist = AdminAspect.getAdminWhitelist();
        Integer user = RequestHelper.getAndVerifyUserId();
        if (!adminWhitelist.contains(user)) {
            params.put("schemas", authenticationService.getSchema(new DbAuthBO()));
            params.put("tables", authenticationService.getDbTable(new DbAuthBO()));
            params.put("forbid", "%_cre");
        }
        Integer dbType = schemaVO.getDbType();
        String name = schemaVO.getName();
        Map<String, Set<String>> databaseSchemaHint;
        switch (dbType) {
            case DbTypeConstants.MYSQL:
                databaseSchemaHint = dbSchemaService.getDatabaseSchemaHint(name, dbType,params);
                result = BigdataResult.ok(databaseSchemaHint);
                break;
            case DbTypeConstants.Presto:
                databaseSchemaHint = dbSchemaService.getHiveDbColumnsSchema(name,params);
                result = BigdataResult.ok(databaseSchemaHint);
                break;
            default:
                result = BigdataResult.build(2005, "没有找到对应的数据源类型！");
        }
        return result;
    }

    /**
     * 查询指定表结构信息
     *
     * @param columnVO
     * @return
     */
    @RequestMapping("/get/schema/table/column")
    public BigdataResult getDBInstanceSchemaHint(@RequestBody ColumnVO columnVO) {
        String schema = columnVO.getSchema();
        String tableName = columnVO.getTableName();
        BigdataResult result;
        Map<String, Object> params = new HashMap<>();
        //校验权限
        List<Integer> adminWhitelist = AdminAspect.getAdminWhitelist();
        Integer user = RequestHelper.getAndVerifyUserId();
        if (!adminWhitelist.contains(user)) {
            List<String> schemas = authenticationService.getSchema(new DbAuthBO());
            List<String> dbTables = authenticationService.getDbTable(new DbAuthBO());
            if(!schemas.contains(schema)){
                throw new RuntimeException("权限不足！！");
            }
            if(dbTables.size()>0 && !dbTables.contains(tableName)){
                throw new RuntimeException("权限不足！！");
            }
        }
        params.put("schemas", new String[]{schema});
        params.put("tables", new String[]{tableName});
        Integer dbType = columnVO.getDbType();
        String name = columnVO.getDbName();
        List<LinkedHashMap<String,Object>> databaseSchemaHint;
        switch (dbType) {
            case DbTypeConstants.MYSQL:
                databaseSchemaHint = dbSchemaService.getDatabaseColumnInfo(name, dbType,params);
                result = BigdataResult.ok(databaseSchemaHint);
                break;
            case DbTypeConstants.Presto:
                databaseSchemaHint = dbSchemaService.getHiveDbColumnsInfo(name,params);
                result = BigdataResult.ok(databaseSchemaHint);
                break;
            default:
                result = BigdataResult.build(2005, "没有找到对应的数据源类型！");
        }
        return result;
    }
}
