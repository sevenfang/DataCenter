package com.chezhibao.bigdata.datax.dao.impl;

import com.chezhibao.bigdata.datax.dao.DataxDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author WangCongJun
 * @date 2018/5/8
 * Created by WangCongJun on 2018/5/8.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DataxDaoImplTest {

    @Autowired
    DataxDao dataxDao;


    @Test
    public void getAllTasks() {
    }
}