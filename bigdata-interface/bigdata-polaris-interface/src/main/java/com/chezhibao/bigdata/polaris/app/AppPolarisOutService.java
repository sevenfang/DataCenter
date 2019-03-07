package com.chezhibao.bigdata.polaris.app;

import java.util.Map;

/**
 * @auther HuangJie2
 * @date 18-12-27 下午1:10
 */
public interface AppPolarisOutService {

    /**
     *  APP端快速估价 和 车系均价 查询接口
     *
     * @param params
     * Integer.parseInt(params.get("seriesId") + "")    --若有modelId,可无须seriesId
     * Integer.parseInt(params.get("modelId") + "")     --若为0或null,则seriesId必传
     * Integer.parseInt(params.get("regionId") + "")    --地区ID
     * Integer.parseInt(params.get("mileage") + "")     --里程(单位:公里)
     * params.get("license") + ""  --上牌日期,格式yyyy-MM-dd  必传,且格式必须正确
     *
     *
     * @return
     *
     * Boolean.parseBoolean(polaris.get("success") + "")
     * polaris.get("message") + ""
     * Map data = (Map) polaris.get("data")
     * //获取data
     * Double.parseDouble(data.get("price") + "")
     * Double.parseDouble(data.get("before6MonthPrice") + "")
     * Double.parseDouble(data.get("before5MonthPrice") + "")
     * Double.parseDouble(data.get("before4MonthPrice") + "")
     * Double.parseDouble(data.get("before3MonthPrice") + "")
     * Double.parseDouble(data.get("before2MonthPrice") + "")
     * Double.parseDouble(data.get("before1MonthPrice") + "")
     * Double.parseDouble(data.get("after1MonthPrice") + "")
     * Double.parseDouble(data.get("after2MonthPrice") + "")
     * Double.parseDouble(data.get("after3MonthPrice") + "")
     * Double.parseDouble(data.get("before2YearPrice") + "")
     * Double.parseDouble(data.get("before1YearPrice") + "")
     * Double.parseDouble(data.get("after1YearPrice") + "")
     * Double.parseDouble(data.get("after2YearPrice") + "")
     * Double.parseDouble(data.get("after3YearPrice") + "")
     * data.get("default_condition") + ""
     * Double.parseDouble(data.get("excellent_min") + "")
     * Double.parseDouble(data.get("excellent_max") + "")
     * Double.parseDouble(data.get("good_min") + "")
     * Double.parseDouble(data.get("good_max") + "")
     * Double.parseDouble(data.get("normal_min") + "")
     * Double.parseDouble(data.get("normal_max") + "")
     *
     * 当success=false时,仅有message
     * 当success=true时,所有返回值均有
     */
    Map<String, Object> getPolarisQuick(Map<String, Object> params);


    /**
     * app端精准估价接口
     *
     * @param params
     * Integer.parseInt(params.get("bizLine") + "")    --业务线 (1车置宝（C2R） 2云检测 3R2R)(必填)
     * Integer.parseInt(params.get("carId") + "")     --carId(必填)
     * Integer.parseInt(params.get("detectId") + "")    --检测单ID(必填)
     *
     * @return
     * Boolean.parseBoolean(polaris.get("success") + "")
     * polaris.get("message") + ""
     * Double.parseDouble(data.get("price") + "")
     *
     * 当success=false时,仅有message
     * 当success=true时,所有返回值均有
     */
    Map<String, Object> getPolarisExact(Map<String, Object> params);
}
