package com.chezhibao.bigdata.datax.service.impl;

import com.chezhibao.bigdata.datax.dao.HiveMetaDao;
import com.chezhibao.bigdata.datax.service.HiveMetaService;
import com.chezhibao.bigdata.datax.vo.FormItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/8.
 */
@Service
public class HiveMetaServiceImpl implements HiveMetaService {
    private HiveMetaDao hiveMetaDao;

    @Autowired
    public HiveMetaServiceImpl(HiveMetaDao hiveMetaDao) {
        this.hiveMetaDao = hiveMetaDao;
    }

    @Override
    public List<FormItemVo> getDbInfo() {
        List<Map<String, Object>> dbs = hiveMetaDao.getDbs();
        List<FormItemVo> formItemVos = new ArrayList<>();
        FormItemVo formItemVo;
        for (Map<String,Object> m : dbs){
            formItemVo=new FormItemVo();
            formItemVo.setValue(m.get("DB_ID")+"");
            formItemVo.setLabel(m.get("NAME")+"");
            formItemVos.add(formItemVo);
        }
        return formItemVos;
    }

    @Override
    public List<FormItemVo> getTables(Integer dbId) {
        List<Map<String, Object>> tablesByDbId = hiveMetaDao.getTablesByDbId(dbId);
        List<FormItemVo> formItemVos = new ArrayList<>();
        FormItemVo formItemVo;
        for (Map<String,Object> m : tablesByDbId){
            formItemVo=new FormItemVo();
            formItemVo.setValue(m.get("TBL_ID")+"");
            formItemVo.setLabel(m.get("TBL_NAME")+"");
            formItemVos.add(formItemVo);
        }
        return formItemVos;
    }

    @Override
    public List<FormItemVo> getColes(Integer tableId) {
        List<Map<String, Object>> colsByTableId = hiveMetaDao.getColsByTableId(tableId);
        List<FormItemVo> formItemVos = new ArrayList<>();
        FormItemVo formItemVo;
        for (Map<String,Object> m : colsByTableId){
            formItemVo=new FormItemVo();
            formItemVo.setValue(m.get("TYPE_NAME")+"");
            formItemVo.setLabel(m.get("COLUMN_NAME")+"");
            formItemVo.setIndex(m.get("INTEGER_IDX")+"");
            formItemVos.add(formItemVo);
        }
        return formItemVos;
    }

    @Override
    public List<FormItemVo> getDBSchema() {
        List<Map<String, Object>> dbs = hiveMetaDao.getDBSchema();
        List<FormItemVo> formItemVos = new ArrayList<>();
        FormItemVo db;
        FormItemVo tbl;
        for (Map<String,Object> m : dbs){
            db=new FormItemVo();
            db.setValue(m.get("DB_ID")+"");
            db.setLabel(m.get("NAME")+"");
            if(!formItemVos.contains(db)){
                formItemVos.add(db);
            }
            int i = formItemVos.indexOf(db);
            tbl=new FormItemVo();
            tbl.setValue(m.get("TBL_ID")+"");
            tbl.setLabel(m.get("TBL_NAME")+"");
            formItemVos.get(i).getChildren().add(tbl);
        }
        return formItemVos;
    }

    @Override
    public String getMysqlQuerySql(Integer tblId) {
        List<Map<String, Object>> sqlWithoutChar10AndChar13 = hiveMetaDao.getSqlWithoutChar10AndChar13(tblId);
        if(sqlWithoutChar10AndChar13.size()==0){
            return "";
        }
        return sqlWithoutChar10AndChar13.get(0).get("query_sql")+"";
    }
}
