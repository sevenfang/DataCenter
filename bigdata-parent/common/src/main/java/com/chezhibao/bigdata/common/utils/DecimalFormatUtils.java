package com.chezhibao.bigdata.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/6/27.
 */
@Slf4j
public class DecimalFormatUtils {
    private static final DecimalFormat df = new DecimalFormat("#.00");
    private static NumberFormat nf;
    static {
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
    }

    public static Double getPointBack2(Double t) throws NumberFormatException{
        String format = nf.format(t);
            return Double.parseDouble(format);

    }
    public static Float getPointBack2(Float t) throws NumberFormatException{
        String format = df.format(t);
        return Float.parseFloat(format);
    }
    //计算增长率
    public static Double getRate(Double t,Double t2){
        double v= (t - t2) / t2*100;
        try {
            String format = nf.format(v);
            return Double.parseDouble(format);
        }catch (NumberFormatException e){
            log.error("【计算增长率】数字1:{},数字1:{}，增长率:{}",t,t2,v,e);
        }
        return 0D;
    }
}
