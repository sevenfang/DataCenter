package com.chezhibao.bigdata;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.net.URL;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/5.
 */
public class Test {
    public static void main(String[] args) {
        PathMatcher matcher = new AntPathMatcher();
        URL resource = Thread.currentThread().getContextClassLoader().getResource("resources");
        System.out.println(resource.getPath());
    }
}
