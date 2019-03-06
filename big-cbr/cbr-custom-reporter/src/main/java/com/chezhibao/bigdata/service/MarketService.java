package com.chezhibao.bigdata.service;

import java.util.List;
import java.util.Map;

public interface MarketService 
{

	/**
	 * 功能描述: 加载数据<br>
	 * 〈功能详细描述〉
	 *
	 * @param id
	 * @param type
	 * @param channel
	 * @param parentChannel
	 * @param childChannel
	 * @return
	 * @author wjh 2016年10月28日 下午7:37:29
	 */
	Map<String, Object> loadData(String id, int type, String time, String channel, String parentChannel, String childChannel);

	Map<String,Object> loadRegionData(String id, int type, String time, String channel, String parentChannel, String childChannel);

	Map<String,Object> loadSourceData(String id, int type, String time, String channel, String parentChannel, String childChannel);

	List<Map<String,Object>> loadSourceData();

}
