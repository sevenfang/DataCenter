package com.lebo.chezhibao.org.entity;

import java.io.Serializable;

/**
 * 
 * 组织摘要信息<br>
 * 〈功能详细描述〉
 * 
 * @author WangJianMin
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SysOrgSummaryVo implements Serializable
{
	/**
	 */
	private static final long	serialVersionUID	= 1L;
	/**
	 * 组织机构id
	 */
	private Integer				id;
	/**
	 * 组织机构id路径
	 */
	private String				idPath;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getIdPath()
	{
		return idPath;
	}

	public void setIdPath(String idPath)
	{
		this.idPath = idPath;
	}

	@Override
	public String toString()
	{
		return "SysOrgSummaryVo [id=" + id + ", idPath=" + idPath + "]";
	}

}
