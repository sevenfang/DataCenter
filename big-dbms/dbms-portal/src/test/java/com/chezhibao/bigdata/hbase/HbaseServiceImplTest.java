package com.chezhibao.bigdata.hbase;

import com.chezhibao.bigdata.dbms.hbase.HbaseService;
import com.chezhibao.bigdata.dbms.hbase.bo.HbaseParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/19.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HbaseServiceImplTest {
    @Autowired
    HbaseService hbaseService;

    /**
     * 渠道推荐商户
     */
    public static final String TABLE_NAME="channel_recommend_buyer";

    /**
     * 根据车编返回推荐商户  对接人：柯文 Hbase表名:CHANNEL_RECOMMEND_BUYER cf:car
     */
    public static final String RECOMMEND_BY_CARID_KEY_PREFIX="recommend_by_carid_";
    /**
     * 根据渠道人员ID获取车辆推荐给商户 对接人：柯文 Hbase表名:CHANNEL_RECOMMEND_BUYER cf:channel
     */
    public static final String RECOMMEND_BY_CHANNELID_KEY_PREFIX="recommend_by_channelid_";

    @Test
    public void save() {

    }

    @Test
    public void get() {
        HbaseParam<String> param = new HbaseParam<>();
        param.setRowKey(RECOMMEND_BY_CHANNELID_KEY_PREFIX + 3275);
        param.setTableName(TABLE_NAME);
        param.setCf("channel");
        param.setQualifier("cuoche");
        String string = hbaseService.getString(param);
        System.out.println(string);
    }

    @Test
    public void deleteByrowKey() {
    }

    @Test
    public void getString() {
    }
}