package com.chezhibao.bigdata.controller;

import com.chezhibao.bigdata.gateway.dto.ApiInfoDTO;
import com.chezhibao.bigdata.gateway.pojo.ApiInfo;
import com.chezhibao.bigdata.gateway.pojo.BigdataResult;
import com.chezhibao.bigdata.gateway.vo.ApiInfoVO;
import com.chezhibao.bigdata.service.ApiManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/3/7.
 */
@RestController
@RequestMapping("/gateway/admin")
@Slf4j
public class APIController {

    @Autowired
    private ApiManageService apiManageService;

    @PostMapping("/api")
    public Object createApi(@RequestBody ApiInfoVO apiInfoVO) {
        ApiInfo apiInfo = ApiInfoDTO.trans2ApiInfo(apiInfoVO);
        log.info("【gateway Admin】 create api params:{}",apiInfo);
        apiManageService.saveApi(apiInfo);
        return BigdataResult.ok();
    }
    @PostMapping("/api/sync")
    public Object syncApi(@RequestBody ApiInfoVO apiInfoVO) {
        ApiInfo apiInfo = ApiInfoDTO.trans2ApiInfo(apiInfoVO);
        log.info("【gateway Admin】 sync api params:{}",apiInfo);
        apiManageService.syncApi(apiInfo);
        return BigdataResult.ok();
    }

    @GetMapping("/api")
    public Object getApi() {
        List<ApiInfo> allApi = apiManageService.getAllApi();
        List<ApiInfoVO> apiInfoVOS = ApiInfoDTO.trans2ApiInfoVO(allApi);
        return BigdataResult.ok(apiInfoVOS);
    }
    @GetMapping("/api/{id}")
    public Object getApi(@PathVariable Integer id) {
        apiManageService.getApiById(id);
        return BigdataResult.ok(apiManageService.getAllApi());
    }

    @PutMapping("/api")
    public Object updateApi(@RequestBody ApiInfoVO apiInfoVO) {
        ApiInfo apiInfo = ApiInfoDTO.trans2ApiInfo(apiInfoVO);
        log.info("【gateway Admin】update api params:{}",apiInfo);
        apiManageService.updateApi(apiInfo);
        return BigdataResult.ok();
    }
    @DeleteMapping("/api")
    public Object deleteApi(@RequestBody ApiInfoVO apiInfoVO) {
        ApiInfo apiInfo = ApiInfoDTO.trans2ApiInfo(apiInfoVO);
        apiManageService.delApi(apiInfo);
        return BigdataResult.ok();
    }
}
