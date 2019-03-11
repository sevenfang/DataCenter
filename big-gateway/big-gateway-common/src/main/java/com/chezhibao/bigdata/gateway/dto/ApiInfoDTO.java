package com.chezhibao.bigdata.gateway.dto;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.vo.ApiInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/11.
 */
public class ApiInfoDTO {
    public static ApiInfo trans2ApiInfo(ApiInfoVO apiInfoVO){
        ApiInfo apiInfo = new ApiInfo();
        BeanUtils.copyProperties(apiInfoVO,apiInfo);
        Map<String, Object> detail = apiInfoVO.getDetail();
        if(ObjectUtils.isEmpty(detail)){
            apiInfo.setDetail("");
        }else {
            apiInfo.setDetail(JSON.toJSONString(detail));
        }
        return apiInfo;
    }
    public static ApiInfoVO trans2ApiInfoVO(ApiInfo apiInfo){
        ApiInfoVO apiInfoVO = new ApiInfoVO();
        BeanUtils.copyProperties(apiInfo,apiInfoVO);
        String detail = apiInfo.getDetail();
        if(StringUtils.isEmpty(detail)){
            apiInfoVO.setDetail(new HashMap<>());
        }else {
            apiInfoVO.setDetail((Map<String, Object>) JSON.parse(detail));
        }
        return apiInfoVO;
    }
    public static List<ApiInfoVO> trans2ApiInfoVO(List<ApiInfo> apiInfos){
        List<ApiInfoVO> apiInfoVOS = new ArrayList<>(apiInfos.size());
        for(ApiInfo apiInfo : apiInfos){
            ApiInfoVO apiInfoVO = trans2ApiInfoVO(apiInfo);
            apiInfoVOS.add(apiInfoVO);
        }
        return apiInfoVOS;
    }
}
