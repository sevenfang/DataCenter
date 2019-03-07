package com.chezhibao.bigdata.jdbctemplate;

import com.chezhibao.bigdata.dao.CommonDao;
import com.chezhibao.bigdata.dao.WareHouseDao;
import com.chezhibao.bigdata.dao.jdbctemplate.DwdmTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/26.
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class DwdmTemplateImplTest {

    @Autowired
    private DwdmTemplate template;

    @Autowired
    private WareHouseDao wareHouseDao;

    @Test
    public void simpleQueryForList() {
        List<Map<String, Object>> maps = template.simpleQueryForList("select * from realreport.t_session", new HashMap<>());
        log.info(maps+"");
        List<Map<String, Object>> select = wareHouseDao.select("select * from realreport.t_session", new HashMap<>());
        log.info(select+"");
    }

    @Test
    public void simpleQueryForList1() {
    }
}