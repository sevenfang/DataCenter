package com.chezhibao.bigdata.msg.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/7/12.
 */
public class Test {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mame","je\"rry");
        jsonObject.put("age",0);
        String s = jsonObject.toJSONString();
        System.out.println(s);
        Person person = JSON.parseObject(s, Person.class);
        System.out.println(person.getAge());
    }
    static class Person{
        private String name;
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
