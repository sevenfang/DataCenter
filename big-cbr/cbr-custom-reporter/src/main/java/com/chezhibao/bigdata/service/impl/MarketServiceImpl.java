package com.chezhibao.bigdata.service.impl;

import com.chezhibao.bigdata.common.utils.SortUtils;
import com.chezhibao.bigdata.dao.MarketDao;
import com.chezhibao.bigdata.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("marketService")
public class MarketServiceImpl implements MarketService
{
	@Autowired
	private MarketDao marketDao;

	@Override
	public Map<String, Object> loadData(String id, int type, String time, String channel, String parentChannel, String childChannel)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		time = ("".equals(time) || "null".equals(time)) ? null : time;
		if (0 != type && null == time)
		{
			return result;
		}
		// 非空处理处理time
		// time = "2016-10-26 21:00";
		if (time == null)
		{
			time = marketDao.getMaxLoadDateTime();
		}
		List<Map<String, Object>> list = marketDao.loadData(id, type, time, channel, parentChannel, childChannel);
		for (Map<String, Object> map : list)
		{
			// treegread组件需要参数,root层不需要
			if (0 != type)
			{
				map.put("_parentId", id);
			}
			// treegread组件需要参数 最后一层不需要折叠
			if (3 != type)
			{
				map.put("state", "closed");
			}
			map.put("type", type + 1);
			map.put("time", time);

		}
		// 添加排序
		SortUtils.marketSort(list);
		result.put("rows", list);
		result.put("total", result.size());
		result.put("loadDateTime", time);
		return result;
	}

	@Override
	public Map<String, Object> loadRegionData(String id, int type, String time, String channel, String parentChannel, String childChannel)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		time = ("".equals(time) || "null".equals(time)) ? null : time;
		if (0 != type && null == time)
		{
			return result;
		}
		// 非空处理处理time
		// time = "2016-10-26 21:00";
		if (time == null)
		{
			time = marketDao.getMaxLoadDateTime();
		}
		List<Map<String, Object>> list = marketDao.loadRegionData(id, type, time, channel, parentChannel, childChannel);
		for (Map<String, Object> map : list)
		{
			// treegread组件需要参数,root层不需要
			if (0 != type)
			{
				map.put("_parentId", id);
			}
			// treegread组件需要参数 最后一层不需要折叠
			if (3 != type)
			{
				map.put("state", "closed");
			}
			map.put("type", type + 1);
			map.put("time", time);

		}
		// 添加排序
		SortUtils.marketSort(list);
		result.put("rows", list);
		result.put("total", result.size());
		result.put("loadDateTime", time);
		return result;
	}

	@Override
	public Map<String, Object> loadSourceData(String id, int type, String time, String channel, String parentChannel, String childChannel)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		time = ("".equals(time) || "null".equals(time)) ? null : time;
		if (0 != type && null == time)
		{
			return result;
		}
		// 非空处理处理time
		// time = "2016-10-26 21:00";
		if (time == null)
		{
			time = marketDao.getMaxLoadDateTime();
		}
		List<Map<String, Object>> list = marketDao.loadSourceData(id, type, time, channel, parentChannel, childChannel);
		for (Map<String, Object> map : list)
		{
			// treegread组件需要参数,root层不需要
			if (0 != type)
			{
				map.put("_parentId", id);
			}
			// treegread组件需要参数 最后一层不需要折叠
			if (3 != type)
			{
				map.put("state", "closed");
			}
			map.put("type", type + 1);
			map.put("time", time);
		}
		// 添加排序
		SortUtils.marketSort(list);
		result.put("rows", list);
		result.put("total", result.size());
		result.put("loadDateTime", time);
		return result;
	}

	@Override
	public List<Map<String, Object>> loadSourceData()
	{
		return marketDao.loadSourceData();
	}

}
