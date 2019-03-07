/*
 * Copyright (C), 2012-2020, Dragon.net
 * FileName: SysOrg.java
 * Author:   wangkai
 * Date:     2012-05-01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.lebo.chezhibao.org.entity;

import java.sql.Date;


/**
 * 组织机构表
 */
public class SysOrg extends BaseEntity
{
	/**
	 */
	private static final long	serialVersionUID	= -2907785988693469409L;

	public SysOrg()
	{
	}

	public SysOrg(int id)
	{
		this.id = id;
	}

	/**
	 * 
	 */
	private Integer	pid			= 0;

	/**
	 * 
	 */
	private String	code;

	/**
	 * 
	 */
	private String	name;

	/**
	 * 
	 */
	private String	idPath;

	/**
	 * 
	 */
	private String	codePath;

	/**
	 * 
	 */
	private String	namePath;

	/**
	 * 
	 */
	private Integer	depth		= 1;

	/**
	 * 
	 */
	private String	bak;

	/**
	 * 
	 */
	private Integer	orderNum	= 1;

	/**
	 * 0停用，1启用，默认1
	 */
	private Integer	flag;

	/**
	 * 所属地区
	 */
	private Integer	ou;

	/**
	 * 创建时间
	 */
	private Date	cuTime;

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append("id=").append(id).append("\t");
		str.append("pid=").append(pid).append("\t");
		str.append("code=").append(code).append("\t");
		str.append("name=").append(name).append("\t");
		str.append("idPath=").append(idPath).append("\t");
		str.append("codePath=").append(codePath).append("\t");
		str.append("namePath=").append(namePath).append("\t");
		str.append("depth=").append(depth).append("\t");
		str.append("bak=").append(bak).append("\t");
		str.append("orderNum=").append(orderNum).append("\t");
		str.append("flag=").append(flag).append("\t");
		str.append("ou=").append(ou).append("\t");
		return str.toString();
	}

	public void setPid(Integer pid)
	{
		this.pid = pid;
	}


	public Integer getPid()
	{
		return this.pid;
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

	public void setIdPath(String idPath)
	{
		this.idPath = idPath;
	}


	public String getIdPath()
	{
		return this.idPath;
	}

	public void setCodePath(String codePath)
	{
		this.codePath = codePath;
	}


	public String getCodePath()
	{
		return this.codePath;
	}

	public void setNamePath(String namePath)
	{
		this.namePath = namePath;
	}


	public String getNamePath()
	{
		return this.namePath;
	}

	public void setDepth(Integer depth)
	{
		this.depth = depth;
	}


	public Integer getDepth()
	{
		return this.depth;
	}

	public void setBak(String bak)
	{
		this.bak = bak;
	}


	public String getBak()
	{
		return this.bak;
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


	public Integer getOu()
	{
		return ou;
	}

	public void setOu(Integer ou)
	{
		this.ou = ou;
	}

	/**
	 * @return the attribute
	 */

	public String getAttribute()
	{
		return attribute;
	}


	public Date getCuTime()
	{
		return cuTime;
	}

	public void setCuTime(Date cuTime)
	{
		this.cuTime = cuTime;
	}

}
