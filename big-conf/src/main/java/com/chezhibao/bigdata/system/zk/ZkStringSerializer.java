package com.chezhibao.bigdata.system.zk;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * @author WangCongJun
 * @date 2018/5/17
 * Created by WangCongJun on 2018/5/17.
 */
public class ZkStringSerializer implements ZkSerializer {
    @Override
    public byte[] serialize(Object data) throws ZkMarshallingError {

        return data.toString().getBytes();
    }

    @Override
    public String deserialize(byte[] bytes) throws ZkMarshallingError {
        return new String(bytes);
    }
}
