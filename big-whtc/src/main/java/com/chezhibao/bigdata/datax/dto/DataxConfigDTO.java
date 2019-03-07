package com.chezhibao.bigdata.datax.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chezhibao.bigdata.datax.pojo.DataxConfig;
import com.chezhibao.bigdata.datax.vo.DataxVo;

/**
 * @author WangCongJun
 * @date 2018/5/9
 * Created by WangCongJun on 2018/5/9.
 */
public class DataxConfigDTO {

    /**
     * 将datax信息转换成dataxVO
     * @param dataxConfig
     * @return
     */
    public static DataxVo trans2DataxVo(DataxConfig dataxConfig){
        DataxVo dataxVo = new DataxVo();
        String data = dataxConfig.getData();
        JSONObject content = JSON.parseObject(data).getJSONObject("job").getJSONArray("content").getJSONObject(0);
        JSONObject reader = content.getJSONObject("reader");
        JSONObject writer = content.getJSONObject("writer");
        dataxVo.setReaderData(reader);
        dataxVo.setWriterData(writer);

        JSONObject jobInfo = new JSONObject();
        jobInfo.put("id",dataxConfig.getId());
        jobInfo.put("jobDesc",dataxConfig.getJobDesc());
        jobInfo.put("jobName",dataxConfig.getJobName());
        jobInfo.put("input_type",reader.get("name"));
        jobInfo.put("output_type",writer.get("name"));
        dataxVo.setJobInfo(jobInfo);

        return dataxVo;
    }

}
