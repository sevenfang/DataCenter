package com.chezhibao.bigdata.cbrconfig.service;

import com.chezhibao.bigdata.cbrconfig.pojo.ChannelUser;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/18.
 */
public interface ChannelUserService {
    /**
     * 后去所有渠道人员信息
     * @return
     */
    List<ChannelUser> getAllChannelUser();

    /**
     * 删除渠道人员信息
     * @param channelUser
     * @return
     */
    Boolean delChannelUser(ChannelUser channelUser);

    /**
     * 更新渠道人员信息
     * @param channelUser
     * @return
     */
    Boolean updateChannelUser(ChannelUser channelUser);

    /**
     * 添加渠道人员信息
     * @param channelUser
     * @return
     */
    Boolean addChannelUser(ChannelUser channelUser);

}
