package com.chezhibao.bigdata.dao.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.chezhibao.bigdata.common.utils.DateUtils;
import com.chezhibao.bigdata.common.utils.SortUtils;
import com.chezhibao.bigdata.dao.CBRCommonDao;
import com.chezhibao.bigdata.dao.MarketDao;
import com.chezhibao.bigdata.template.service.SqlTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("marketDao")
public class MarketDaoImpl implements MarketDao
{

	@Reference(check = false)
	private CBRCommonDao cbrCommonDao;

	@Autowired
	private SqlTemplateService sqlTemplateService;

	private List<Map<String,Object>> queryForList(String sqlId,Map<String,Object> params){
		List<Map<String,Object>> result = new ArrayList<>();
		String sql = sqlTemplateService.getSql(sqlId, params);
		List<LinkedHashMap<String, Object>> query = cbrCommonDao.query(sql,params);
		if (ObjectUtils.isEmpty(query)){
			return result;
		}
		result.addAll(query);
		return result;
	}

	@Override
	public List<Map<String, Object>> loadData(String id, int type, String time, String channel, String parentChannel,
			String childChannel)
	{
		List<Map<String, Object>> report = null;
		Map<String, Object> param = new HashMap<>();
		if (StringUtils.isNotEmpty(id))
		{
			param.put("id", id);
		}
		param.put("queryTime", time);
		switch (type)
		{
			case 0:
				report = queryWholeReport(param);
				break;
			case 1:
				report = queryBigChannelSortsReport(param, channel, parentChannel, childChannel);
				break;
			case 2:
				report = queryRegionReport(param, childChannel);
				break;
			case 3:
				report = queryChannelReport(param, childChannel);
				break;
		}
		return report;
	}

	/**
	 * 缓存所有存在子节点的分类
	 *
	 * @return
	 */
	private Map<String, Object> getChanelChildMap()
	{
		List<Map<String, Object>> list = queryForList("market.getChildSource", new HashMap<String, Object>());
		Map<String, Object> map = new HashMap<>();
		if (list != null && list.size() > 0)
		{

			for (Map<String, Object> mapTmp : list)
			{
				map.put(mapTmp.get("supId").toString(), mapTmp.get("count"));
			}
		}
		return map;
	}

	/**
	 * 查询整个市场部数据统计
	 *
	 * @param param
	 * @return
	 */
	private List<Map<String, Object>> queryWholeReport(Map<String, Object> param)
	{
		List<Map<String, Object>> yesterdayReport = null;
		List<Map<String, Object>> lastWeekReport = null;
		List<Map<String, Object>> lastMonthReport = null;
		List<Map<String, Object>> currentDayReport = queryForList("market.queryWholeReport", param);
		String currentTime = param.get("queryTime").toString();
		String yesterdayTime = getNearQueryTime(currentTime);
		if (StringUtils.isNotEmpty(yesterdayTime))
		{
			param.put("queryTime", yesterdayTime);
			yesterdayReport = queryForList("market.queryWholeReport", param);
		}
		String lastWeekTime = getLastWeekQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastWeekTime))
		{
			param.put("queryTime", lastWeekTime);
			lastWeekReport = queryForList("market.queryWholeReport", param);
		}
		String lastMonthTime = getLastMonthQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastMonthTime))
		{
			param.put("queryTime", lastMonthTime);
			lastMonthReport = queryForList("market.queryWholeReport", param);
		}
		return appendData(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport);
	}

	private List<Map<String, Object>> appendSourceData(List<Map<String, Object>> currentDayReport,
			List<Map<String, Object>> yesterdayReport, List<Map<String, Object>> lastWeekReport,
			List<Map<String, Object>> lastMonthReport, String currentTime, Map<String, Object> childChannelMap,
			String channel, String childChannel, Map<String, Map<String, Object>> sumMap, String parentChannelId)
	{
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> report = new ArrayList<>();
		Map<String, Object> map = getAllId(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport);

		if (map != null && map.size() > 0)
		{
			for (String id : map.keySet())
			{
				Object item = map.get(id);
				Map<String, Object> row = new HashMap<>();
				row.put("id", id);
				row.put("item", item);
				if (getChildSource(id, childChannelMap))
				{
					param.put("queryTime", currentTime);
					param.put("channel", channel);
					param.put("childChannel", childChannel);
					param.put("id", id);
					List<Map<String, Object>> currentDayChildReport = queryForList("market.queryChildChannelSortsReport", param);
					List<Map<String, Object>> yesterdayChildReport = null;
					List<Map<String, Object>> lastWeekChildReport = null;
					List<Map<String, Object>> lastMonthChildReport = null;

					String yesterdayTime = getNearQueryTime(currentTime);
					if (StringUtils.isNotEmpty(yesterdayTime))
					{
						param.put("queryTime", yesterdayTime);
						yesterdayChildReport = queryForList("market.queryChildChannelSortsReport", param);
					}
					String lastWeekTime = getLastWeekQueryTime(currentTime);
					if (StringUtils.isNotEmpty(lastWeekTime))
					{
						param.put("queryTime", lastWeekTime);
						lastWeekChildReport = queryForList("market.queryChildChannelSortsReport", param);
					}
					String lastMonthTime = getLastMonthQueryTime(currentTime);
					if (StringUtils.isNotEmpty(lastMonthTime))
					{
						param.put("queryTime", lastMonthTime);
						lastMonthChildReport = queryForList("market.queryChildChannelSortsReport", param);
					}

					if (sumMap == null)
					{
						sumMap = new HashMap<>();
					}

					if (sumMap.get(id) == null)
					{
						Map<String, Object> TmpMap = new HashMap<>();
						sumMap.put(id, TmpMap);
					}
					List<Map<String, Object>> resultList = appendSourceData(currentDayChildReport, yesterdayChildReport,
							lastWeekChildReport, lastMonthChildReport, currentTime, childChannelMap, channel,
							childChannel, sumMap, id);
					// 添加排序
					SortUtils.marketSort(resultList);
					row.put("children", resultList);

					row.putAll(sumMap.get(id));
					// 需要累加到上一层
					calc(sumMap.get(parentChannelId), sumMap.get(id));
				}
				else
				{
					addParam(id, currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport, sumMap, row,
							parentChannelId);
				}
				row.put("state", "closed");
				row.put("time", currentTime);
				row.put("type", 2);
				report.add(row);
			}

		}
		return report;
	}

	private void calc(Map<String, Object> parentMap, Map<String, Object> childMap)
	{
		if (parentMap != null && childMap != null)
		{
			for (String key : childMap.keySet())
			{
				Object s = parentMap.get(key);
				Integer parent = s!=null? Integer.valueOf(s.toString()):0;
				Integer child = Integer.valueOf(childMap.get(key).toString());
				parentMap.put(key, parent + child);
			}
		}
	}

	private List<Map<String, Object>> appendSourceCityData(List<Map<String, Object>> currentDayReport,
			List<Map<String, Object>> yesterdayReport, List<Map<String, Object>> lastWeekReport,
			List<Map<String, Object>> lastMonthReport, String currentTime, Map<String, Object> childChannelMap,
			String channel, String childChannel, Map<String, Map<String, Object>> sumMap, String parentChannelId)
	{
		List<Map<String, Object>> report = new ArrayList<>();
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> map = getAllId(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport);

		if (map != null && map.size() > 0)
		{
			for (String id : map.keySet())
			{
				Object item = map.get(id);
				Map<String, Object> row = new HashMap<>();
				row.put("id", id);
				row.put("item", item);
				String parentId = id.split("_")[0];
				String cityId = id.split("_")[1];
				param.put("parentId", parentId);
				param.put("cityId", cityId);
				if (StringUtils.isNotEmpty(channel))
				{
					param.put("channel", channel + "_" + cityId);
				}
				param.put("childChannel", childChannel);
				if (getChildSource(parentId, childChannelMap))// 存在子节点
				{
					param.put("queryTime", currentTime);
					List<Map<String, Object>> currentDayChildReport = queryForList("market.queryChildRegionChannelReport", param);
					List<Map<String, Object>> yesterdayChildReport = null;
					List<Map<String, Object>> lastWeekChildReport = null;
					List<Map<String, Object>> lastMonthChildReport = null;
					String yesterdayTime = getNearQueryTime(currentTime);
					if (StringUtils.isNotEmpty(yesterdayTime))
					{
						param.put("queryTime", yesterdayTime);
						yesterdayChildReport = queryForList("market.queryChildRegionChannelReport", param);
					}
					String lastWeekTime = getLastWeekQueryTime(currentTime);
					if (StringUtils.isNotEmpty(lastWeekTime))
					{
						param.put("queryTime", lastWeekTime);
						lastWeekChildReport = queryForList("market.queryChildRegionChannelReport", param);
					}
					String lastMonthTime = getLastMonthQueryTime(currentTime);
					if (StringUtils.isNotEmpty(lastMonthTime))
					{
						param.put("queryTime", lastMonthTime);
						lastMonthChildReport = queryForList("market.queryChildRegionChannelReport", param);
					}

					if (sumMap == null)
					{
						sumMap = new HashMap<>();
					}
					if (sumMap.get(parentId) == null)
					{
						Map<String, Object> TmpMap = new HashMap<>();
						sumMap.put(parentId, TmpMap);
					}
					List<Map<String, Object>> resultList = appendSourceCityData(currentDayChildReport,
							yesterdayChildReport, lastWeekChildReport, lastMonthChildReport, currentTime,
							childChannelMap, channel, childChannel, sumMap, parentId);
					// 添加排序
					SortUtils.marketSort(resultList);

					row.put("children", resultList);
					row.putAll(sumMap.get(parentId));
					calc(sumMap.get(parentChannelId), sumMap.get(parentId));
				}
				else
				{
					addParam(id, currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport, sumMap, row,
							parentChannelId);
				}
				row.put("state", "closed");
				row.put("time", currentTime);
				row.put("type", 3);
				report.add(row);
			}
		}
		return report;
	}

	private void addParam(String id, List<Map<String, Object>> currentDayReport,
			List<Map<String, Object>> yesterdayReport, List<Map<String, Object>> lastWeekReport,
			List<Map<String, Object>> lastMonthReport, Map<String, Map<String, Object>> sumMap, Map<String, Object> row,
			String parentChannelId)
	{
		if (currentDayReport != null)
		{
			for (Map<String, Object> currentDayRow : currentDayReport)
			{
				if (isEmpty(currentDayRow.get("id").toString()))
				{
					continue;
				}
				if (id.equals(currentDayRow.get("id").toString()))
				{
					row.put("totalCount", currentDayRow.get("totalCount"));
					row.put("vaildCount", currentDayRow.get("vaildCount"));
					row.put("appointDetectionCount", currentDayRow.get("appointDetectionCount"));

					if (sumMap != null)
					{
						countMap(sumMap, parentChannelId, "totalCount", row.get("totalCount"));
						countMap(sumMap, parentChannelId, "vaildCount", row.get("vaildCount"));
						countMap(sumMap, parentChannelId, "appointDetectionCount", row.get("appointDetectionCount"));
					}
					break;
				}
			}
		}
		if (yesterdayReport != null)
		{
			for (Map<String, Object> yesterdayRow : yesterdayReport)
			{
				if (isEmpty(yesterdayRow.get("id").toString()))
				{
					continue;
				}
				if (id.equals(yesterdayRow.get("id").toString()))
				{
					row.put("_totalCount", yesterdayRow.get("totalCount"));
					row.put("_vaildCount", yesterdayRow.get("vaildCount"));
					row.put("_appointDetectionCount", yesterdayRow.get("appointDetectionCount"));
					if (sumMap != null)
					{
						countMap(sumMap, parentChannelId, "_totalCount", yesterdayRow.get("totalCount"));
						countMap(sumMap, parentChannelId, "_vaildCount", yesterdayRow.get("vaildCount"));
						countMap(sumMap, parentChannelId, "_appointDetectionCount",
								yesterdayRow.get("appointDetectionCount"));
					}

					break;
				}
			}
		}
		if (lastWeekReport != null)
		{
			for (Map<String, Object> lastWeekRow : lastWeekReport)
			{
				if (isEmpty(lastWeekRow.get("id").toString()))
				{
					continue;
				}
				if (id.equals(lastWeekRow.get("id").toString()))
				{
					row.put("weekTotalCount", lastWeekRow.get("totalCount"));
					row.put("weekVaildCount", lastWeekRow.get("vaildCount"));
					row.put("weekAppointDetectionCount", lastWeekRow.get("appointDetectionCount"));
					if (sumMap != null)
					{
						countMap(sumMap, parentChannelId, "weekTotalCount", lastWeekRow.get("totalCount"));
						countMap(sumMap, parentChannelId, "weekVaildCount", lastWeekRow.get("vaildCount"));
						countMap(sumMap, parentChannelId, "weekAppointDetectionCount",
								lastWeekRow.get("appointDetectionCount"));
					}
					break;
				}
			}
		}
		if (lastMonthReport != null)
		{
			for (Map<String, Object> lastMonthRow : lastMonthReport)
			{
				if (isEmpty(lastMonthRow.get("id").toString()))
				{
					continue;
				}
				if (id.equals(lastMonthRow.get("id").toString()))
				{
					row.put("monthTotalCount", lastMonthRow.get("totalCount"));
					row.put("monthVaildCount", lastMonthRow.get("vaildCount"));
					row.put("monthAppointDetectionCount", lastMonthRow.get("appointDetectionCount"));
					if (sumMap != null)
					{
						countMap(sumMap, parentChannelId, "monthTotalCount", lastMonthRow.get("totalCount"));
						countMap(sumMap, parentChannelId, "monthVaildCount", lastMonthRow.get("vaildCount"));
						countMap(sumMap, parentChannelId, "monthAppointDetectionCount",
								lastMonthRow.get("appointDetectionCount"));
					}

					break;
				}
			}
		}
	}

	private void countMap(Map<String, Map<String, Object>> sumMap, String id, String key, Object value)
	{
		if (sumMap.get(id) == null)
		{
			return;
		}
		Map<String, Object> tmpMap = sumMap.get(id);
		if (tmpMap.get(key) == null)
		{
			tmpMap.put(key, 0);
		}
		int value1 = Integer.valueOf(tmpMap.get(key).toString());
		int value2 = value == null ? 0 : Integer.valueOf(value.toString());
		tmpMap.put(key, value1 + value2);
	}

	private List<Map<String, Object>> queryBigChannelSortsReport(Map<String, Object> param, String channel,
			String parentChannel, String childChannel)
	{
		Map<String, Object> channelChildMap = getChanelChildMap();
		List<Map<String, Object>> yesterdayReport = null;
		List<Map<String, Object>> lastWeekReport = null;
		List<Map<String, Object>> lastMonthReport = null;
		if (StringUtils.isNotEmpty(parentChannel))
		{
			param.put("channel", parentChannel);
		}
		else
		{
			param.put("channel", channel);
		}
		param.put("childChannel", childChannel);
		List<Map<String, Object>> currentDayReport = queryForList("market.queryBigChannelSortsReport", param);
		String currentTime = param.get("queryTime").toString();
		String yesterdayTime = getNearQueryTime(currentTime);
		if (StringUtils.isNotEmpty(yesterdayTime))
		{
			param.put("queryTime", yesterdayTime);
			yesterdayReport = queryForList("market.queryBigChannelSortsReport", param);
		}
		String lastWeekTime = getLastWeekQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastWeekTime))
		{
			param.put("queryTime", lastWeekTime);
			lastWeekReport = queryForList("market.queryBigChannelSortsReport", param);
		}
		String lastMonthTime = getLastMonthQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastMonthTime))
		{
			param.put("queryTime", lastMonthTime);
			lastMonthReport = queryForList("market.queryBigChannelSortsReport", param);
		}
		return appendSourceData(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport, currentTime,
				channelChildMap, channel, childChannel, null, null);
	}

	/**
	 * 查询整个部门的统计，按照销售片区进行分组统计
	 *
	 * @param param
	 * @param childChannel
	 * @return
	 */
	private List<Map<String, Object>> queryRegionReport(Map<String, Object> param, String childChannel)
	{
		List<Map<String, Object>> yesterdayReport = null;
		List<Map<String, Object>> lastWeekReport = null;
		List<Map<String, Object>> lastMonthReport = null;
		param.put("childChannel", childChannel);
		List<Map<String, Object>> currentDayReport = queryForList("market.queryRegionReport", param);
		String currentTime = param.get("queryTime").toString();
		String yesterdayTime = getNearQueryTime(currentTime);
		if (StringUtils.isNotEmpty(yesterdayTime))
		{
			param.put("queryTime", yesterdayTime);
			yesterdayReport = queryForList("market.queryRegionReport", param);
		}
		String lastWeekTime = getLastWeekQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastWeekTime))
		{
			param.put("queryTime", lastWeekTime);
			lastWeekReport = queryForList("market.queryRegionReport", param);
		}
		String lastMonthTime = getLastMonthQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastMonthTime))
		{
			param.put("queryTime", lastMonthTime);
			lastMonthReport = queryForList("market.queryRegionReport", param);
		}
		return appendData(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport);
	}

	/**
	 * 查询片区实时统计，按照运营城市分组统计
	 *
	 * @param param
	 * @param childChannel
	 * @return
	 */
	private List<Map<String, Object>> queryChannelReport(Map<String, Object> param, String childChannel)
	{
		if (param.get("id") == null || StringUtils.isEmpty(param.get("id").toString()))
		{
			return null;
		}
		String id = param.get("id").toString();
		if (id.indexOf("_") == -1)
		{
			return null;
		}
		String parentId = id.split("_")[0];
		String cityId = id.split("_")[1];
		param.put("parentId", parentId);
		param.put("cityId", cityId);
		param.put("childChannel", childChannel);
		List<Map<String, Object>> yesterdayReport = null;
		List<Map<String, Object>> lastWeekReport = null;
		List<Map<String, Object>> lastMonthReport = null;
		List<Map<String, Object>> currentDayReport = queryForList("market.queryChannelReport", param);
		String currentTime = param.get("queryTime").toString();
		String yesterdayTime = getNearQueryTime(currentTime);
		if (StringUtils.isNotEmpty(yesterdayTime))
		{
			param.put("queryTime", yesterdayTime);
			yesterdayReport = queryForList("market.queryChannelReport", param);
		}
		String lastWeekTime = getLastWeekQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastWeekTime))
		{
			param.put("queryTime", lastWeekTime);
			lastWeekReport = queryForList("market.queryChannelReport", param);
		}
		String lastMonthTime = getLastMonthQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastMonthTime))
		{
			param.put("queryTime", lastMonthTime);
			lastMonthReport = queryForList("market.queryChannelReport", param);
		}
		return appendData(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport);
	}

	/**
	 * 获取昨天同比有结果的最近的一个时间，时间在指定范围内，目前制定是10分钟之内
	 *
	 * @param queryTime
	 * @return
	 */
	private String getNearQueryTime(String queryTime)
	{
		int precision = 20;
		Date currentDate = DateUtils.parse(queryTime, DateUtils.yyyy_MM_dd_HH_mm);
		Date yesterday = DateUtils.add(currentDate, Calendar.DATE, -1);
		Date beginTime = DateUtils.add(yesterday, Calendar.MINUTE, 0 - precision);
		Date endTime = DateUtils.add(yesterday, Calendar.MINUTE, precision);
		Map<String, Object> param = new HashMap<>();
		param.put("queryTime", DateUtils.format(yesterday, DateUtils.yyyy_MM_dd_HH_mm));
		param.put("beginTime", DateUtils.format(beginTime, DateUtils.yyyy_MM_dd_HH_mm));
		param.put("endTime", DateUtils.format(endTime, DateUtils.yyyy_MM_dd_HH_mm));
		List<Map<String,Object>> queryList = queryForList("market.getNearQueryTime", param);
		if (queryList != null && queryList.size() > 0) {
			return queryList.get(0).get("queryTime")+"";
		}
		return null;
	}

	/**
	 * 获取上周同比有结果的最近的一个时间，时间在指定范围内，目前制定是10分钟之内
	 *
	 * @param queryTime
	 * @return
	 */
	public String getLastWeekQueryTime(String queryTime)
	{
		int precision = 20;
		Date currentDate = DateUtils.parse(queryTime, DateUtils.yyyy_MM_dd_HH_mm);
		Date lastWeek = DateUtils.add(currentDate, Calendar.DATE, -7);
		Date beginTime = DateUtils.add(lastWeek, Calendar.MINUTE, 0 - precision);
		Date endTime = DateUtils.add(lastWeek, Calendar.MINUTE, precision);
		Map<String, Object> param = new HashMap<>();
		param.put("queryTime", DateUtils.format(lastWeek, DateUtils.yyyy_MM_dd_HH_mm));
		param.put("beginTime", DateUtils.format(beginTime, DateUtils.yyyy_MM_dd_HH_mm));
		param.put("endTime", DateUtils.format(endTime, DateUtils.yyyy_MM_dd_HH_mm));
		List<Map<String,Object>> queryList = queryForList("market.getNearQueryTime", param);
		if (queryList != null && queryList.size() > 0)
		{
			return queryList.get(0).get("queryTime")+"";
		}

		return null;
	}

	/**
	 * 获取上月同比有结果的最近的一个时间，时间在指定范围内，目前制定是10分钟之内
	 *
	 * @param queryTime
	 * @return
	 */
	public String getLastMonthQueryTime(String queryTime)
	{
		int precision = 20;
		Date currentDate = DateUtils.parse(queryTime, DateUtils.yyyy_MM_dd_HH_mm);
		Date lastMonth = DateUtils.add(currentDate, Calendar.MONTH, -1);
		if (checkDate(lastMonth, currentDate))
		{
			Date beginTime = DateUtils.add(lastMonth, Calendar.MINUTE, 0 - precision);
			Date endTime = DateUtils.add(lastMonth, Calendar.MINUTE, precision);
			Map<String, Object> param = new HashMap<>();
			param.put("queryTime", DateUtils.format(lastMonth, DateUtils.yyyy_MM_dd_HH_mm));
			param.put("beginTime", DateUtils.format(beginTime, DateUtils.yyyy_MM_dd_HH_mm));
			param.put("endTime", DateUtils.format(endTime, DateUtils.yyyy_MM_dd_HH_mm));
			List<Map<String,Object>> queryList = queryForList("market.getNearQueryTime", param);
			if (queryList != null && queryList.size() > 0)
			{
				return queryList.get(0).get("queryTime")+"";
			}
		}
		return null;
	}

	/**
	 * 检查日期格式时候正确, 比如3月31里, 2月31日报错
	 *
	 * @param date
	 * @param currentDate
	 * @return
	 */
	private boolean checkDate(Date date, Date currentDate)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(date);
		String dateStr = String.format("%d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, day);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		format.setLenient(false);
		try
		{
			format.parse(dateStr);
		}
		catch (ParseException e)
		{
			return false;
		}
		return true;
	}

	/**
	 * 在今日统计结果中追加昨天同比的数据
	 *
	 * @param currentDayReport
	 * @param lastWeekReport
	 * @param lastMonthReport
	 * @return
	 */
	private List<Map<String, Object>> appendData(List<Map<String, Object>> currentDayReport,
			List<Map<String, Object>> yesterdayReport, List<Map<String, Object>> lastWeekReport,
			List<Map<String, Object>> lastMonthReport) {

		List<Map<String, Object>> report = new ArrayList<>();
		Map<String, Object> map = getAllId(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport);
		if (map != null && map.size() > 0) {
			for (String id : map.keySet()) {
				Object item = map.get(id);
				Map<String, Object> row = new HashMap<>();
				row.put("id", id);
				row.put("item", item);
				// 处理当前时间的数据
				if (currentDayReport != null) {
					boolean flag = false;
					for (Map<String, Object> currentDayRow : currentDayReport) {
						if (isEmpty(currentDayRow.get("id"))) {
							continue;
						}
						if (id.equals(currentDayRow.get("id").toString())) {
							row.put("totalCount", currentDayRow.get("totalCount"));
							row.put("vaildCount", currentDayRow.get("vaildCount"));
							row.put("appointDetectionCount", currentDayRow.get("appointDetectionCount"));
							flag = true;
							break;
						}
					}

					if (!flag) {	// 没有值手动设置0
						row.put("totalCount", 0);
						row.put("vaildCount", 0);
						row.put("appointDetectionCount", 0);
					}
				}
				// 处理昨日的数据（环比）
				if (yesterdayReport != null) {
					boolean flag = false;
					for (Map<String, Object> yesterdayRow : yesterdayReport) {
						if (isEmpty(yesterdayRow.get("id"))) {
							continue;
						}
						if (id.equals(yesterdayRow.get("id").toString())) {
							row.put("_totalCount", yesterdayRow.get("totalCount"));
							row.put("_vaildCount", yesterdayRow.get("vaildCount"));
							row.put("_appointDetectionCount", yesterdayRow.get("appointDetectionCount"));
							flag = true;
							break;
						}
					}
					if (!flag) {	// 没有值手动设置0
						row.put("_totalCount", 0);
						row.put("_vaildCount", 0);
						row.put("_appointDetectionCount", 0);
					}
				}
				// 处理上周同比数据
				if (lastWeekReport != null) {
					boolean flag = false;
					for (Map<String, Object> lastWeekRow : lastWeekReport) {
						if (isEmpty(lastWeekRow.get("id"))) {
							continue;
						}
						if (id.equals(lastWeekRow.get("id").toString())) {
							row.put("weekTotalCount", lastWeekRow.get("totalCount"));
							row.put("weekVaildCount", lastWeekRow.get("vaildCount"));
							row.put("weekAppointDetectionCount", lastWeekRow.get("appointDetectionCount"));
							flag = true;
							break;
						}
					}
					if (!flag) {	// 没有值手动设置0
						row.put("weekTotalCount", 0);
						row.put("weekVaildCount", 0);
						row.put("weekAppointDetectionCount", 0);
					}
				}
				// 处理上月同比
				if (lastMonthReport != null) {
					boolean flag = false;
					for (Map<String, Object> lastMonthRow : lastMonthReport) {
						if (isEmpty(lastMonthRow.get("id"))) {
							continue;
						}
						if (id.equals(lastMonthRow.get("id").toString())) {
							row.put("monthTotalCount", lastMonthRow.get("totalCount"));
							row.put("monthVaildCount", lastMonthRow.get("vaildCount"));
							row.put("monthAppointDetectionCount", lastMonthRow.get("appointDetectionCount"));
							flag = true;
							break;
						}
					}
					if (!flag) {	// 没有值手动设置0
						row.put("monthTotalCount", 0);
						row.put("monthVaildCount", 0);
						row.put("monthAppointDetectionCount", 0);
					}
				}
				report.add(row);
			}
		}

		return report;
	}

	private boolean isEmpty(Object obj)
	{
		return obj == null || StringUtils.isEmpty(obj.toString());
	}

	private Map<String, Object> getAllId(List<Map<String, Object>> currentDayReport,
			List<Map<String, Object>> yesterdayReport, List<Map<String, Object>> lastWeekReport,
			List<Map<String, Object>> lastMonthReport)
	{
		Map<String, Object> map = new LinkedHashMap<>();
		// 当前时间数据
		if (currentDayReport != null && currentDayReport.size() > 0)
		{
			for (Map<String, Object> row : currentDayReport)
			{
					map.put(row.get("id").toString(), row.get("item").toString());
			}
		}
		// 昨日环比
		if (yesterdayReport != null && yesterdayReport.size() > 0)
		{
			for (Map<String, Object> row : yesterdayReport)
			{
				map.put(row.get("id").toString(), row.get("item").toString());
			}
		}
		// 上周同比
		if (lastWeekReport != null && lastWeekReport.size() > 0)
		{
			for (Map<String, Object> row : lastWeekReport)
			{
				map.put(row.get("id").toString(), row.get("item").toString());
			}
		}
		// 上周同比
		if (lastMonthReport != null && lastMonthReport.size() > 0)
		{
			for (Map<String, Object> row : lastMonthReport)
			{
				map.put(row.get("id").toString(), row.get("item").toString());
			}
		}
		return map;
	}

	@Override
	public String getMaxLoadDateTime()
	{
		Map<String, Object> param = new HashMap<>();
		List<Map<String, Object>> maps = queryForList("market.getMaxLoadDateTime", param);
		return maps.get(0).get("queryTime")+"";
	}

	@Override
	public List<Map<String, Object>> loadRegionData(String id, int type, String time, String channel,
			String parentChannel, String childChannel)
	{
		List<Map<String, Object>> report = null;
		Map<String, Object> param = new HashMap<>();
		if (StringUtils.isNotEmpty(id))
		{
			param.put("id", id);
		}
		param.put("queryTime", time);
		switch (type)
		{
			case 0:
				report = queryWholeRegionReport(param);
				break;
			case 1:
				report = queryCityRegionReport(param, childChannel);
				break;
			case 2:
				report = queryRegionChannelReport(param, channel, parentChannel, childChannel);
				break;
			case 3:
				report = queryCityChannelReport(param, childChannel);
				break;
		}
		return report;
	}

	@Override
	public List<Map<String, Object>> loadSourceData(String id, int type, String time, String channel,
													String parentChannel, String childChannel) {
		Map<String, Object> param = new HashMap<>();
		if (StringUtils.isNotEmpty(id)) {
			param.put("id", id);
		}
		param.put("queryTime", time);
		List<Map<String, Object>> report = null;
		switch (type) {
			case 0:
				report = queryWholeRegionReport(param);
				break;
			case 1:
				report = queryBigChannelSortsReport(param, channel, parentChannel, childChannel);
				break;
			case 2:
				report = querySmallChannelSortsReport(param, channel, parentChannel, childChannel);
//				report = null;
				break;
			case 3:
				report = querySmallChannelSortsCityReport(param, childChannel);
//				report = null;
				break;
		}
		return report;
	}

	private List<Map<String,Object>> querySmallChannelSortsCityReport(Map<String, Object> param, String childChannel) {
		String id = String.valueOf(param.get("id"));
		if(StringUtils.isEmpty(id)) {
			return null;
		} else {
			param.put("id", id.split("_")[1]);
		}
		List<Map<String, Object>> yesterdayReport = null;
		List<Map<String, Object>> lastWeekReport = null;
		List<Map<String, Object>> lastMonthReport = null;
		List<Map<String, Object>> currentDayReport = queryForList("market.querySmallChannelSortsCityReport", param);
		String currentTime = param.get("queryTime").toString();
		// 昨日
		String yesterdayTime = getNearQueryTime(currentTime);
		if (StringUtils.isNotEmpty(yesterdayTime)) {
			param.put("queryTime", yesterdayTime);
			yesterdayReport = queryForList("market.querySmallChannelSortsCityReport", param);
		}
		// 上周
		String lastWeekTime = getLastWeekQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastWeekTime)) {
			param.put("queryTime", lastWeekTime);
			lastWeekReport = queryForList("market.querySmallChannelSortsCityReport", param);
		}
		// 上月
		String lastMonthTime = getLastMonthQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastMonthTime)) {
			param.put("queryTime", lastMonthTime);
			lastMonthReport = queryForList("market.querySmallChannelSortsCityReport", param);
		}
		return appendData(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport);
	}

	private List<Map<String,Object>> querySmallChannelSortsReport(Map<String, Object> param, String channel, String parentChannel, String childChannel) {
//		String id = String.valueOf(param.get("id"));
//		String time = String.valueOf(param.get("queryTime"));
//		List<Map<String,Object>> channelList = queryForList(true,"market.get", param);

		List<Map<String, Object>> yesterdayReport = null;
		List<Map<String, Object>> lastWeekReport = null;
		List<Map<String, Object>> lastMonthReport = null;
		List<Map<String, Object>> currentDayReport = queryForList("market.querySmallChannelSortsReport", param);
		String currentTime = param.get("queryTime").toString();
		// 昨日
		String yesterdayTime = getNearQueryTime(currentTime);
		if (StringUtils.isNotEmpty(yesterdayTime)) {
			param.put("queryTime", yesterdayTime);
			yesterdayReport = queryForList("market.querySmallChannelSortsReport", param);
		}
		// 上周
		String lastWeekTime = getLastWeekQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastWeekTime)) {
			param.put("queryTime", lastWeekTime);
			lastWeekReport = queryForList("market.querySmallChannelSortsReport", param);
		}
		// 上月
		String lastMonthTime = getLastMonthQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastMonthTime)) {
			param.put("queryTime", lastMonthTime);
			lastMonthReport = queryForList("market.querySmallChannelSortsReport", param);
		}
		return appendData(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport);

//		List<Map<String,Object>> countList = queryForList(true,"market.querySmallChannelSortsReport", param);
//		return countList;
//		for (Map)
//		return null;
	}

	/**
	 * 判断是否存在子节点
	 *
	 * @param id
	 * @param childChannelMap
	 * @return
	 */
	@Override
	public boolean getChildSource(String id, Map<String, Object> childChannelMap)
	{
		Object count = childChannelMap.get(id);
		if (count != null && Integer.parseInt(count.toString()) > 0)
		{
			return true;
		}
		return false;
	}

	private List<Map<String, Object>> queryCityChannelReport(Map<String, Object> param, String childChannel)
	{
		if (param.get("id") == null || StringUtils.isEmpty(param.get("id").toString()))
		{
			return null;
		}
		String id = param.get("id").toString();
		if (id.indexOf("_") == -1)
		{
			return null;
		}
		String parentId = id.split("_")[0];
		String cityId = id.split("_")[1];
		param.put("parentId", parentId);
		param.put("cityId", cityId);
		param.put("childChannel", childChannel);
		List<Map<String, Object>> yesterdayReport = null;
		List<Map<String, Object>> lastWeekReport = null;
		List<Map<String, Object>> lastMonthReport = null;
		List<Map<String, Object>> currentDayReport = queryForList("market.queryCityChannelReport", param);
		String currentTime = param.get("queryTime").toString();
		String yesterdayTime = getNearQueryTime(currentTime);
		if (StringUtils.isNotEmpty(yesterdayTime))
		{
			param.put("queryTime", yesterdayTime);
			yesterdayReport = queryForList("market.queryCityChannelReport", param);
		}
		String lastWeekTime = getLastWeekQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastWeekTime))
		{
			param.put("queryTime", lastWeekTime);
			lastWeekReport = queryForList("market.queryCityChannelReport", param);
		}
		String lastMonthTime = getLastMonthQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastMonthTime))
		{
			param.put("queryTime", lastMonthTime);
			lastMonthReport = queryForList("market.queryCityChannelReport", param);
		}
		return appendData(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport);
	}

	private List<Map<String, Object>> queryRegionChannelReport(Map<String, Object> param, String channel,
			String parentChannel, String childChannel)
	{
		Map<String, Object> channelChildMap = getChanelChildMap();
		List<Map<String, Object>> yesterdayReport = null;
		List<Map<String, Object>> lastWeekReport = null;
		List<Map<String, Object>> lastMonthReport = null;

		if (StringUtils.isNotEmpty(parentChannel))
		{
			String id = param.get("id").toString();
			param.put("channel", parentChannel + "_" + id);
		}
		else if (StringUtils.isNotEmpty(channel))
		{
			String id = param.get("id").toString();
			param.put("channel", channel + "_" + id);
		}
		param.put("childChannel", childChannel);
		List<Map<String, Object>> currentDayReport = queryForList("market.queryRegionChannelReport", param);
		String currentTime = param.get("queryTime").toString();
		String yesterdayTime = getNearQueryTime(currentTime);
		if (StringUtils.isNotEmpty(yesterdayTime))
		{
			param.put("queryTime", yesterdayTime);
			yesterdayReport = queryForList("market.queryRegionChannelReport", param);
		}
		String lastWeekTime = getLastWeekQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastWeekTime))
		{
			param.put("queryTime", lastWeekTime);
			lastWeekReport = queryForList("market.queryRegionChannelReport", param);
		}
		String lastMonthTime = getLastMonthQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastMonthTime))
		{
			param.put("queryTime", lastMonthTime);
			lastMonthReport = queryForList("market.queryRegionChannelReport", param);
		}
		return appendSourceCityData(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport, currentTime,
				channelChildMap, channel, childChannel, null, null);
	}

	private List<Map<String, Object>> queryCityRegionReport(Map<String, Object> param, String childChannel)
	{
		List<Map<String, Object>> yesterdayReport = null;
		List<Map<String, Object>> lastWeekReport = null;
		List<Map<String, Object>> lastMonthReport = null;
		param.put("childChannel", childChannel);
		List<Map<String, Object>> currentDayReport = queryForList("market.queryCityRegionReport", param);
		String currentTime = param.get("queryTime").toString();
		String yesterdayTime = getNearQueryTime(currentTime);
		if (StringUtils.isNotEmpty(yesterdayTime))
		{
			param.put("queryTime", yesterdayTime);
			yesterdayReport = queryForList("market.queryCityRegionReport", param);
		}
		String lastWeekTime = getLastWeekQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastWeekTime))
		{
			param.put("queryTime", lastWeekTime);
			lastWeekReport = queryForList("market.queryCityRegionReport", param);
		}
		String lastMonthTime = getLastMonthQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastMonthTime))
		{
			param.put("queryTime", lastMonthTime);
			lastMonthReport = queryForList("market.queryCityRegionReport", param);
		}
		return appendData(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport);
	}

	private List<Map<String, Object>> queryWholeRegionReport(Map<String, Object> param) {
		List<Map<String, Object>> yesterdayReport = null;
		List<Map<String, Object>> lastWeekReport = null;
		List<Map<String, Object>> lastMonthReport = null;
		// 某分钟 各个渠道和城市的数量总和
		List<Map<String, Object>> currentDayReport = queryForList("market.queryWholeRegionReport", param);
		String currentTime = param.get("queryTime").toString();
		// 昨日某分钟总信息量
		String yesterdayTime = getNearQueryTime(currentTime);
		if (StringUtils.isNotEmpty(yesterdayTime)) {
			param.put("queryTime", yesterdayTime);
			yesterdayReport = queryForList("market.queryWholeRegionReport", param);
		}
		// 上周某分钟总信息量
		String lastWeekTime = getLastWeekQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastWeekTime)) {
			param.put("queryTime", lastWeekTime);
			lastWeekReport = queryForList("market.queryWholeRegionReport", param);
		}
		// 上月某分钟总信息量
		String lastMonthTime = getLastMonthQueryTime(currentTime);
		if (StringUtils.isNotEmpty(lastMonthTime)) {
			param.put("queryTime", lastMonthTime);
			lastMonthReport = queryForList("market.queryWholeRegionReport", param);
		}
		return appendData(currentDayReport, yesterdayReport, lastWeekReport, lastMonthReport);
	}

	@Override
	public List<Map<String, Object>> loadSourceData()
	{
		Map<String, Object> channelChildMap = getChanelChildMap();
		Map<String, Object> param = new HashMap<>();
		param.put("supId", 2);
		List<Map<String, Object>> list = queryForList("market.loadSource", param);
		List<Map<String, Object>> allChildChannelList = queryForList("market.loadAllChannel",
				new HashMap<>());
		if (list != null && list.size() > 0)
		{
			for (Map<String, Object> map : list)
			{
				String id = map.get("id").toString();
				Object obj = channelChildMap.get(id);
				if (obj != null && Integer.parseInt(obj.toString()) > 0)
				{
					// 有子节点
					param.put("supId", id);
					List<Map<String, Object>> childList = queryForList("market.loadSource", param);
					if (childList != null && childList.size() > 0)
					{
						for (Map<String, Object> tmpMap : childList)
						{
							tmpMap.put("state", "closed");
							String ChildId = tmpMap.get("id").toString();
							List<Map<String, Object>> childChannelList = getChildChannelList(ChildId,
									allChildChannelList);
							if (childChannelList != null && childChannelList.size() > 0)
							{
								tmpMap.put("children", childChannelList);
							}
						}
						map.put("state", "open");
					}
					else
					{
						map.put("state", "closed");
					}
					map.put("children", childList);
				}
				else
				{
					List<Map<String, Object>> childChannelList = getChildChannelList(id, allChildChannelList);
					if (childChannelList != null && childChannelList.size() > 0)
					{
						map.put("children", childChannelList);
					}
					map.put("state", "closed");
				}
			}
		}
		return list;
	}

	private List<Map<String, Object>> getChildChannelList(String id, List<Map<String, Object>> allChildChannelList)
	{
		if (allChildChannelList != null && allChildChannelList.size() > 0)
		{
			List<Map<String, Object>> resultList = new ArrayList<>();
			for (Map<String, Object> map : allChildChannelList)
			{
				String tmpId = map.get("parentId").toString();
				if (tmpId.equals(id))
				{
					resultList.add(map);
				}
			}

			allChildChannelList.remove(resultList);
			return resultList;
		}
		return null;
	}

}
