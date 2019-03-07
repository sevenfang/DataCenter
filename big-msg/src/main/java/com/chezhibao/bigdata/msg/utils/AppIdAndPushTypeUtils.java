package com.chezhibao.bigdata.msg.utils;

import com.chezhibao.bigdata.msg.constants.AppPushConstants;
import com.chezhibao.bigdata.msg.constants.BuyerPushMsgConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * 1=竞拍android，2=竞拍IOS，3=渠道android，4=渠道IOS,5=CRM小手机,6=个人android，7=个人ios, 8=车检宝安卓
 * 2018-01-06  9云检测安卓、10云检测IOS
 * @author WangCongJun
 * @date 2018/4/28
 * Created by WangCongJun on 2018/4/28.
 */
@Slf4j
public class AppIdAndPushTypeUtils {
    static final String Client_Id_Ios = "2";
    static final String Client_Id_Android = "3";

    /**
     * 根据客户端的Id确定AppID
     * @param client
     * @return
     */
    public static Integer transBuyerClientToAppId(String client)
    {
        log.info("【确定AppID】商户的clientID为：{}" ,client);

        //商户默认是竞拍App的Ios
        String appId = BuyerPushMsgConstants.PushMsg_AppId_BuyerAuction_IOS.getConfigValue();
        if (Client_Id_Ios.equals(client))
        {
            appId = BuyerPushMsgConstants.PushMsg_AppId_BuyerAuction_IOS.getConfigValue();
        }
        else if (Client_Id_Android.equals(client))
        {
            appId = BuyerPushMsgConstants.PushMsg_AppId_BuyerAuction_Android.getConfigValue();
        }
        return Integer.parseInt(appId);
    }


    /**
     * 根据客户端的Id确定AppID
     * @param client
     * @return
     */
    public static String transBuyerAppType(String client)
    {
        log.info("【确定AppID】商户的clientID为：{}" ,client);

        //商户默认是竞拍App的Ios
        String appType = "2";
        if (Client_Id_Android.equals(client))
        {
            appType = "1";
        }
        log.info("【确定AppID】商户的AppID为：{}" ,appType);
        return appType;
    }

    /**
     * 根据客户端的Id确定渠道AppID
     * @param client
     * @return
     */
    public static Integer transChannelAppId(String client)
    {
        log.info("【确定AppID】商户的clientID为：{}" ,client);

        //商户默认是竞拍App的Ios
        Integer appId = 1;
        if (Client_Id_Ios.equals(client))
        {
            appId = 1;
        }
        else if (Client_Id_Android.equals(client))
        {
            appId = 2;
        }
        return appId;
    }

    /**
     * 根据用户组和clientId确定消息类型
     * @param group
     * @param client
     * @return
     */
    public AppPushConstants getAppPushType(String group, String client)
    {
        log.info("【确定PUSH消息类型】所在组{},客户端ID:{}",group,client);


        if ( AppPushConstants.Group_Auction.getValue().equals(group) )
        {
            if ( AppPushConstants.BuyerClient_Android.getValue().equals(client) )
            {
                return AppPushConstants.AppPushType_AUC_Android ;
            }
            else if ( AppPushConstants.BuyerClient_IOS.getValue().equals(client) )
            {
                return AppPushConstants.AppPushType_AUC_IOS ;
            }
            else
            {
                return null;
            }

        }
        else if ( AppPushConstants.Group_Channel.getValue().equals(group) )
        {
            if ( AppPushConstants.ChannelClient_Android.getValue().equals(client) || AppPushConstants.ChannelClient_AndroidNew.getValue().equals(client) )
            {
                return AppPushConstants.AppPushType_CHA_Android ;
            }
            else if ( AppPushConstants.ChannelClient_IOS.getValue().equals(client) || AppPushConstants.ChannelClient_IOSNew.getValue().equals(client) )
            {
                return AppPushConstants.AppPushType_CHA_IOS ;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null ;
        }

    }
}
