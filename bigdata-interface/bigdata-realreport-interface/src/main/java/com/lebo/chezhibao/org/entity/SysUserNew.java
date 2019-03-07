package com.lebo.chezhibao.org.entity;

import java.sql.Date;
import java.sql.Timestamp;


/**
 * 系统用户
 */
public class SysUserNew extends BaseEntity
{
	/**
	 */
	private static final long	serialVersionUID	= -6750285662349261924L;

	public SysUserNew()
	{

	}

	public SysUserNew(int id)
	{
		this.id = id;
	}

	/**
	 * 
	 */
	private Integer		orgID;

	/**
	 * 存储组织机构表的idpath
	 */
	private String		orgIDPath;

	/**
	 * 姓名
	 */
	private String		name;

	/**
	 * 性别 1女(默认);2男
	 */
	private String		sex;

	/**
	 * 手机号码
	 */
	private String		mobile;

	/**
	 * 电子邮件
	 */
	private String		email;

	/**
	 * 用户名(与t_sys_useraccount表同步，待删除)
	 */
	private String		userName;

	/**
	 * 密码，MD5加密32位(与t_sys_useraccount表同步，待删除)
	 */
	private String		password;

	/**
	 * 最近一次登录时间
	 */
	private Timestamp	latestLoginTime;

	/**
	 * 最后登录时间
	 */
	private Timestamp	lastLoginTime;

	/**
	 * 最近一次登录IP，考虑到ipv6，长度适当加大
	 */
	private String		latestLoginIp;

	/**
	 * 最后一次登录IP，考虑到ipv6，长度适当加大
	 */
	private String		lastLoginIp;

	/**
	 * 0停用，1启用，默认1
	 */
	private Integer		flag	= 1;

	/**
	 * 非标准属性以json格式存储
	 */
	private String		attribute;

	/**
	 * 
	 */
	private Timestamp	cutime;

	/**
	 * 档案编号
	 */
	private String		archiveId;

	/**
	 * 工号
	 */
	private String		staffCode;

	/**
	 * 入职日期
	 */
	private Date		accessDate;

	/**
	 * 用工形式 01实习生;02正式工;03兼职;04实习转正式
	 */
	private String		employmentForm;

	/**
	 * 转正状态，0未转正(默认)；1转正
	 */
	private String		converseState;

	/**
	 * 转正日期
	 */
	private Date		converseDate;

	/**
	 * 身份证号码
	 */
	private String		idCardNo;

	/**
	 * 生日
	 */
	private Date		birthday;

	/**
	 * 出生日期(年),由身份证号解析,yyyy
	 */
	private String		birthdayY;

	/**
	 * 出生日期(月),由身份证号解析,MM
	 */
	private String		birthdayM;

	/**
	 * 出生日期(日),由身份证号解析,dd
	 */
	private String		birthdayD;

	/**
	 * 出生日期(年代),由身份证号解析,70,80,90
	 */
	private String		birthdayG;

	/**
	 * 年龄,由身份证号解析
	 */
	private Integer		age;

	/**
	 * 备注
	 */
	private String		memo;

	/**
	 * 社保(缴纳开始年月),yyyy/MM
	 */
	private String		socialInsuranceBegin;

	/**
	 * 本司调岗情况记录,初始化导入
	 */
	private String		postTransferDetail;

	/**
	 * 公积金(缴纳开始年月),yyyy/MM
	 */
	private String		providentFundBegin;

	/**
	 * 工资卡开户银行
	 */
	private String		bankName;

	/**
	 * 银行卡号
	 */
	private String		bankAccountNo;

	/**
	 * 最高学历 数据来源于数据字典
	 */
	private Integer		education;

	/**
	 * 毕业院校
	 */
	private String		graduateCollege;

	/**
	 * 专业
	 */
	private String		major;

	/**
	 * 毕业时间
	 */
	private String		graduateTime;

	/**
	 * 户口所在地址
	 */
	private String		registeredResidenceAddress;

	/**
	 * 通讯地址
	 */
	private String		address;

	/**
	 * 民族,数据来源于数据字典
	 */
	private Integer		nation;

	/**
	 * 户口性质 1农业;2城镇
	 */
	private String		anmelden;

	/**
	 * 原就业单位
	 */
	private String		originalCompany;

	/**
	 * 原工作岗位
	 */
	private String		originalPost;

	/**
	 * 紧急联络人姓名
	 */
	private String		urgPersonName;

	/**
	 * 紧急联络人关系 数据来源于数据字典
	 */
	private Integer		urgPersonRelation;

	/**
	 * 紧急联络人电话
	 */
	private String		urgPersonMobile;

	/**
	 * 参加工作时间
	 */
	private String		beginJobDate;

	/**
	 * 入职前累计工作年度
	 */
	private Integer		totalWorkYears;

	/**
	 * 婚姻状态 1未婚;2已婚;3离异
	 */
	private String		marriage;

	/**
	 * 生育状态 1未育;2已育
	 */
	private String		bear;

	/**
	 * 照片2张 0无;1有
	 */
	private String		photos;

	/**
	 * 面试登记表 0无;1有
	 */
	private String		interviewRegister;

	/**
	 * 员工信息登记表 0无;1有
	 */
	private String		staffInfoRegister;

	/**
	 * 身份证复印件 0无;1有
	 */
	private String		idcardCopies;

	/**
	 * 学历复印件 0无;1有
	 */
	private String		educationHisCopies;

	/**
	 * 学位复印件 0无;1有
	 */
	private String		degreeCopies;

	/**
	 * 其他资格证书复印件 0无;1有
	 */
	private String		otherCopies;

	/**
	 * 前公司离职证明原件 0无;1有
	 */
	private String		formerLeavingCertificate;

	/**
	 * 前公司离职声明 0无;1有
	 */
	private String		formerLeavingStatement;

	/**
	 * 入职承诺书 0无;1有
	 */
	private String		entryPromise;

	/**
	 * 个人工资卡复印件 0无;1有
	 */
	private String		payCardCopies;

	/**
	 * 劳动合同 0无;1有
	 */
	private String		employmentContract;

	/**
	 * 保密协议 0无;1有
	 */
	private String		secrecyAgreement;

	/**
	 * 实习协议书 0无;1有
	 */
	private String		internshipAgreement;

	/**
	 * 劳务协议书 0无;1有
	 */
	private String		laborAgreement;

	/**
	 * 薪资确认书 0无;1有
	 */
	private String		salaryConfirmation;

	/**
	 * 就业失业登记证原件 0无;1有
	 */
	private String		employmentRegistrationCertificate;

	/**
	 * 参保申明 0无;1有
	 */
	private String		insuredDeclare;

	/**
	 * 离职日期 值格式：YYYY/MM/DD
	 */
	private Date		leaveDate;

	/**
	 * 离职类型 来源于数据字典
	 */
	private Integer		leaveType;

	/**
	 * 离职原因 来源于数据字典
	 */
	private Integer		leaveReason;

	/**
	 * 离职手续是否办理 0否;1是
	 */
	private String		leaveProcedure;

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append("id=").append(id).append("\t");
		str.append("orgID=").append(orgID).append("\t");
		str.append("orgIDPath=").append(orgIDPath).append("\t");
		str.append("name=").append(name).append("\t");
		str.append("sex=").append(sex).append("\t");
		str.append("mobile=").append(mobile).append("\t");
		str.append("email=").append(email).append("\t");
		str.append("userName=").append(userName).append("\t");
		str.append("password=").append(password).append("\t");
		str.append("latestLoginTime=").append(latestLoginTime).append("\t");
		str.append("lastLoginTime=").append(lastLoginTime).append("\t");
		str.append("latestLoginIp=").append(latestLoginIp).append("\t");
		str.append("lastLoginIp=").append(lastLoginIp).append("\t");
		str.append("flag=").append(flag).append("\t");
		str.append("attribute=").append(attribute).append("\t");
		str.append("cutime=").append(cutime).append("\t");
		str.append("archiveId=").append(archiveId).append("\t");
		str.append("staffCode=").append(staffCode).append("\t");
		str.append("accessDate=").append(accessDate).append("\t");
		str.append("employmentForm=").append(employmentForm).append("\t");
		str.append("converseState=").append(converseState).append("\t");
		str.append("converseDate=").append(converseDate).append("\t");
		str.append("idCardNo=").append(idCardNo).append("\t");
		str.append("birthday=").append(birthday).append("\t");
		str.append("birthdayY=").append(birthdayY).append("\t");
		str.append("birthdayM=").append(birthdayM).append("\t");
		str.append("birthdayD=").append(birthdayD).append("\t");
		str.append("birthdayG=").append(birthdayG).append("\t");
		str.append("age=").append(age).append("\t");
		str.append("memo=").append(memo).append("\t");
		str.append("socialInsuranceBegin=").append(socialInsuranceBegin).append("\t");
		str.append("postTransferDetail=").append(postTransferDetail).append("\t");
		str.append("providentFundBegin=").append(providentFundBegin).append("\t");
		str.append("bankName=").append(bankName).append("\t");
		str.append("bankAccountNo=").append(bankAccountNo).append("\t");
		str.append("education=").append(education).append("\t");
		str.append("graduateCollege=").append(graduateCollege).append("\t");
		str.append("major=").append(major).append("\t");
		str.append("graduateTime=").append(graduateTime).append("\t");
		str.append("registeredResidenceAddress=").append(registeredResidenceAddress).append("\t");
		str.append("address=").append(address).append("\t");
		str.append("nation=").append(nation).append("\t");
		str.append("anmelden=").append(anmelden).append("\t");
		str.append("originalCompany=").append(originalCompany).append("\t");
		str.append("originalPost=").append(originalPost).append("\t");
		str.append("urgPersonName=").append(urgPersonName).append("\t");
		str.append("urgPersonRelation=").append(urgPersonRelation).append("\t");
		str.append("urgPersonMobile=").append(urgPersonMobile).append("\t");
		str.append("beginJobDate=").append(beginJobDate).append("\t");
		str.append("totalWorkYears=").append(totalWorkYears).append("\t");
		str.append("marriage=").append(marriage).append("\t");
		str.append("bear=").append(bear).append("\t");
		str.append("photos=").append(photos).append("\t");
		str.append("interviewRegister=").append(interviewRegister).append("\t");
		str.append("staffInfoRegister=").append(staffInfoRegister).append("\t");
		str.append("idcardCopies=").append(idcardCopies).append("\t");
		str.append("educationHisCopies=").append(educationHisCopies).append("\t");
		str.append("degreeCopies=").append(degreeCopies).append("\t");
		str.append("otherCopies=").append(otherCopies).append("\t");
		str.append("formerLeavingCertificate=").append(formerLeavingCertificate).append("\t");
		str.append("formerLeavingStatement=").append(formerLeavingStatement).append("\t");
		str.append("entryPromise=").append(entryPromise).append("\t");
		str.append("payCardCopies=").append(payCardCopies).append("\t");
		str.append("employmentContract=").append(employmentContract).append("\t");
		str.append("secrecyAgreement=").append(secrecyAgreement).append("\t");
		str.append("internshipAgreement=").append(internshipAgreement).append("\t");
		str.append("laborAgreement=").append(laborAgreement).append("\t");
		str.append("salaryConfirmation=").append(salaryConfirmation).append("\t");
		str.append("employmentRegistrationCertificate=").append(employmentRegistrationCertificate).append("\t");
		str.append("insuredDeclare=").append(insuredDeclare).append("\t");
		str.append("leaveDate=").append(leaveDate).append("\t");
		str.append("leaveType=").append(leaveType).append("\t");
		str.append("leaveReason=").append(leaveReason).append("\t");
		str.append("leaveProcedure=").append(leaveProcedure).append("\t");
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

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	 
	public String getSex()
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

	public void setLastLoginTime(Timestamp lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}

	 
	public Timestamp getLastLoginTime()
	{
		return this.lastLoginTime;
	}

	public void setLatestLoginIp(String latestLoginIp)
	{
		this.latestLoginIp = latestLoginIp;
	}

	 
	public String getLatestLoginIp()
	{
		return this.latestLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp)
	{
		this.lastLoginIp = lastLoginIp;
	}

	 
	public String getLastLoginIp()
	{
		return this.lastLoginIp;
	}

	public void setFlag(Integer flag)
	{
		this.flag = flag;
	}

	 
	public Integer getFlag()
	{
		return this.flag;
	}

	public void setAttribute(String attribute)
	{
		this.attribute = attribute;
	}

	 
	public String getAttribute()
	{
		return this.attribute;
	}

	public void setCutime(Timestamp cutime)
	{
		this.cutime = cutime;
	}

	 
	public Timestamp getCutime()
	{
		return this.cutime;
	}

	public void setArchiveId(String archiveId)
	{
		this.archiveId = archiveId;
	}

	 
	public String getArchiveId()
	{
		return this.archiveId;
	}

	public void setStaffCode(String staffCode)
	{
		this.staffCode = staffCode;
	}

	 
	public String getStaffCode()
	{
		return this.staffCode;
	}

	public void setAccessDate(Date accessDate)
	{
		this.accessDate = accessDate;
	}

	 
	public Date getAccessDate()
	{
		return this.accessDate;
	}

	public void setEmploymentForm(String employmentForm)
	{
		this.employmentForm = employmentForm;
	}

	 
	public String getEmploymentForm()
	{
		return this.employmentForm;
	}

	public void setConverseState(String converseState)
	{
		this.converseState = converseState;
	}

	 
	public String getConverseState()
	{
		return this.converseState;
	}

	public void setConverseDate(Date converseDate)
	{
		this.converseDate = converseDate;
	}

	 
	public Date getConverseDate()
	{
		return this.converseDate;
	}

	public void setIdCardNo(String idCardNo)
	{
		this.idCardNo = idCardNo;
	}

	 
	public String getIdCardNo()
	{
		return this.idCardNo;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	 
	public Date getBirthday()
	{
		return this.birthday;
	}

	public void setBirthdayY(String birthdayY)
	{
		this.birthdayY = birthdayY;
	}

	 
	public String getBirthdayY()
	{
		return this.birthdayY;
	}

	public void setBirthdayM(String birthdayM)
	{
		this.birthdayM = birthdayM;
	}

	 
	public String getBirthdayM()
	{
		return this.birthdayM;
	}

	public void setBirthdayD(String birthdayD)
	{
		this.birthdayD = birthdayD;
	}

	 
	public String getBirthdayD()
	{
		return this.birthdayD;
	}

	public void setBirthdayG(String birthdayG)
	{
		this.birthdayG = birthdayG;
	}

	 
	public String getBirthdayG()
	{
		return this.birthdayG;
	}

	public void setAge(Integer age)
	{
		this.age = age;
	}

	 
	public Integer getAge()
	{
		return this.age;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	 
	public String getMemo()
	{
		return this.memo;
	}

	public void setSocialInsuranceBegin(String socialInsuranceBegin)
	{
		this.socialInsuranceBegin = socialInsuranceBegin;
	}

	 
	public String getSocialInsuranceBegin()
	{
		return this.socialInsuranceBegin;
	}

	public void setPostTransferDetail(String postTransferDetail)
	{
		this.postTransferDetail = postTransferDetail;
	}

	 
	public String getPostTransferDetail()
	{
		return this.postTransferDetail;
	}

	public void setProvidentFundBegin(String providentFundBegin)
	{
		this.providentFundBegin = providentFundBegin;
	}

	 
	public String getProvidentFundBegin()
	{
		return this.providentFundBegin;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	 
	public String getBankName()
	{
		return this.bankName;
	}

	public void setBankAccountNo(String bankAccountNo)
	{
		this.bankAccountNo = bankAccountNo;
	}

	 
	public String getBankAccountNo()
	{
		return this.bankAccountNo;
	}

	public void setEducation(Integer education)
	{
		this.education = education;
	}

	 
	public Integer getEducation()
	{
		return this.education;
	}

	public void setGraduateCollege(String graduateCollege)
	{
		this.graduateCollege = graduateCollege;
	}

	 
	public String getGraduateCollege()
	{
		return this.graduateCollege;
	}

	public void setMajor(String major)
	{
		this.major = major;
	}

	 
	public String getMajor()
	{
		return this.major;
	}

	public void setGraduateTime(String graduateTime)
	{
		this.graduateTime = graduateTime;
	}

	 
	public String getGraduateTime()
	{
		return this.graduateTime;
	}

	public void setRegisteredResidenceAddress(String registeredResidenceAddress)
	{
		this.registeredResidenceAddress = registeredResidenceAddress;
	}

	 
	public String getRegisteredResidenceAddress()
	{
		return this.registeredResidenceAddress;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	 
	public String getAddress()
	{
		return this.address;
	}

	public void setNation(Integer nation)
	{
		this.nation = nation;
	}

	 
	public Integer getNation()
	{
		return this.nation;
	}

	public void setAnmelden(String anmelden)
	{
		this.anmelden = anmelden;
	}

	 
	public String getAnmelden()
	{
		return this.anmelden;
	}

	public void setOriginalCompany(String originalCompany)
	{
		this.originalCompany = originalCompany;
	}

	 
	public String getOriginalCompany()
	{
		return this.originalCompany;
	}

	public void setOriginalPost(String originalPost)
	{
		this.originalPost = originalPost;
	}

	 
	public String getOriginalPost()
	{
		return this.originalPost;
	}

	public void setUrgPersonName(String urgPersonName)
	{
		this.urgPersonName = urgPersonName;
	}

	 
	public String getUrgPersonName()
	{
		return this.urgPersonName;
	}

	public void setUrgPersonRelation(Integer urgPersonRelation)
	{
		this.urgPersonRelation = urgPersonRelation;
	}

	 
	public Integer getUrgPersonRelation()
	{
		return this.urgPersonRelation;
	}

	public void setUrgPersonMobile(String urgPersonMobile)
	{
		this.urgPersonMobile = urgPersonMobile;
	}

	 
	public String getUrgPersonMobile()
	{
		return this.urgPersonMobile;
	}

	public void setBeginJobDate(String beginJobDate)
	{
		this.beginJobDate = beginJobDate;
	}

	 
	public String getBeginJobDate()
	{
		return this.beginJobDate;
	}

	public void setTotalWorkYears(Integer totalWorkYears)
	{
		this.totalWorkYears = totalWorkYears;
	}

	 
	public Integer getTotalWorkYears()
	{
		return this.totalWorkYears;
	}

	public void setMarriage(String marriage)
	{
		this.marriage = marriage;
	}

	 
	public String getMarriage()
	{
		return this.marriage;
	}

	public void setBear(String bear)
	{
		this.bear = bear;
	}

	 
	public String getBear()
	{
		return this.bear;
	}

	public void setPhotos(String photos)
	{
		this.photos = photos;
	}

	 
	public String getPhotos()
	{
		return this.photos;
	}

	public void setInterviewRegister(String interviewRegister)
	{
		this.interviewRegister = interviewRegister;
	}

	 
	public String getInterviewRegister()
	{
		return this.interviewRegister;
	}

	public void setStaffInfoRegister(String staffInfoRegister)
	{
		this.staffInfoRegister = staffInfoRegister;
	}

	 
	public String getStaffInfoRegister()
	{
		return this.staffInfoRegister;
	}

	public void setIdcardCopies(String idcardCopies)
	{
		this.idcardCopies = idcardCopies;
	}

	 
	public String getIdcardCopies()
	{
		return this.idcardCopies;
	}

	public void setEducationHisCopies(String educationHisCopies)
	{
		this.educationHisCopies = educationHisCopies;
	}

	 
	public String getEducationHisCopies()
	{
		return this.educationHisCopies;
	}

	public void setDegreeCopies(String degreeCopies)
	{
		this.degreeCopies = degreeCopies;
	}

	 
	public String getDegreeCopies()
	{
		return this.degreeCopies;
	}

	public void setOtherCopies(String otherCopies)
	{
		this.otherCopies = otherCopies;
	}

	 
	public String getOtherCopies()
	{
		return this.otherCopies;
	}

	public void setFormerLeavingCertificate(String formerLeavingCertificate)
	{
		this.formerLeavingCertificate = formerLeavingCertificate;
	}

	 
	public String getFormerLeavingCertificate()
	{
		return this.formerLeavingCertificate;
	}

	public void setFormerLeavingStatement(String formerLeavingStatement)
	{
		this.formerLeavingStatement = formerLeavingStatement;
	}

	 
	public String getFormerLeavingStatement()
	{
		return this.formerLeavingStatement;
	}

	public void setEntryPromise(String entryPromise)
	{
		this.entryPromise = entryPromise;
	}

	 
	public String getEntryPromise()
	{
		return this.entryPromise;
	}

	public void setPayCardCopies(String payCardCopies)
	{
		this.payCardCopies = payCardCopies;
	}

	 
	public String getPayCardCopies()
	{
		return this.payCardCopies;
	}

	public void setEmploymentContract(String employmentContract)
	{
		this.employmentContract = employmentContract;
	}

	 
	public String getEmploymentContract()
	{
		return this.employmentContract;
	}

	public void setSecrecyAgreement(String secrecyAgreement)
	{
		this.secrecyAgreement = secrecyAgreement;
	}

	 
	public String getSecrecyAgreement()
	{
		return this.secrecyAgreement;
	}

	public void setInternshipAgreement(String internshipAgreement)
	{
		this.internshipAgreement = internshipAgreement;
	}

	 
	public String getInternshipAgreement()
	{
		return this.internshipAgreement;
	}

	public void setLaborAgreement(String laborAgreement)
	{
		this.laborAgreement = laborAgreement;
	}

	 
	public String getLaborAgreement()
	{
		return this.laborAgreement;
	}

	public void setSalaryConfirmation(String salaryConfirmation)
	{
		this.salaryConfirmation = salaryConfirmation;
	}

	 
	public String getSalaryConfirmation()
	{
		return this.salaryConfirmation;
	}

	public void setEmploymentRegistrationCertificate(String employmentRegistrationCertificate)
	{
		this.employmentRegistrationCertificate = employmentRegistrationCertificate;
	}

	 
	public String getEmploymentRegistrationCertificate()
	{
		return this.employmentRegistrationCertificate;
	}

	public void setInsuredDeclare(String insuredDeclare)
	{
		this.insuredDeclare = insuredDeclare;
	}

	 
	public String getInsuredDeclare()
	{
		return this.insuredDeclare;
	}

	public void setLeaveDate(Date leaveDate)
	{
		this.leaveDate = leaveDate;
	}

	 
	public Date getLeaveDate()
	{
		return this.leaveDate;
	}

	public void setLeaveType(Integer leaveType)
	{
		this.leaveType = leaveType;
	}

	 
	public Integer getLeaveType()
	{
		return this.leaveType;
	}

	public void setLeaveReason(Integer leaveReason)
	{
		this.leaveReason = leaveReason;
	}

	 
	public Integer getLeaveReason()
	{
		return this.leaveReason;
	}

	public void setLeaveProcedure(String leaveProcedure)
	{
		this.leaveProcedure = leaveProcedure;
	}

	 
	public String getLeaveProcedure()
	{
		return this.leaveProcedure;
	}

}