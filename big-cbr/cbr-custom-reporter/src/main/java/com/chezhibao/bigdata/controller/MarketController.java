/*
 * Copyright (C), 2014-2016, 江苏乐博国际投资发展有限公司
 * FileName: MarketController.java
 * Author:   wjh
 * Date:     2016年10月31日 上午10:43:43
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <wjh>      <2016年10月31日 上午10:43:43>      <V1.0.0>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.service.MarketService;
import com.chezhibao.bigdata.vo.MarketVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author wjh 2016年10月31日 上午10:43:43
 */
@RestController
@RequestMapping("/cbr/market")
public class MarketController {
	@Autowired
	private MarketService marketService;

	@RequestMapping("loadData")
	public Object loadData(String id, int type, String time, String channel, String parentChannel, String childChannel)
	{
		Map<String, Object> result = marketService.loadData(id, type, time, channel, parentChannel, childChannel);
		if (0 != type)
		{
			return result.get("rows");
		}
		return result;
	}

	@RequestMapping("loadRegionData")
	public Object loadRegionData(MarketVo marketVo)
	{
		Map<String, Object> result = marketService.loadRegionData(marketVo.getId(), marketVo.getType(),
				marketVo.getTime(), marketVo.getChannel(), marketVo.getParentChannel(), marketVo.getChildChannel());
		if (0 != marketVo.getType())
		{
			return result.get("rows");
		}
		return result;
	}

	/*
	* 2017.12.07 实时报表 - 市场报表(渠道)loadChannelData
	* dongdemin
	* */
	@RequestMapping("loadSourceData")
	public Object loadSourceData(MarketVo marketVo) {
		Map<String, Object> result = marketService.loadSourceData(marketVo.getId(), marketVo.getType(),
				marketVo.getTime(), marketVo.getChannel(), marketVo.getParentChannel(), marketVo.getChildChannel());
		if (0 != marketVo.getType()) {
			return result.get("rows");
		}
		return result;
	}

	@RequestMapping("loadChannelData")
	public Object loadChannelData() {
		List<Map<String, Object>> result = marketService.loadSourceData();
		return result;
	}
}
