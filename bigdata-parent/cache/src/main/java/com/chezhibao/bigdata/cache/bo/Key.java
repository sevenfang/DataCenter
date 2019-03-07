package com.chezhibao.bigdata.cache.bo;


/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/11.
 */
public class Key {
    private String namespace;
    private String set;
    private String row;
    private String column;
    private String cell;

    public Key(String namespace, String set) {
        this.namespace = namespace;
        this.set = set;
    }

}
