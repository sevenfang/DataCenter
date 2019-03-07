package com.chezhibao.bigdata.msg.constants;

/**
 * 
 * App推送常量定义
 *
 * @author zhoutao
 * @see AUCS-12 竞拍App和渠道App的消息推送改造
 * @since AuctionServiceV1.00.03
 */
public enum AppPushConstants
{
	/**
	 * 友盟移动消息推送类型： 
	 *    竞拍-Android: 1
	 *    竞拍-IOS： 2
	 */
	AppPushType_AUC_Android("AppPushType",1,"1"),
	AppPushType_AUC_IOS("AppPushType",2,"2"),
	
	
	/**
	 * 友盟移动消息推送类型： 
	 *     渠道-Android： 3
	 *     渠道-IOS：     4
	 */
	AppPushType_CHA_Android("AppPushType",3,"3"),
	AppPushType_CHA_IOS("AppPushType",4,"4"),
	
	
	/**
	 * t_buyer_client表记录商户终端类型定义： 
	 *     Android： 3
	 *     IOS：     2
	 */	
	BuyerClient_IOS("BuyerClient",2,"2"),
	BuyerClient_Android("BuyerClient",3,"3"),

	
	/**
	 * t_buyer_channel_appdevice表记录渠道终端类型定义： 
	 *     Android：230
	 *     IOS：     231
	 */	
	ChannelClient_IOS("ChannelClient",231,"231"),
	ChannelClient_Android("ChannelClient",230,"230"),

	/**
	 * t_buyer_channel_appdevice表记录渠道终端类型定义：
	 *     Android：13
	 *     IOS：     12
	 *     [渠道4.0项目更改]
	 */
	ChannelClient_IOSNew("ChannelClient",2,"2"),
	ChannelClient_AndroidNew("ChannelClient",3,"3"),
	
	
	/**
	 * 分组类型
	 */
	Group_Auction("Group",-1,"auction"),
	Group_Channel("Group",-2,"channel"),
	
	
	;
	
	private AppPushConstants (String group, int id , String value)
	{
		this.group = group ;
		this.id  = id ;
		this.value = value ;
	}
	
	private String group;
	private int id;
	private String value;
	
	
	public String getGroup()
	{
		return group;
	}
	public void setGroup(String group)
	{
		this.group = group;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	
	

}
