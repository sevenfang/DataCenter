package com.lebo.chezhibao.org.entity;

import java.io.Serializable;

/**
 * 
 * 〈用户相关信息类〉<br>
 * 〈功能详细描述〉
 * 
 * @author 王凯
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ClientInfo implements Serializable
{
	/**
	 */
	private static final long	serialVersionUID	= 1L;

	public ClientInfo()
	{
	}

	/**
	 * 终端码
	 */
	private int		client;

	/**
	 * 登录ip
	 */
	private String	ip;

	/**
	 * 会话ID
	 */
	private String	sessionId;

	@Override
	public String toString()
	{
		return String.format("[ClientInfo]sessionId:%s,ip:%s,client:%s", sessionId, ip, client);
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId()
	{
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	/**
	 * @return the ip
	 */
	public String getIp()
	{
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip)
	{
		this.ip = ip;
	}

	/**
	 * @return the client
	 */
	public int getClient()
	{
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(int client)
	{
		this.client = client;
	}

}
