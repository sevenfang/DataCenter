package com.chezhibao.bigdata.datax.service.impl;

import com.chezhibao.bigdata.datax.pojo.DataxConfig;
import com.chezhibao.bigdata.datax.service.DataxService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DataxServiceImplTest {

    @Autowired
    DataxService dataxService;

    @Test
    public void getJobInfo() {
        DataxConfig jobInfo = dataxService.getJobInfo(14);
        log.info("【DataxServiceImplTest】DataxConfig:{}",jobInfo);
    }
}