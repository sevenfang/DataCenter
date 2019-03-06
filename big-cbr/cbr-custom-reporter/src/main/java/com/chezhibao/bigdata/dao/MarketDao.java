package com.chezhibao.bigdata.dao;

import java.util.List;
import java.util.Map;

public interface MarketDao
{

	List<Map<String, Object>> loadData(String id, int type, String time, String channel, String parentChannel, String childChannel);
	
	String getMaxLoadDateTime();

	List<Map<String,Object>> loadRegionData(String id, int type, String time, String channel, String parentChannel, String childChannel);

	List<Map<String,Object>> loadSourceData(String id, int type, String time, String channel, String parentChannel, String childChannel);

	boolean getChildSource(String id, Map<String, Object> childChannelMap);

    List<Map<String,Object>> loadSourceData();
}
