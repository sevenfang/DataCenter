package com.chezhibao.bigdata.authorization.bo;

import java.io.Serializable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/4.
 */
public class ClientInfoBO implements Serializable {
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
