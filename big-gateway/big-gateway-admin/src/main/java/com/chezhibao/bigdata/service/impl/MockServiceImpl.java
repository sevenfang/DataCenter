package com.chezhibao.bigdata.service.impl;

import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/11.
 */
@Service
public class MockServiceImpl implements MockService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Object post(String url, Map<String, Object> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(params),headers);

        ResponseEntity<Object> exchange = restTemplate.exchange(url,HttpMethod.POST,httpEntity, Object.class);
        return exchange.getBody();
    }
    @Override
    public Object send(String url,String method, Map<String, Object> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(params),headers);

        HttpMethod httpMethod;
        switch (method){
            case "POST":
                httpMethod = HttpMethod.POST;
                break;
            case "GET":
                httpMethod = HttpMethod.GET;
                break;
            case "PUT":
                httpMethod = HttpMethod.PUT;
                break;
            case "DELETE":
                httpMethod = HttpMethod.DELETE;
                break;
                default:
                    httpMethod = HttpMethod.POST;
        }
        ResponseEntity<Object> exchange = restTemplate.exchange(url,httpMethod,httpEntity, Object.class);
        return exchange.getBody();
    }

    @Override
    public Object get() {
        return null;
    }

    @Override
    public Object put() {
        return null;
    }

    @Override
    public Object delete() {
        return null;
    }
}
