/*
 * Copyright (C), 2012-2020, Dragon.net
 * FileName: SysRole.java
 * Author:   wangkai
 * Date:     2012-05-01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.lebo.chezhibao.org.entity;


/**
 * 角色组
 */
public class SysRole extends BaseEntity
{

	/**
	 */
	private static final long	serialVersionUID	= 6317964299729734159L;

	/**
	 * 
	 */
	private String				code;

	/**
	 * 
	 */
	private String				name;

	/**
	 * 
	 */
	private Integer				orderNum;

	/**
	 * 0停用，1启用，默认1
	 */
	private Integer				flag;

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append("ID=").append(id).append("\t");
		str.append("code=").append(code).append("\t");
		str.append("name=").append(name).append("\t");
		str.append("orderNum=").append(orderNum).append("\t");
		str.append("flag=").append(flag).append("\t");
		return str.toString();
	}

	public void setCode(String code)
	{
		this.code = code;
	}


	public String getCode()
	{
		return this.code;
	}

	public void setName(String name)
	{
		this.name = name;
	}


	public String getName()
	{
		return this.name;
	}

	public void setOrderNum(Integer orderNum)
	{
		this.orderNum = orderNum;
	}


	public Integer getOrderNum()
	{
		return this.orderNum;
	}

	public void setFlag(Integer flag)
	{
		this.flag = flag;
	}

	public Integer getFlag()
	{
		return this.flag;
	}

}
