package com.chezhibao.bigdata.dao.impl;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.common.utils.DateUtils;
import com.chezhibao.bigdata.dao.CBRCommonDao;
import com.chezhibao.bigdata.dao.CustomerDao;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/5/28.
 */
@Repository
@Slf4j
public class CustomerDaoImpl implements CustomerDao {

    @Resource(name = "cBRCommonDao")
    private CBRCommonDao cBRCommonDao;
    private SqlTemplateService sqlTemplateService;

    @Autowired
    public CustomerDaoImpl(SqlTemplateService sqlTemplateService) {
        this.sqlTemplateService = sqlTemplateService;
    }

    @Override
    public String getMaxLoadDateTime() {
        Map<String, Object> param = new HashMap<String, Object>();
        String sql = sqlTemplateService.getSql("customer.getMaxLoadDateTime", param);
        List<Map<String, Object>> maxTime = cBRCommonDao.select(sql, param);
        if (!ObjectUtils.isEmpty(maxTime)) {
            Object queryTime = maxTime.get(0).get("queryTime");
            if (!ObjectUtils.isEmpty(queryTime)) {
                return queryTime.toString();
            }
        }
        return "";
    }

    @Override
    public List<Map<String, Object>> loadData(String id, int type, String time, List<Integer> orgIds) {
        List<Map<String, Object>> report = null;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgIds", orgIds);
        if (!StringUtils.isEmpty(id)) {
            param.put("id", id);
        }
        param.put("queryTime", time);
        switch (type) {
            case 0:
                report = queryWholeReport(param);
                break;
            case 1:
                report = querySaleDeptReport(param);
                break;
            case 2:
                report = querySaleAreaReport(param);
                break;
            case 3:
                report = queryCityReport(param);
                break;
            default:
                    break;
        }
        log.debug("【实时报表】loadData返回结果：{}",report);
        return report;
    }

    private List<Map<String, Object>> queryWholeReport(Map<String, Object> param) {
        log.debug("【实时报表】queryWholeReport查询参数：{}",param);


        String sql = sqlTemplateService.getSql("customer.queryWholeReport", param);
        List<Map<String, Object>> currentDayReport = cBRCommonDao.select(sql, param);

        String yesterdayTime = getNearQueryTime(param.get("queryTime").toString());
        if (StringUtils.isEmpty(yesterdayTime)) {
            return currentDayReport;
        }
        param.put("queryTime", yesterdayTime);
         sql = sqlTemplateService.getSql("customer.queryWholeReport", param);
        List<Map<String, Object>> yesterdayReport = cBRCommonDao.select(sql, param);
        List<Map<String, Object>> result = appendYesterday(currentDayReport, yesterdayReport);
        log.debug("【实时报表】queryWholeReport查询结果：{}",result);
        return result;
    }

    /**
     * 查询整个部门的统计，按照销售片区进行分组统计
     *
     * @param param
     * @return
     */
    private List<Map<String, Object>> querySaleDeptReport(Map<String, Object> param) {
        log.debug("【实时报表】querySaleDeptReport查询参数：{}",param);
        String yesterdayTime = getNearQueryTime(param.get("queryTime").toString());
        String sql = sqlTemplateService.getSql("customer.querySaleDeptReport", param);
        List<Map<String, Object>> currentDayReport = cBRCommonDao.select(sql, param);

        if (StringUtils.isEmpty(yesterdayTime))
        {
            return currentDayReport;
        }
        param.put("queryTime", yesterdayTime);
         sql = sqlTemplateService.getSql("customer.querySaleDeptReport", param);
        List<Map<String, Object>> yesterdayReport = cBRCommonDao.select(sql, param);
        List<Map<String, Object>> result = appendYesterday(currentDayReport, yesterdayReport);
        log.debug("【实时报表】querySaleDeptReport查询结果：{}",result);
        return result;
    }

    /**
     * 查询片区实时统计，按照运营城市分组统计
     *
     * @param param
     * @return
     */
    private List<Map<String, Object>> querySaleAreaReport(Map<String, Object> param) {
        log.debug("【实时报表】querySaleAreaReport查询参数：{}",param);
        String yesterdayTime = getNearQueryTime(param.get("queryTime").toString());
        String sql = sqlTemplateService.getSql("customer.querySaleAreaReport", param);
        List<Map<String, Object>> currentDayReport = cBRCommonDao.select(sql, param);
        if (StringUtils.isEmpty(yesterdayTime))
        {
            return currentDayReport;
        }
        param.put("queryTime", yesterdayTime);
        sql = sqlTemplateService.getSql("customer.querySaleAreaReport", param);
        List<Map<String, Object>> yesterdayReport = cBRCommonDao.select(sql, param);
        List<Map<String, Object>> result = appendYesterday(currentDayReport, yesterdayReport);
        log.debug("【实时报表】querySaleAreaReport查询结果：{}",result);
        return result;
    }

    /**
     * 查询运营城市实时统计数据，按照人员分组统计
     *
     * @param param
     * @return
     */
    private List<Map<String, Object>> queryCityReport(Map<String, Object> param) {
        log.debug("【实时报表】queryCityReport查询参数：{}",param);
        String yesterdayTime = getNearQueryTime(param.get("queryTime").toString());
        String sql = sqlTemplateService.getSql("customer.queryCityReport", param);
        List<Map<String, Object>> currentDayReport = cBRCommonDao.select(sql, param);
        if (StringUtils.isEmpty(yesterdayTime))
        {
            return currentDayReport;
        }
        param.put("queryTime", yesterdayTime);
        sql = sqlTemplateService.getSql("customer.queryCityReport", param);
        List<Map<String, Object>> yesterdayReport = cBRCommonDao.select(sql, param);
        List<Map<String, Object>> result = appendYesterday(currentDayReport, yesterdayReport);
        log.debug("【实时报表】queryCityReport查询结果：{}",result);
        return result;
    }

    /**
     * 获取昨天同比有结果的最近的一个时间，时间在指定范围内，目前制定是10分钟之内
     *
     * @param queryTime
     * @return
     */
    private String getNearQueryTime(String queryTime) {
        int precision = 20;
        Date currentDate = DateUtils.parse(queryTime, DateUtils.yyyy_MM_dd_HH_mm);
        Date yesterday = DateUtils.add(currentDate, Calendar.DATE, -1);
        Date beginTime = DateUtils.add(yesterday, Calendar.MINUTE, 0 - precision);
        Date endTime = DateUtils.add(yesterday, Calendar.MINUTE, precision);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("queryTime", DateUtils.format(yesterday, DateUtils.yyyy_MM_dd_HH_mm));
        param.put("beginTime", DateUtils.format(beginTime, DateUtils.yyyy_MM_dd_HH_mm));
        param.put("endTime", DateUtils.format(endTime, DateUtils.yyyy_MM_dd_HH_mm));
        String sql = sqlTemplateService.getSql("customer.getNearQueryTime", param);
        List<Map<String, Object>> queryList = cBRCommonDao.select(sql, param);
        if (!ObjectUtils.isEmpty(queryList)) {
            Object queryTimeNear = queryList.get(0).get("queryTime");
            if (!ObjectUtils.isEmpty(queryTime)) {
                log.debug("【实时报表】确定昨天的最近时间：{}",queryTimeNear);
                return queryTimeNear.toString();
            }
        }
        return null;
    }


    /**
     * 在今日统计结果中追加昨天同比的数据
     *
     * @return
     */
    private List<Map<String, Object>> appendYesterday(List<Map<String, Object>> currentDayReport,
                                                      List<Map<String, Object>> yesterdayReport) {
        List<Map<String, Object>> report = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> row : currentDayReport)
        {
            if (row.get("id") == null || StringUtils.isEmpty(row.get("id").toString()))
            {
                report.add(row);
                continue;
            }
            String id = row.get("id").toString();
            for (Map<String, Object> yesterdayRow : yesterdayReport)
            {
                if (yesterdayRow.get("id") == null || StringUtils.isEmpty(yesterdayRow.get("id").toString()))
                {
                    continue;
                }
                if (id.equals(yesterdayRow.get("id").toString()))
                {
                    row.put("_totalCount", yesterdayRow.get("totalCount"));
                    row.put("_vaildCount", yesterdayRow.get("vaildCount"));
                    row.put("_dataTackingCount", yesterdayRow.get("dataTackingCount"));
                    row.put("_appointDetectionCount", yesterdayRow.get("appointDetectionCount"));
                    row.put("_transactionCount", yesterdayRow.get("transactionCount"));
                    row.put("_newtransactionCount", yesterdayRow.get("newtransactionCount"));
                    row.put("_serviceCharge", yesterdayRow.get("serviceCharge"));
                    row.put("_newdatacount", yesterdayRow.get("newdatacount"));
                    row.put("_serviceChargeAve", yesterdayRow.get("serviceChargeAve"));
                    row.put("_smc_transactionCount", yesterdayRow.get("smc_transactionCount"));
                    row.put("_smc_serviceCharge", yesterdayRow.get("smc_serviceCharge"));
                    row.put("_jmc_dataTackingCount", yesterdayRow.get("jmc_dataTackingCount"));
                    row.put("_jmc_transactionCount", yesterdayRow.get("jmc_transactionCount"));
                    row.put("_jmc_serviceCharge", yesterdayRow.get("jmc_serviceCharge"));
                    row.put("_smc_serviceChargeAve", yesterdayRow.get("smc_serviceChargeAve"));
                    row.put("_cuocheStartCount", yesterdayRow.get("cuocheStartCount"));
                    row.put("_cuocheTransactionCount", yesterdayRow.get("cuocheTransactionCount"));
                    break;
                }
            }
            report.add(row);
        }
        log.debug("【实时报表】查询结果：{}",report);
        return report;
    }


    /**
     * OCSA基础数据（新）
     * @return
     */
    @Override
    public String getMaxLoadOCSADateTimeNew() {
        //TODO
        Map<String, Object> param = new HashMap<String, Object>();
        String sql = sqlTemplateService.getSql("customer.getMaxLoadOCSADateTimeNew", param);
        List<LinkedHashMap<String, Object>> query = cBRCommonDao.query(sql, param);
        if(ObjectUtils.isEmpty(query)){
            log.warn("【CBR实时报表】OCSA查询时间为空！！！");
        }
        return query.get(0).get("queryTime")+"";

    }


    /**
     * OCSA基础数据（新）
     * @param id
     * @param type
     * @param time
     * @return
     */
    @Override
    public List<Map<String, Object>> loadOCSADataNew(String id, int type, String time, List<Integer> cityIds) {
        List<Map<String, Object>> report = null;
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("cityIds", cityIds);

        if (!StringUtils.isEmpty(id))
        {
            param.put("id", id);
        }
        param.put("queryTime", time);
        param.put("type", type);
        return queryOCSADataNew(param);
    }

    /**
     *
     * @param param
     * @return
     */
    private List<Map<String,Object>> queryOCSADataNew(Map<String, Object> param) {
        HashMap<String, Object> params = new HashMap<>();
//        String sql = sqlTemplateService.getSql("customer.deleteOCSADataNew",params );
//        cBRCommonDao.delete( sql,params);

        List<Map<String, Object>> currentDayReport= queryForList("customer.queryOCSADataNew",param);
        List<Map<String, Object>> maps = queryForList("customer.getOCSANearQueryTimeNew", param);
        if(ObjectUtils.isEmpty(maps)){
            log.error("【CBR实时报表】昨日时间为空！！！");
        }
        String yesterdayTime =  maps.get(0).get("queryTime")+"";

        if (StringUtils.isEmpty(yesterdayTime))
        {
            return currentDayReport;
        }

        param.put("queryTime", yesterdayTime);

        List<Map<String, Object>> yesterdayReport = queryForList("customer.queryOCSADataNew",param);

        return appendOCSAYesterday(currentDayReport, yesterdayReport);
    }

    private List<Map<String,Object>> queryForList(String sqlId,Map<String,Object> params){
        List<Map<String,Object>> result = new ArrayList<>();
        String sql = sqlTemplateService.getSql(sqlId, params);
        List<LinkedHashMap<String, Object>> query = cBRCommonDao.query(sql,params);
        if (ObjectUtils.isEmpty(query)){
            return result;
        }
        result.addAll(query);
        return result;
    }
    /**
     *
     * @param currentDayReport
     * @param yesterdayReport
     * @return
     */
    private List<Map<String,Object>> appendOCSAYesterday(List<Map<String, Object>> currentDayReport, List<Map<String, Object>> yesterdayReport) {
        List<Map<String, Object>> report = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> row : currentDayReport)
        {
            if (row.get("id") == null || StringUtils.isEmpty(row.get("id").toString()))
            {
                report.add(row);
                continue;
            }
            String id = row.get("id").toString();
            for (Map<String, Object> yesterdayRow : yesterdayReport)
            {
                if (yesterdayRow.get("id") == null || StringUtils.isEmpty(yesterdayRow.get("id").toString()))
                {
                    continue;
                }
                if (id.equals(yesterdayRow.get("id").toString()))
                {
                    row.put("_calldurtotal", yesterdayRow.get("calldurtotal"));
                    row.put("_outcallrealtotal", yesterdayRow.get("outcallrealtotal"));
                    row.put("_outcalltotal", yesterdayRow.get("outcalltotal"));
                    row.put("_outcall_accounted", yesterdayRow.get("outcall_accounted"));
                    row.put("_detectflag", yesterdayRow.get("detectflag"));
                    row.put("_effective_per_capita", yesterdayRow.get("effective_per_capita"));
                    row.put("_oneconnecttotal", yesterdayRow.get("oneconnecttotal"));
                    row.put("_threeconnecttotal", yesterdayRow.get("threeconnecttotal"));
                    break;
                }
            }
            report.add(row);
        }
        return report;
    }
}
