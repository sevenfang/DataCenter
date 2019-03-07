package com.chezhibao.bigdata.dbms.server.download.dao.impl;

import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dbms.server.dao.DefaultDao;
import com.chezhibao.bigdata.dbms.server.download.bo.TaskInfoBO;
import com.chezhibao.bigdata.dbms.server.download.dao.DownloadTaskDao;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
@Repository
public class DownloadTaskDaoImpl implements DownloadTaskDao {
    private DefaultDao defaultDao;
    private SqlTemplateService sqlTemplateService;

    public DownloadTaskDaoImpl(DefaultDao defaultDao, SqlTemplateService sqlTemplateService) {
        this.defaultDao = defaultDao;
        this.sqlTemplateService = sqlTemplateService;
    }

    @Override
    public Boolean delete(Integer id) {
        Map<String, Object> params = ParamsBean.newInstance().put("id", id).build();
        String sql = sqlTemplateService.getSql("download.task.delete", params);
        defaultDao.delete(sql, params);
        return true;
    }

    @Override
    public Boolean save(TaskInfoBO taskInfoBO) {
        Map<String, Object> params = ObjectCommonUtils.object2Map(taskInfoBO);
        String sql = sqlTemplateService.getSql("download.task.save", params);
        return defaultDao.insert(sql, params);
    }

    @Override
    public List<LinkedHashMap<String, Object>> query(TaskInfoBO taskInfoBO) {
        Map<String, Object> params = ObjectCommonUtils.object2Map(taskInfoBO);
        String sql = sqlTemplateService.getSql("download.task.query", params);
        return defaultDao.select(sql, params);
    }

    @Override
    public Boolean updateTask(TaskInfoBO taskInfoBO) {
        Map<String, Object> params = ObjectCommonUtils.object2Map(taskInfoBO);
        String sql = sqlTemplateService.getSql("download.task.updateTask", params);
        defaultDao.update(sql, params);
        return true;
    }
}
