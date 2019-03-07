/*
 * Copyright (C), 2012-2020, Dragon.net
 * FileName: UserService.java
 * Author:   wangkai
 * Date:     2012-05-01
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.lebo.chezhibao.org.user.service;

import java.util.List;

import com.lebo.chezhibao.org.entity.SysOrg;
import com.lebo.chezhibao.org.entity.SysRole;
import com.lebo.chezhibao.org.entity.SysUser;
import com.lebo.chezhibao.org.entity.SysUserNew;
import com.lebo.chezhibao.org.entity.SysUserNewVo;

import dragon.core.system.Roles;

public interface UserService
{

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	public abstract List<SysUser> loadAllUser();

	/**
	 * 根据id数组获取指定用户
	 * 
	 * @param ids
	 * @return
	 */
	public abstract List<SysUser> loadNotMarkUser(int[] ids);

	/**
	 * 更新用户，视null为不更新(用户org关系不在此处更新)
	 * 
	 * @param user
	 * @return
	 */
	public abstract boolean updataUser(SysUser user);

	/**
	 * 根据id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public abstract SysUser getUser(int id);

	/**
	 * 有且紧对用户的组织关系维护string视null不更新 int视-1为不更新
	 * 
	 * @param user
	 * @return
	 */
	public abstract boolean updateUserOrg(SysUser user);

	/**
	 * 根据Ids如（1,2,3）获取用户
	 * 
	 * @param user
	 * @return
	 */
	public abstract List<SysUser> getUsersByIds(String ids);

	/**
	 * 根据ids批改变用户状态
	 * 
	 * @param ids
	 * @param state
	 * @return
	 */
	public abstract int changeUsersState(String ids, int flag);

	/**
	 * 
	 * 查询用户所属组织名称 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param user
	 *            用户
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public abstract String getOrgName(SysUser user);

	/**
	 * 
	 * 功能描述: <br>
	 * 根据用户查询所属组织机构
	 * 
	 * @param user
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	abstract SysOrg getOrg(SysUser user);

	/**
	 * 
	 * 获取用户角色 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public abstract List<SysRole> getRoles(int userId);

	/**
	 * 
	 * 判断用户是否拥有某种系统内部角色 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param user
	 *            用户
	 * @param role
	 *            系统内置角色
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	// public boolean hasRole(SysUser user, DefaultRoles role);

	/**
	 * 
	 * 判断用户是否拥有全部的角色 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param user
	 *            用户
	 * @param roles
	 *            多系统角色
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	// public boolean hasAllRoles(SysUser user, DefaultRoles... roles);

	/**
	 * 
	 * 判断用户是否拥有某一个角色 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param user
	 *            用户
	 * @param roles
	 *            多系统角色
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	// public boolean hasOneRole(SysUser user, DefaultRoles... roles);

	/**
	 * 
	 * 判断用户是否拥有某种业务角色 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param user
	 *            用户
	 * @param role
	 *            业务角色
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public boolean hasRole(SysUser user, Roles role);

	/**
	 * 
	 * 判断用户是否拥有全部的角色 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param user
	 *            用户
	 * @param roles
	 *            多业务角色
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public boolean hasAllRoles(SysUser user, Roles... roles);

	/**
	 * 
	 * 判断用户是否拥有某一个角色 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param user
	 *            用户
	 * @param roles
	 *            多业务角色
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public boolean hasOneRole(SysUser user, Roles... roles);

	/**
	 * 
	 * 判断用户是否拥有某种角色 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param user
	 *            用户
	 * @param code
	 *            角色编码
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public boolean hasRole(SysUser user, String code);

	/**
	 * 
	 * 缓存用户信息 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param user
	 *            用户
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public void cacheUser(SysUser user);

	/**
	 * 
	 * 获取缓存中的用户 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public SysUser getCacheUser(int id);

	/**
	 * 
	 * 移除缓存中的用户 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param id
	 *            用户ID
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public void removeCacheUser(int id);

	/**
	 * 
	 * 根据用户姓名查询系统用户 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param name
	 *            用户姓名
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public SysUser queryUserByName(String name);

	/**
	 * 
	 * 判断用户是否在线 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param uid
	 *            用户id
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public boolean isOnline(int uid);

	/**
	 * 
	 * 重置用户过期时间 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param uid
	 *            用户ID
	 * @param liveMinutes
	 *            过期时间，单位：分钟
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public void expire(int uid, int liveMinutes);

	/**
	 * 
	 * 功能描述: <br>
	 * 获取用户管辖地列表
	 * 
	 * @param userId
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	List<Integer> getUserRegion(int userId);

	/**
	 * 
	 * 功能描述: 用户ID查询用户详细信息<br>
	 * 〈功能详细描述〉
	 * 
	 * @param userId
	 *            用户id
	 * @return 用户信息详细信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	SysUser queryUserDetailsByUserId(String userId);

	/**
	 * 
	 * 功能描述: 用户ID查询用户信息<br>
	 * 〈功能详细描述〉 返回信息：用户编号ID、组织机构ID、组织机构ID路径、用户姓名、用户工号、性别、邮箱、
	 * 生日、状态、转正日期、转正状态、入职日期、身份证号码、民族、婚姻状态、生育状态、
	 * 通讯地址、最高学历、毕业院校、专业、毕业时间、参加工作时间、入职前累计工作月度、 户口性质、原就业单位、紧急联络人、紧急联络人关系、紧急联络人电话
	 * 
	 * @param userId
	 *            用户id
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	SysUserNewVo queryUserAndOrgInfoByUserId(String userId);

	// SysUser queryUserByUserIdExcludOrgInfo(String userId);

	/**
	 * 
	 * 功能描述:用户ID集合查询用户信息 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param userIds
	 *            用户id数组
	 * @return 用户信息集合
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	List<SysUserNewVo> queryUserAndOrgInfoByUserIds(String[] userIds);

	/**
	 * 
	 * 功能描述: 用户工号查询用户信息 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param number
	 *            用户工号
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	SysUserNewVo queryUserAndOrgInfoByNumber(String number);

	/**
	 * 
	 * 功能描述:用户工号集合查询用户信息<br>
	 * 〈功能详细描述〉
	 * 
	 * @param numbers
	 *            用户工号集合
	 * @return 用户信息集合
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	List<SysUserNewVo> queryUserAndOrgInfoByNumbers(String[] numbers);

	/**
	 * 
	 * 功能描述: 查询归属指定组织机构的用户列表<br>
	 * 〈功能详细描述〉
	 * 
	 * @param orgId
	 *            组织机构id
	 * @return 用户列表
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	List<SysUserNewVo> queryUserAndOrgInfoByOrgId(String orgId);

	/**
	 * 
	 * 功能描述:组织机构ID和状态查询用户列表<br>
	 * 〈功能详细描述〉
	 * 
	 * @param orgId
	 *            组织机构ID
	 * @param status
	 *            人员状态
	 * @return 用户列表
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	List<SysUserNewVo> queryUserAndOrgInfoByOrgIdAndStatus(String orgId, String status);

	/**
	 * 
	 * 功能描述: 查询指定岗位上的用户列表<br>
	 * 〈功能详细描述〉
	 * 
	 * @param postId
	 *            岗位ID
	 * @return 用户列表
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	List<SysUserNewVo> queryUserAndOrgInfoByPostId(String postId);

	/**
	 * 
	 * 功能描述:查询指定岗位集上的用户列表 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param postIds
	 *            岗位ID数组
	 * @return 用户列表
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	List<SysUserNewVo> queryUserAndOrgInfoByPostIds(String[] postIds);

	/**
	 * 
	 * 功能描述:身份证号码查询用户列表 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param id
	 *            身份证号码ID
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	SysUserNewVo queryUserAndOrgInfoById(String id);

	/**
	 * 
	 * 功能描述:按邮箱账号查询用户列表 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param emailId
	 *            邮箱账号ID
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	SysUserNewVo queryUserAndOrgInfoByEmailId(String emailId);

	/**
	 * 
	 * 功能描述: 用户姓名模糊查询用户列表<br>
	 * 〈功能详细描述〉
	 * 
	 * @param name
	 *            用户姓名
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	List<SysUserNewVo> queryUserAndOrgInfoByName(String name);

	/**
	 * 
	 * 功能描述: 新增用户基本信息<br>
	 * 〈功能详细描述〉
	 * 
	 * @param name
	 *            用户对象
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	int insertUserBaseInfo(SysUser user);

	/**
	 * 
	 * 功能描述: 新增用户基本信息(新表)<br>
	 * 〈功能详细描述〉
	 * 
	 * @param name
	 *            用户对象
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	int insertNewUserBaseInfo(SysUserNew user);

	/**
	 * 
	 * 功能描述: 新增用户基本信息(新表+老表)<br>
	 * 〈功能详细描述〉
	 * 
	 * @param name
	 *            用户对象
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	int insertAllUserBaseInfo(SysUserNew userNew, SysUser user);

	/**
	 * 
	 * 功能描述: 变更用户基本信息(新表+老表)<br>
	 * 〈功能详细描述〉
	 * 
	 * @param name
	 *            用户对象
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	int updateAllUserBaseInfo(SysUserNew userNew, SysUser user);

	/**
	 * 
	 * 功能描述: 停用和启用<br>
	 * 〈功能详细描述〉
	 * 
	 * @param name
	 *            用户对象
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	int changeUsersStateNew(String ids, int flag);
	
	/**
	 * 
	 * 功能描述: 根据组织机构数组查询归属的用户集合<br>
	 * 〈功能详细描述〉
	 * 
	 * @param name
	 *            用户对象
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	List<SysUser> queryUserByOrgArraysAndStatus(String[] ids, int flag);

	/**
	 * 
	 * 功能描述: 查询序列<br>
	 * 〈功能详细描述〉
	 * 
	 * @param name
	 *            用户对象
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	String queryNextSequencebById(String sequenceName);
	
	/**
	 * 更新用户性别信息
	 * 
	 * @param user
	 * @return
	 */
	public abstract boolean updataUserSexInfo(String  userID, String sex);

	/**
	 * 
	 * 功能描述: 用户工号查询用户信息 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param number
	 *            用户工号
	 * @return 用户信息
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	SysUserNew queryUserByStaffCode(String staffCode);
	
	/**
	 * 
	 * 功能描述: 根据用户ID查询用户所有信息，包括劳动合同 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param number
	 *            用户ID
	 * @return 用户信息（数组形式，数组下表对应导出excel索引位置)
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	String[] queryUserAllInfo(Integer userId,String[] headers);
	
	/**
	 * 功能描述: <br>
	 * 获取最近一次登陆的城市名称
	 *
	 * @param userId
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public String getLastLoginCity(int userId);
	
	/**
	 * 功能描述: <br>
	 * 设置最近一次登陆的城市名称
	 *
	 * @param userId
	 * @param city
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	void setLastLoginCity(int userId,String city);
	
	/**
	 * 
	 * 功能描述:组织机构ID和状态查询用户列表(递归查询组织机构下所有用户)<br>
	 * 〈功能详细描述〉
	 * 
	 * @param orgId
	 *            组织机构ID
	 * @param status
	 *            人员状态
	 * @return 用户列表
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	List<SysUserNewVo> queryUserAndOrgInfoByOrgIdAndStatusRecursive(String orgId, String status);
}
