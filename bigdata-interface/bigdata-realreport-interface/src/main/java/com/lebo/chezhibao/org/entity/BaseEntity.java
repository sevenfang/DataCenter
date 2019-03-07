/*
 * Copyright (C), 2012-2020, Dragon.net
 * FileName: BaseEntity.java
 * Author:   wangkai
 * Date:     2012-05-01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.lebo.chezhibao.org.entity;

import java.io.Serializable;


/**
 * 〈对象实体基类〉<br>
 * 〈功能详细描述〉
 * 
 * @author 王凯
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BaseEntity implements Serializable 
{

	// Logger logger = LoggerFactory.getLogger(BaseEntity.class);
	// logger 影响dubbo序列化

	/**
	 */
	private static final long	serialVersionUID	= 8151284552699070448L;

	/**
	 * 主键
	 */
	public Integer				id					= null;

	/**
	 * 扩展属性
	 */
	public String				attribute;


	public BaseEntity()
	{

	}

	public void setId(Integer id)
	{
		this.id = id;
	}

}
