package com.chezhibao.bigdata.cache.config;

import java.util.Hashtable;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/11.
 */
public class CacheProperties {
        private String nameSpace;
        private Integer clusterId;
        private String url;
        private String version;

        public String getNameSpace() {
                return nameSpace;
        }

        public void setNameSpace(String nameSpace) {
                this.nameSpace = nameSpace;
        }

        public Integer getClusterId() {
                return clusterId;
        }

        public void setClusterId(Integer clusterId) {
                this.clusterId = clusterId;
        }

        public String getUrl() {
                return url;
        }

        public void setUrl(String url) {
                this.url = url;
        }

        public String getVersion() {
                return version;
        }

        public void setVersion(String version) {
                this.version = version;
        }

        @Override
        public String toString() {
                return "CacheProperties{" +
                        "nameSpace='" + nameSpace + '\'' +
                        ", clusterId=" + clusterId +
                        ", url='" + url + '\'' +
                        ", version='" + version + '\'' +
                        '}';
        }
}
