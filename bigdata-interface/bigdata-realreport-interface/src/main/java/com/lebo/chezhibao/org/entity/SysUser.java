/*
 * Copyright (C), 2012-2020, Dragon.net
 * FileName: SysUser.java
 * Author:   wangkai
 * Date:     2012-05-01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.lebo.chezhibao.org.entity;

import java.sql.Timestamp;

/**
 * 系统用户
 */

public class SysUser extends BaseEntity
{
	/**
	 */
	private static final long	serialVersionUID	= -9011872978069493249L;

	public SysUser()
	{

	}

	public SysUser(int id)
	{
		this.id = id;
	}

	/**
	 * 
	 */
	private Integer		orgID		= -1;

	/**
	 * 存储组织机构表的idpath
	 */
	private String		orgIDPath;

	/**
	 * 
	 */
	private String		name;

	/**
	 * 
	 */
	private String		namePY;

	/**
	 * 1男，2女，默认1
	 */
	private Integer		sex			= 1;

	/**
	 * 
	 */
	private String		mobile;

	/**
	 * 
	 */
	private String		email;

	/**
	 * 
	 */
	private String		userName	= "";

	/**
	 * MD5加密32位
	 */
	private String		password	= "";

	private Timestamp	lastLoginTime;
	private Timestamp	latestLoginTime;
	private String		lastLoginIp;
	private String		latestLoginIp;

	/**
	 * 0停用，1启用，默认1
	 */
	private Integer		flag		= 1;

	// 身份证号码
	private String		idCardNo;

	// 数据状态
	private String		status;

	/*----------------------------------以下是运行时字段----------------------------------*/
	private String		orgName		= "";	// 用户所属组织机构名

	private ClientInfo	clientInfo;		// 登录信息

	// private HashMap<String, Object> rolesMap; // 用户拥有的角色

	// private List<SysRole> rolesList; // 用户拥有的角色

	private String		sso			= "";	// 用户sso登录标识

	/**
	 * 登录冻结状态
	 */
	private int abnormalStatus ;
	/**
	 * 档案编号
	 */
	// private String archiveId;
	/**
	 * 工号
	 */
	 private String staffCode;

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append("id=").append(id).append("\t");
		str.append("orgID=").append(orgID).append("\t");
		str.append("orgName=").append(orgName).append("\t");
		str.append("orgIDPath=").append(orgIDPath).append("\t");
		str.append("name=").append(name).append("\t");
		str.append("sex=").append(sex).append("\t");
		str.append("mobile=").append(mobile).append("\t");
		str.append("email=").append(email).append("\t");
		str.append("userName=").append(userName).append("\t");
		str.append("password=").append(password).append("\t");
		str.append("latestLoginTime=").append(latestLoginTime).append("\t");
		str.append("latestLoginIp=").append(latestLoginIp).append("\t");
		str.append("flag=").append(flag).append("\t");
		str.append("clientInfo=").append(clientInfo).append("\t");
		// str.append("roles=").append(rolesMap).append("\t");
		str.append("attribute=").append(attribute).append("\t");
		str.append("abnormalStatus=").append(abnormalStatus).append("\t");
		// str.append("archiveId=").append(archiveId).append("\t");
		 str.append("staffCode=").append(staffCode).append("\t");

		return str.toString();
	}

	public void setOrgID(Integer orgID)
	{
		this.orgID = orgID;
	}

	public Integer getOrgID()
	{
		return this.orgID;
	}

	public void setOrgIDPath(String orgIDPath)
	{
		this.orgIDPath = orgIDPath;
	}

	public String getOrgIDPath()
	{
		return this.orgIDPath;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public void setNamePY(String namePY)
	{
		this.namePY = namePY;
	}

	public String getNamePY()
	{
		return this.namePY;
	}

	public void setSex(Integer sex)
	{
		this.sex = sex;
	}

	public Integer getSex()
	{
		return this.sex;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getMobile()
	{
		return this.mobile;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getEmail()
	{
		return this.email;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserName()
	{
		return this.userName;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return this.password;
	}

	public void setLatestLoginTime(Timestamp latestLoginTime)
	{
		this.latestLoginTime = latestLoginTime;
	}

	public Timestamp getLatestLoginTime()
	{
		return this.latestLoginTime;
	}

	public void setLatestLoginIp(String latestLoginIp)
	{
		this.latestLoginIp = latestLoginIp;
	}

	public String getLatestLoginIp()
	{
		return this.latestLoginIp;
	}

	public void setFlag(Integer flag)
	{
		this.flag = flag;
	}

	public Integer getFlag()
	{
		return this.flag;
	}

	/**
	 * @return the idCardNo
	 */
	public String getIdCardNo()
	{
		return idCardNo;
	}

	/**
	 * @param idCardNo
	 *            the idCardNo to set
	 */
	public void setIdCardNo(String idCardNo)
	{
		this.idCardNo = idCardNo;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Timestamp getLastLoginTime()
	{
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime
	 *            the lastLoginTime to set
	 */
	public void setLastLoginTime(Timestamp lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp()
	{
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp
	 *            the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp)
	{
		this.lastLoginIp = lastLoginIp;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	/**
	 * @return the clientInfo
	 */
	public ClientInfo getClientInfo()
	{
		return clientInfo;
	}

	/**
	 * @param clientInfo
	 *            the clientInfo to set
	 */
	public void setClientInfo(ClientInfo clientInfo)
	{
		this.clientInfo = clientInfo;
	}


	/**
	 * @return the attribute
	 */
	public String getAttribute()
	{
		return attribute;
	}

	/**
	 * @return the sso
	 */
	public String getSso()
	{
		return sso;
	}

	/**
	 * @param sso
	 *            the sso to set
	 */
	public void setSso(String sso)
	{
		this.sso = sso;
	}

	public int getAbnormalStatus() 
	{
		return abnormalStatus;
	}

	public void setAbnormalStatus(int abnormalStatus)
	{
		this.abnormalStatus = abnormalStatus;
	}


	 public String getStaffCode()
	 {
	 return staffCode;
	 }

	 public void setStaffCode(String staffCode)
	 {
	 this.staffCode = staffCode;
	 }
}
