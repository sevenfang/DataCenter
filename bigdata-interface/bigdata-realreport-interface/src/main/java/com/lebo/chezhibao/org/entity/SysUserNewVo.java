/*
 * Copyright (C), 2012-2020, Dragon.net
 * FileName: User.java
 * Author:   WangJianMin
 * Date:     2012-05-01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.lebo.chezhibao.org.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * 
 * 系统用户信息<br>
 * 〈功能详细描述〉
 * 
 * @author WangJianMin
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SysUserNewVo implements Serializable
{
	/**
	 */
	private static final long		serialVersionUID	= 1L;
	/**
	 * 用户编号ID
	 */
	private Integer					id;
	/**
	 * 工号
	 */
	private String					staffCode;
	/**
	 * 姓名
	 */
	private String					name;
	/**
	 * 性别
	 */
	private Integer					sex					= 1;
	/**
	 * 在职状态
	 */
	private Integer					flag				= 1;
	/**
	 * 入职日期
	 */
	private Date					accessDate;
	/**
	 * 转正状态
	 */
	private String					converseState;
	/**
	 * 转正日期
	 */
	private Date					converseDate;
	/**
	 * 身份证号码
	 */
	private String					identityNumber;
	/**
	 * 生日
	 */
	private Date					birthday;
	/**
	 * 邮箱
	 */
	private String					email;
	/**
	 * 民族
	 */
	private String					nation;
	/**
	 * 通讯地址
	 */
	private String					address;
	/**
	 * 婚姻状态
	 */
	private String					marriage;
	/**
	 * 生育状态
	 */
	private String					bear;
	/**
	 * 最高学历
	 */
	private String					education;
	/**
	 * 毕业院校
	 */
	private String					graduateCollege;
	/**
	 * 专业
	 */
	private String					major;
	/**
	 * 毕业时间
	 */
	private Date					graduateTime;
	/**
	 * 户口性质
	 */
	private String					anmelden;
	/**
	 * 原就业单位
	 */
	private String					originalCompany;
	/**
	 * 紧急联络人姓名
	 */
	private String					urgpersoName;
	/**
	 * 紧急联络人关系
	 */
	private String					urgpersonRelation;
	/**
	 * 紧急联络人电话
	 */
	private String					urgpersonMobile;
	/**
	 * 参加工作时间
	 */
	private Date					beginJobDate;
	/**
	 * 入职前累计工作月度
	 */
	private Integer					totalWorkMonths;
	/**
	 * 所属组织
	 */
	private List<SysOrgSummaryVo>	orgs;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getStaffCode()
	{
		return staffCode;
	}

	public void setStaffCode(String staffCode)
	{
		this.staffCode = staffCode;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getSex()
	{
		return sex;
	}

	public void setSex(Integer sex)
	{
		this.sex = sex;
	}

	public Integer getFlag()
	{
		return flag;
	}

	public void setFlag(Integer flag)
	{
		this.flag = flag;
	}

	public Date getAccessDate()
	{
		return accessDate;
	}

	public void setAccessDate(Date accessDate)
	{
		this.accessDate = accessDate;
	}

	public String getConverseState()
	{
		return converseState;
	}

	public void setConverseState(String converseState)
	{
		this.converseState = converseState;
	}

	public Date getConverseDate()
	{
		return converseDate;
	}

	public void setConverseDate(Date converseDate)
	{
		this.converseDate = converseDate;
	}

	public String getIdentityNumber()
	{
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber)
	{
		this.identityNumber = identityNumber;
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getNation()
	{
		return nation;
	}

	public void setNation(String nation)
	{
		this.nation = nation;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getMarriage()
	{
		return marriage;
	}

	public void setMarriage(String marriage)
	{
		this.marriage = marriage;
	}

	public String getBear()
	{
		return bear;
	}

	public void setBear(String bear)
	{
		this.bear = bear;
	}

	public String getEducation()
	{
		return education;
	}

	public void setEducation(String education)
	{
		this.education = education;
	}

	public String getGraduateCollege()
	{
		return graduateCollege;
	}

	public void setGraduateCollege(String graduateCollege)
	{
		this.graduateCollege = graduateCollege;
	}

	public String getMajor()
	{
		return major;
	}

	public void setMajor(String major)
	{
		this.major = major;
	}

	public Date getGraduateTime()
	{
		return graduateTime;
	}

	public void setGraduateTime(Date graduateTime)
	{
		this.graduateTime = graduateTime;
	}

	public String getAnmelden()
	{
		return anmelden;
	}

	public void setAnmelden(String anmelden)
	{
		this.anmelden = anmelden;
	}

	public String getOriginalCompany()
	{
		return originalCompany;
	}

	public void setOriginalCompany(String originalCompany)
	{
		this.originalCompany = originalCompany;
	}

	public String getUrgpersoName()
	{
		return urgpersoName;
	}

	public void setUrgpersoName(String urgpersoName)
	{
		this.urgpersoName = urgpersoName;
	}

	public String getUrgpersonRelation()
	{
		return urgpersonRelation;
	}

	public void setUrgpersonRelation(String urgpersonRelation)
	{
		this.urgpersonRelation = urgpersonRelation;
	}

	public String getUrgpersonMobile()
	{
		return urgpersonMobile;
	}

	public void setUrgpersonMobile(String urgpersonMobile)
	{
		this.urgpersonMobile = urgpersonMobile;
	}

	public Date getBeginJobDate()
	{
		return beginJobDate;
	}

	public void setBeginJobDate(Date beginJobDate)
	{
		this.beginJobDate = beginJobDate;
	}

	public Integer getTotalWorkMonths()
	{
		return totalWorkMonths;
	}

	public void setTotalWorkMonths(Integer totalWorkMonths)
	{
		this.totalWorkMonths = totalWorkMonths;
	}

	public List<SysOrgSummaryVo> getOrgs()
	{
		return orgs;
	}

	public void setOrgs(List<SysOrgSummaryVo> orgs)
	{
		this.orgs = orgs;
	}

	@Override
	public String toString()
	{
		return "SysUserNewBaseInfo [id=" + id + ", staffCode=" + staffCode + ", name=" + name + ", sex=" + sex
				+ ", flag=" + flag + ", accessDate=" + accessDate + ", converseState=" + converseState
				+ ", converseDate=" + converseDate + ", identityNumber=" + identityNumber + ", birthday=" + birthday
				+ ", email=" + email + ", nation=" + nation + ", address=" + address + ", marriage=" + marriage
				+ ", bear=" + bear + ", education=" + education + ", graduateCollege=" + graduateCollege + ", major="
				+ major + ", graduateTime=" + graduateTime + ", anmelden=" + anmelden + ", originalCompany="
				+ originalCompany + ", urgpersoName=" + urgpersoName + ", urgpersonRelation=" + urgpersonRelation
				+ ", urgpersonMobile=" + urgpersonMobile + ", beginJobDate=" + beginJobDate + ", totalWorkMonths="
				+ totalWorkMonths + ", orgs=" + orgs + "]";
	}

}
