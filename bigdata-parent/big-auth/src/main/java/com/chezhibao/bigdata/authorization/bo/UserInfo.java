package com.chezhibao.bigdata.authorization.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/4.
 */
public class UserInfo implements Serializable {
    private Integer		id;
    private Integer		orgID		= -1;

    private String		orgIDPath;

    private String		name;

    private String		namePY;

    /**
     * 1男，2女，默认1
     */
    private Integer		sex			= 1;

    private String		mobile;

    private String		email;

    private String		userName	= "";

    private String		password	= "";

    private Date lastLoginTime;
    private Date	latestLoginTime;
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

    private ClientInfoBO clientInfo;		// 登录信息

    // private HashMap<String, Object> rolesMap; // 用户拥有的角色

    // private List<SysRole> rolesList; // 用户拥有的角色

    private String		sso			= "";	// 用户sso登录标识

    /**
     * 登录冻结状态
     */
    private int abnormalStatus ;
    /**
    /**
     * 工号
     */
    private String staffCode;


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

    public void setLatestLoginTime(Date latestLoginTime)
    {
        this.latestLoginTime = latestLoginTime;
    }

    public Date getLatestLoginTime()
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
    public Date getLastLoginTime()
    {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     *            the lastLoginTime to set
     */
    public void setLastLoginTime(Date lastLoginTime)
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
    public ClientInfoBO getClientInfo()
    {
        return clientInfo;
    }

    /**
     * @param clientInfo
     *            the clientInfo to set
     */
    public void setClientInfo(ClientInfoBO clientInfo)
    {
        this.clientInfo = clientInfo;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
