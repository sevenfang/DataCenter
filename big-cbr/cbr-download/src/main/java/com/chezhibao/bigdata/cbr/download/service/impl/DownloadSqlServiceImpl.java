package com.chezhibao.bigdata.cbr.download.service.impl;

import com.chezhibao.bigdata.cbr.download.bo.DownloadSqlBO;
import com.chezhibao.bigdata.cbr.download.dao.DownloadDao;
import com.chezhibao.bigdata.cbr.download.service.DownloadSqlService;
import com.chezhibao.bigdata.cbr.download.util.DownloadUtils;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.template.ParamsBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/12.
 */
@Service
@Slf4j
public class DownloadSqlServiceImpl implements DownloadSqlService {

    @Autowired
    private DownloadDao downloadDao;

    @Override
    public List<DownloadSqlBO> getAllSql(String realreportId) {
        Map<String, Object> params = ParamsBean.newInstance().put("realreportId", realreportId).build();
        List<DownloadSqlBO> result = new ArrayList<>();
        List<LinkedHashMap<String, Object>> query = downloadDao.query("download.getAllSql", params);
        DownloadSqlBO sqlBO;
        for (Map<String, Object> m :query ){
            sqlBO = ObjectCommonUtils.map2Object(m, DownloadSqlBO.class);
            DownloadUtils.decodeSql(sqlBO);
            result.add(sqlBO);
        }
        return result;
    }

    @Override
    public Boolean saveSql(DownloadSqlBO downloadSqlBO) {
        Date date = new Date();
        downloadSqlBO.setCreatedTime(date);
        downloadSqlBO.setUpdatedTime(date);
        DownloadUtils.encodeSql(downloadSqlBO);
        Map<String, Object> params = ObjectCommonUtils.object2Map(downloadSqlBO);
        return downloadDao.insert("download.saveSql", params);
    }

    @Override
    public Boolean saveOrUpdateSql(DownloadSqlBO downloadSqlBO) {

        if (ObjectUtils.isEmpty(downloadSqlBO.getId())) {
            return saveSql(downloadSqlBO);
        }
        //已经存在就更新
        return updateSql(downloadSqlBO);
    }

    @Override
    public Boolean updateSql(DownloadSqlBO downloadSqlBO) {
        downloadSqlBO.setUpdatedTime(new Date());
        DownloadUtils.encodeSql(downloadSqlBO);
        Map<String, Object> params = ObjectCommonUtils.object2Map(downloadSqlBO);
        downloadDao.update("download.updateSql", params);
        return true;
    }

    @Override
    public Boolean delSql(Integer id) {
        Map<String, Object> params = ParamsBean.newInstance().put("id", id).build();
        downloadDao.delete("download.delSql", params);
        return true;
    }
}
