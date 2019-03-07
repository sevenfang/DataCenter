package com.chezhibao.bigdata.msg.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 
 * App推送参数
 *
 * @author zhoutao
 * @see AUCS-12 竞拍App和渠道App的消息推送改造
 * @since AuctionServiceV1.00.03
 */
public class AppPushParam implements Serializable
{

	/**
	 */
	private static final long	serialVersionUID	= 2933974097100523834L;
	
	
	/**
	 * 
	 * 友盟移动消息推送<br>
	 * 
	 * IOS推送，只有text显示，ticker、title均无效。
	 * 
	 * @param type
	 *            <必填> 1=竞拍android，2=竞拍IOS，3=渠道android，4=渠道IOS
	 * @param ticker
	 *            <必填> 通知栏提示文字
	 * @param title
	 *            <必填> 通知标题
	 * @param text
	 *            <必填> 通知文字描述
	 * @param deviceTokens
	 *            <必填> 设备Id
	 * @param pushDate
	 *            定时发送，传null为立即发送
	 * @param extraParam
	 * 			自定义参数，可以传null
	 * @author zhangjianwei Date: 2016年10月24日 下午8:21:15
	 */
	
	private String type;
	private String ticker;
	private String title;
	private String text;
	private String deviceTokens;
	private Date pushDate ;
	private Map<String, Object>	extraParam ;
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getTicker()
	{
		return ticker;
	}
	public void setTicker(String ticker)
	{
		this.ticker = ticker;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public String getDeviceTokens()
	{
		return deviceTokens;
	}
	public void setDeviceTokens(String deviceTokens)
	{
		this.deviceTokens = deviceTokens;
	}
	public Date getPushDate()
	{
		return pushDate;
	}
	public void setPushDate(Date pushDate)
	{
		this.pushDate = pushDate;
	}
	public Map<String, Object> getExtraParam()
	{
		return extraParam;
	}
	public void setExtraParam(Map<String, Object> extraParam)
	{
		this.extraParam = extraParam;
	}
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("AppPushParam [type=");
		builder.append(type);
		builder.append(", ticker=");
		builder.append(ticker);
		builder.append(", title=");
		builder.append(title);
		builder.append(", text=");
		builder.append(text);
		builder.append(", deviceTokens=");
		builder.append(deviceTokens);
		builder.append(", pushDate=");
		builder.append(pushDate);
		builder.append(", extraParam=");
		builder.append(extraParam);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}
