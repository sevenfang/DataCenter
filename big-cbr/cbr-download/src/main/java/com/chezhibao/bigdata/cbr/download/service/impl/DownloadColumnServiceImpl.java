package com.chezhibao.bigdata.cbr.download.service.impl;

import com.chezhibao.bigdata.cbr.download.bo.DownloadColumnBO;
import com.chezhibao.bigdata.cbr.download.dao.DownloadDao;
import com.chezhibao.bigdata.cbr.download.service.DownloadColumnService;
import com.chezhibao.bigdata.common.utils.ObjectCommonUtils;
import com.chezhibao.bigdata.dao.BigdataDao;
import com.chezhibao.bigdata.template.ParamsBean;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/11.
 */
@Repository
@Slf4j
public class DownloadColumnServiceImpl implements DownloadColumnService {

    @Autowired
    private DownloadDao downloadDao;

    @Override
    public List<DownloadColumnBO> getAllColumns(String realreportId) {
        Map<String, Object> params = ParamsBean.newInstance().put("realreportId", realreportId).build();
        List<Map<String, Object>> select = downloadDao.select("download.getAllColumns", params);
        return ObjectCommonUtils.map2Object(select,DownloadColumnBO.class);
    }

    @Override
    public Boolean saveColumn(DownloadColumnBO columnBO) {
        Date date = new Date();
        columnBO.setCreatedTime(date);
        columnBO.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(columnBO);
        return downloadDao.insert("download.saveColumn", params);
    }

    @Override
    public Boolean saveOrUpdateColumn(DownloadColumnBO columnBO) {
        if (ObjectUtils.isEmpty(columnBO.getId())){
            return saveColumn(columnBO);
        }else{
            return updateColumn(columnBO);
        }
    }

    @Override
    public Boolean updateColumn(DownloadColumnBO columnBO) {
        Date date = new Date();
        columnBO.setUpdatedTime(date);
        Map<String, Object> params = ObjectCommonUtils.object2Map(columnBO);
        downloadDao.update("download.updateColumn", params);
        return true;
    }

    @Override
    public Boolean delColumn(Integer id) {
        Map<String, Object> params = ParamsBean.newInstance().put("id", id).build();
        downloadDao.update("download.delColumn", params);
        return true;
    }
}
