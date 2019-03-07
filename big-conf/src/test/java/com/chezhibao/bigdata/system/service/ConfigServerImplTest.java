package com.chezhibao.bigdata.system.service;

import com.chezhibao.bigdata.system.constants.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

import static org.junit.Assert.*;

/**
 * @author WangCongJun
 * @date 2018/5/22
 * Created by WangCongJun on 2018/5/22.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigServerImplTest {

    @Autowired
    private SystemConfigServer configServer;

    @Test
    public void getPropFromZK() {
        Properties propFromZK = configServer.getPropFromZK("bigdata-config");
        System.out.println(propFromZK);
    }

    @Test
    public void put() {
    }

    @Test
    public void updateNode() {
    }

    @Test
    public void delNode() {
        configServer.delNode(Constants.BIGDATACONFIGMENU);
    }

    @Test
    public void createNode() {
    }

    @Test
    public void getChildren() {
    }

    @Test
    public void getSysMenu() {
    }

    public static void main(String[] args) {
    }
}