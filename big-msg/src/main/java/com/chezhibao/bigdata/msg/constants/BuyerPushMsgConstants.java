package com.chezhibao.bigdata.msg.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 
 *  商户中心推送消息常量定义<br> 
 * 〈功能详细描述〉
 *
 * @author zhoutao
 */
public enum BuyerPushMsgConstants
{
	
	/**
	 * 商户中心系统消息服务appid定义
	 */
	PushMsg_AppId_BusinessMs("商户中心系统消息服务appid定义","PushMsg_AppId_BusinessMs","14"),
	
	/**
	 * 商户中心系统访问消息服务appKey
	 */
	PushMsg_APPKEY_BusinessMs("商户中心系统访问消息服务appKey","PushMsg_APPKEY_BusinessMs","b126bd61504d49b4bf9dd1be924a0c9d"),

	/**
	 * 商户中心系统访问消息服务appSecret
	 */
	PushMsg_APPSECRET_BusinessMs("商户中心系统访问消息服务appSecret","PushMsg_APPSECRET_BusinessMs","89ec54057598e7fe4db2279a54caa5c5"),
	
	/**
	 * 消息服务HTTP服务URL
	 */
	PushMsg_HttpServiceUrl("消息服务HTTP服务URL","PushMsg_HttpServiceUrl","http://message.public.mychebao.com"),
	
	
	
	
	/**
	 * 商户版IOS的appid定义
	 */
	PushMsg_AppId_BuyerAuction_IOS("商户版IOS的appid定义","PushMsg_AppId_BuyerAuction_IOS","5"),
	
	
	/**
	 * 商户版Android的appid定义
	 */
	PushMsg_AppId_BuyerAuction_Android("商户版Android的appid定义","PushMsg_AppId_BuyerAuction_Android","6"),
	
	/**
	 * 消息推送-发送短信开关  On:推送  Off:关闭
	 */
	PushMsg_SMS_Switch("消息推送-发送短信开关","PushMsg_SMS_Switch","On"),
	
	/**
	 * 消息推送-发送App消息开关  On:推送  Off:关闭
	 */
	PushMsg_App_Switch("消息推送-发送App消息开关","PushMsg_App_Switch","On"),
	
	;
	/**
	 * 配置Id
	 */
	private String id ;
	
	/**
	 * 配置Id对应value默认值
	 */
	private String defaultValue ;

	/**
	 * 配置量描述
	 */
	private String desc  ;
	
	private Logger logger	= LoggerFactory.getLogger(BuyerPushMsgConstants.class);
	
	

	private BuyerPushMsgConstants(String desc , String id ,String defaultValue)
	{
		this.id = id;
		this.defaultValue = defaultValue;
		this.desc = desc;
	}
	
	
	
	public String getDefaultValue()
	{
		return defaultValue;
	}



	public String getConfigValue()
	{
        Assert.hasLength(this.id);
		
		String configvalue = "";
//		String configvalue = ParameterUtil.getParameter(id);

		logger.info("BuyerPushMsgConstants.getConfigValue[id:{},configvalue:{}]",this.id,configvalue);
		
		if (StringUtils.hasLength(configvalue))
		{
			return configvalue;
		}
		else
		{
			logger.info("BuyerPushMsgConstants.getConfigValue[id:{},defaultValue:{}]",this.id,this.defaultValue);
			return this.defaultValue;
		}
		
	}



}
