package com.sohu.tv.cachecloud.client.basic.util;

import com.chezhibao.bigdata.redis.CloudCacheConfigAdapter;

import java.util.ResourceBundle;

/**
 * 客户端常量
 *
 * @author leifu
 * @Date 2014年6月21日
 * @Time 上午10:54:34
 */
public class ConstUtils {

    public static final String HTTP_PREFIX = "http://";

    /**
     * http连接和读取超时
     */
    public static final int HTTP_CONN_TIMEOUT = 1000;
    public static final int HTTP_SOCKET_TIMEOUT = 1000;

    /**
     * 客户端版本信息
     */
    public static final String CLIENT_VERSION;

    /**
     * 上报域名和对应各个类型redis的rest url.
     */
    public static final String DOMAIN_URL;
    /**
     * redis_cluster_suffix = /cache/client/redis/cluster/%s.json?clientVersion=
     * redis_sentinel_suffix = /cache/client/redis/sentinel/%s.json?clientVersion=
     * redis_standalone_suffix = /cache/client/redis/standalone/%s.json?clientVersion=
     * cachecloud_report_url = /cachecloud/client/reportData.json
     */
    public static final String REDIS_CLUSTER_URL;
    public static final String REDIS_SENTINEL_URL;
    public static final String REDIS_STANDALONE_URL;
    public static final String CACHECLOUD_REPORT_URL;
    public static final String APP_LIST_URL;
    private static  GetRedisClusterAddrFromCloudProperties cloudProperties;

    static {
        cloudProperties = CloudCacheConfigAdapter.getConfig();
        if(cloudProperties==null){
            cloudProperties = new GetRedisClusterAddrFromCloudProperties();
            ResourceBundle rb = ResourceBundle.getBundle("cacheCloudClient");
            cloudProperties.setClientVersion(rb.getString("client_version"));
            cloudProperties.setDomainUrl(rb.getString("domain_url"));
        }
        CLIENT_VERSION = cloudProperties.getClientVersion();
        String domainUrl = cloudProperties.getDomainUrl();
        if (!domainUrl.startsWith(HTTP_PREFIX)) {
            domainUrl = HTTP_PREFIX + domainUrl;
        }
        if (domainUrl.endsWith("/")) {
            domainUrl = domainUrl.substring(0,domainUrl.length()-1);
        }
        DOMAIN_URL = domainUrl;
        REDIS_CLUSTER_URL = DOMAIN_URL + "/cache/client/redis/cluster/%s.json?clientVersion=" + CLIENT_VERSION;
        REDIS_SENTINEL_URL = DOMAIN_URL + "/cache/client/redis/sentinel/%s.json?clientVersion=" + CLIENT_VERSION;
        REDIS_STANDALONE_URL = DOMAIN_URL + "/cache/client/redis/standalone/%s.json?clientVersion=" + CLIENT_VERSION;
        CACHECLOUD_REPORT_URL = DOMAIN_URL + "/cachecloud/client/reportData.json";
        APP_LIST_URL = DOMAIN_URL + "/cache/client/redis/apps";
    }

    public static GetRedisClusterAddrFromCloudProperties cloudProperties() {
        return cloudProperties;
    }
}
