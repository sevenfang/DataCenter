package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.authorization.bo.AuthBO;
import com.chezhibao.bigdata.authorization.service.Authorization;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import com.chezhibao.bigdata.config.client.ParamsUtil;
import com.chezhibao.bigdata.service.QueryDataService;
import com.chezhibao.bigdata.vo.QueryDataVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询数据接口
 *
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/30.
 */
@RestController
@Slf4j
@RequestMapping("/cbr/data")
public class QueryDataController {

    @Autowired
    private QueryDataService queryDataService;

    @Autowired
    private Authorization authorization;

    /**
     * 查询报表数据：前端传递报表ID和当前的层级，以及当前行的数据（当前点击的行）
     *
     * @param queryDataVo
     * @return
     */
    @RequestMapping("/level")
    public BigdataResult getData(@RequestBody QueryDataVo queryDataVo, @CookieValue("LOGIN_USER_ID") Integer userId) {
        System.out.println(ParamsUtil.getDynamicValue("auth.sjb_user_list"));
        Assert.notNull(userId, "userId can't be null!!");
        AuthBO authBO = authorization.getAuthBo(userId,queryDataVo.getSimulatorId());
        log.info("【CBR】{}获取对应的权限信息{}",userId,authBO);
        if (!authBO.getIsAdministrator()) {
            Map<String, Object> params = queryDataVo.getParams();
            //如果返回为空则不允许访问报表
            if(ObjectUtils.isEmpty(authBO.getAuthParams())){
                return BigdataResult.build(300,"无权查看该报表"+userId);
            }
            params.put("cityIds", authBO.getCityIds());
            params.put("orgIds", authBO.getOrgIds());
        }
        List<LinkedHashMap<String, Object>> data = queryDataService.getCbrData(queryDataVo);
        return BigdataResult.ok(data);
    }
}
