package com.chezhibao.bigdata.adapter;


import com.alibaba.fastjson.JSON;
import com.chezhibao.bigdata.gateway.adapter.dubbo.DubboProxyService;
import com.chezhibao.bigdata.gateway.adapter.dubbo.DubboProxyService.DubboRequest;
import org.junit.Before;
import org.junit.Test;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/2/19.
 */
public class DubboProxyServiceTest {
    DubboProxyService dubboProxyService;
    @Before
    public void before() {
        dubboProxyService = new DubboProxyService();

    }

    @Test
    public void testDubboEntityParam() {
        String json = "{\n" +
                "  \"interfaceName\":\"org.dromara.soul.test.dubbo.api.service.DubboTestService\",\n" +
                "  \"method\":\"insert\",\n" +
                "  \"timeout\":\"50000\",\n" +
                "  \"paramClass\":[\"org.dromara.soul.test.dubbo.api.entity.DubboTest\"],\n" +
                "  \"paramValues\":[{\n" +
                "    \"id\":\"xxxx\",\n" +
                "    \"name\":\"xiaoyu\"\n" +
                "  }]\n" +
                "}";

        final Object o = dubboProxyService.genericInvoker(JSON.parseObject(json,DubboRequest.class));
        System.out.println(o.toString());
    }

    @Test
    public void testDubboEntityStringIntegerParam() {
        String json = "{" +
                "  \"interfaceName\":\"org.dromara.soul.test.dubbo.api.service.DubboTestService\",\n" +
                "  \"method\":\"testEntityStringParam\",\n" +
                "  \"timeout\":\"50000\",\n" +
                "  \"paramClass\":[\"org.dromara.soul.test.dubbo.api.entity.DubboTest\"],\n" +
                "  \"paramValues\":[{\n" +
                "    \"id\":\"xxx\",\n" +
                "    \"name\":\"xiaoyu\"\n" +
                "  }],\n" +
                "  \"params\":{" +
                "    \"java.lang.String\":\"666\",\n" +
                "    \"java.lang.Integer\":\"1\"\n" +
                "  }\n" +
                "}";
        final Object o = dubboProxyService.genericInvoker(JSON.parseObject(json,DubboRequest.class));
        System.out.println(o.toString());
    }

    @Test
    public void testDubboStringParam() {
        DubboRequest dubboRequest = new DubboRequest();
        dubboRequest.setInterfaceName("org.dromara.soul.test.dubbo.api.service.DubboTestService");
        dubboRequest.setMethod("findByIdAndName");

//        dubboRequest.setParamClass(new String[]{"java.lang.String","java.lang.String"});
//        dubboRequest.setParamValues(new Object[]{"666","1"});
        dubboRequest.setTimeout(5000);
        final Object o = dubboProxyService.genericInvoker(dubboRequest);
        System.out.println(o.toString());
    }

    @Test
    public void testListEntity() {
        String json = "{\n" +
                "    \"interfaceName\": \"org.dromara.soul.test.dubbo.api.service.DubboTestService\", \n" +
                "    \"method\": \"testListEntity\", \n" +
                "    \"timeout\": \"5000\", \n" +
                "    \"paramClass\": [\n" +
                "        \"java.util.List\"\n" +
                "    ], \n" +
                "    \"paramValues\": [\n" +
                "        [{\n" +
                "            \"id\": \"xxxx\", \n" +
                "            \"name\": \"y\", \n" +
                "        }, \n" +
                "        {\n" +
                "            \"id\": \"xxx\", \n" +
                "            \"name\": \"y\", \n" +
                "        }]\n" +
                "    ]\n" +
                "}";
        final Object o = dubboProxyService.genericInvoker(JSON.parseObject(json,DubboRequest.class));
        System.out.println(o.toString());

    }
    @Test
    public void testMultiEntity() {
        String json = "{\n" +
                "    \"interfaceName\": \"org.dromara.soul.test.dubbo.api.service.DubboTestService\", \n" +
                "    \"method\": \"testMultiEntity\", \n" +
                "    \"timeout\": \"5000\", \n" +
                "    \"paramClass\": [\n" +
                "        \"org.dromara.soul.test.dubbo.api.entity.DubboTest\",\n" +
                "        \"org.dromara.soul.test.dubbo.api.entity.DubboTest\"\n" +
                "    ], \n" +
                "    \"paramValues\": [\n" +
                "        {\n" +
                "            \"id\": \"xxxx\", \n" +
                "            \"name\": \"y\" \n" +
                "        }, \n" +
                "        {\n" +
                "            \"id\": \"xxx\", \n" +
                "            \"name\": \"yy\" \n" +
                "        }\n" +
                "    ]\n" +
                "}";
        final Object o = dubboProxyService.genericInvoker(JSON.parseObject(json,DubboRequest.class));
        System.out.println(o.toString());

    }
    @Test
    public void testMultiEntity1() {
        String json = "{\n" +
                "    \"interfaceName\": \"org.dromara.soul.test.dubbo.api.service.DubboTestService\", \n" +
                "    \"method\": \"testMultiEntity\", \n" +
                "    \"timeout\": \"5000\", \n" +
                "    \"paramClass\": [\n" +
                "        \"org.dromara.soul.test.dubbo.api.entity.DubboTest\",\n" +
                "        \"java.lang.String\",\n" +
                "        \"org.dromara.soul.test.dubbo.api.entity.DubboTest\"\n" +
                "    ], \n" +
                "    \"paramValues\": [\n" +
                "        {\n" +
                "            \"id\": \"xxxx\", \n" +
                "            \"name\": \"y\" \n" +
                "        }, \n" +
                "       \"susan\","+
                "        {\n" +
                "            \"id\": \"xxx\", \n" +
                "            \"name\": \"yy\" \n" +
                "        }\n" +
                "    ]\n" +
                "}";
        final Object o = dubboProxyService.genericInvoker(JSON.parseObject(json,DubboRequest.class));
        System.out.println(o.toString());

    }
    @Test
    public void testEntityStringListParam() {
        String json = "{\n" +
                "    \"interfaceName\": \"org.dromara.soul.test.dubbo.api.service.DubboTestService\", \n" +
                "    \"method\": \"testEntityStringListParam\", \n" +
                "    \"timeout\": \"5000\", \n" +
                "    \"paramClass\": [\n" +
                "        \"org.dromara.soul.test.dubbo.api.entity.DubboTest\",\n" +
                "        \"java.lang.String\",\n" +
                "        \"java.util.List\"\n" +
                "    ], \n" +
                "    \"paramValues\": [\n" +
                "        {\n" +
                "            \"id\": \"xxxx\", \n" +
                "            \"name\": \"y\" \n" +
                "        }, \n" +
                "       \"susan\","+
                "       [1,2,3]"+
                "    ]\n" +
                "}";
        final Object o = dubboProxyService.genericInvoker(JSON.parseObject(json,DubboRequest.class));
        System.out.println(o.toString());
    }

    @Test
    public void testMapEntity() {
        String json = "{\n" +
                "    \"interfaceName\": \"org.dromara.soul.test.dubbo.api.service.DubboTestService\", \n" +
                "    \"method\": \"testMapEntity\", \n" +
                "    \"timeout\": \"5000\", \n" +
                "    \"paramClass\": [\n" +
                "        \"org.dromara.soul.test.dubbo.api.entity.DubboTest\",\n" +
                "        \"java.util.Map\"\n" +
                "    ], \n" +
                "    \"paramValues\": [\n" +
                "        {\n" +
                "            \"id\": \"xxxx\", \n" +
                "            \"name\": \"y\" \n" +
                "        }, \n" +
                "        {" +
                          "\"test\":{\"id\":\"555\"},"+
                "        }"+
                "    ]\n" +
                "}";
        final Object o = dubboProxyService.genericInvoker(JSON.parseObject(json,DubboRequest.class));
        System.out.println(o.toString());
    }

    @Test
    public void testInt(){
        DubboRequest dubboRequest = new DubboRequest();
        dubboRequest.setInterfaceName("org.dromara.soul.test.dubbo.api.service.DubboTestService");
        dubboRequest.setMethod("testInt");
//        dubboRequest.setParamClass(new String[]{"int"});
//        dubboRequest.setParamValues(new Object[]{2});
        dubboRequest.setTimeout(5000);
        final Object o = dubboProxyService.genericInvoker(dubboRequest);
        System.out.println(o.toString());
    }
    @Test
    public void testDate(){
        DubboRequest dubboRequest = new DubboRequest();
        dubboRequest.setInterfaceName("org.dromara.soul.test.dubbo.api.service.DubboTestService");
        dubboRequest.setMethod("testDate");
//        dubboRequest.setParamClass(new String[]{"java.util.Date"});
//        dubboRequest.setParamValues(new Object[]{"2019-02-21 12:25:23"});
        dubboRequest.setTimeout(5000);
        final Object o = dubboProxyService.genericInvoker(dubboRequest);
        System.out.println(o.toString());
    }
}