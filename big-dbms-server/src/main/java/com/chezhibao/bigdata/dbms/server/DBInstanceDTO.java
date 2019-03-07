package com.chezhibao.bigdata.dbms.server;

import com.chezhibao.bigdata.dbms.server.bo.DBInstanceSchemaBO;
import com.chezhibao.bigdata.dbms.server.vo.DBInstanceSchemaVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/8/23.
 */
public class DBInstanceDTO {
    public static DBInstanceSchemaVO transBO2VO(DBInstanceSchemaBO dbInstanceSchemaBO){
        DBInstanceSchemaVO dbInstanceSchemaVO= new DBInstanceSchemaVO();
        dbInstanceSchemaVO.setDbType(dbInstanceSchemaBO.getDbType());
        dbInstanceSchemaVO.setIcon("laptop");
        dbInstanceSchemaVO.setId(dbInstanceSchemaBO.getId());
        dbInstanceSchemaVO.setName(dbInstanceSchemaBO.getName());
        return dbInstanceSchemaVO;
    }
    public static List<DBInstanceSchemaVO> transBO2VO(List<DBInstanceSchemaBO> dbInstanceSchemaBOS){
        List<DBInstanceSchemaVO> dbInstanceSchemaVOS= new ArrayList<>();

        for(DBInstanceSchemaBO dbInstanceSchemaBO : dbInstanceSchemaBOS){
            dbInstanceSchemaVOS.add(transBO2VO(dbInstanceSchemaBO));
        }

        return dbInstanceSchemaVOS;
    }
}
