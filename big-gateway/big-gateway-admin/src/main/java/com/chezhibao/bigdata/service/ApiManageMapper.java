package com.chezhibao.bigdata.service;

import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/9.
 */
@Component
@Mapper
public interface ApiManageMapper {
    @Insert("INSERT INTO `gateway`.`t_api` (`uri`, `desc`, `type`, `method`, `timeout`," +
            " `version`, `retries`, `loadbalance`, `userId`, `detail`, `createdTime`, `updatedTime`) " +
            "VALUES (#{uri}, #{desc}, #{type}, #{method}, #{timeout}, " +
            "#{version}, #{retries}, #{loadbalance}, #{userId}, #{detail}, " +
            "#{createdTime}, #{updatedTime});")
    void saveApiInfo(ApiInfo apiInfo);

    @Update("UPDATE `gateway`.`t_api` SET `uri`=#{uri}, `desc`=#{desc}, `type`=#{type}, `method`=#{method}, `timeout`=#{timeout}," +
            " `version`=#{version}, `retries`=#{retries}, `loadbalance`=#{loadbalance}, `userId`=#{userId}, " +
            "`detail`=#{detail}, `status`=#{status}, `updatedTime`= #{updatedTime} WHERE `id`= #{id};\n")
    void updateApiInfo(ApiInfo apiInfo);

    @Select("SELECT * FROM `gateway`.`t_api` WHERE uri=#{uri} AND method=#{method} AND version= #{version}")
    ApiInfo getApiInfoByUriAndMethodAndVersion(ApiInfo apiInfo);

    @Select("SELECT * FROM `gateway`.`t_api`")
    List<ApiInfo> getAllApi();

    @Select("SELECT * FROM `gateway`.`t_api` where id=#{id}")
    ApiInfo getApiById(Integer id);

    @Delete("DELETE FROM `gateway`.`t_api` where id=#{id}")
    void delApiById(Integer id);
}
