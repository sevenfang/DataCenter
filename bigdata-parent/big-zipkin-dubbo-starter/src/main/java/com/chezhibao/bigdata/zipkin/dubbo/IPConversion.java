package com.chezhibao.bigdata.zipkin.dubbo;

/**
 * Created by chenjg on 16/8/4.
 */
public class IPConversion {

    public  static  int convertToInt(String ipAddr){
        String[] p4 = ipAddr.split("\\.");
        int ipInt = 0;
        int part = Integer.valueOf(p4[0]);
        ipInt = ipInt | (part << 24);
        part = Integer.valueOf(p4[1]);
        ipInt = ipInt | (part << 16);
        part = Integer.valueOf(p4[2]);
        ipInt = ipInt | (part << 8);
        part = Integer.valueOf(p4[3]);
        ipInt = ipInt | (part);
        return ipInt;
    }
}
