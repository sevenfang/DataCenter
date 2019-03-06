package com.chezhibao.bigdata.cbrconfig;

import com.chezhibao.bigdata.cbrconfig.pojo.LargeArea;
import com.chezhibao.bigdata.cbrconfig.service.LargeAreaService;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/9/18.
 */
@RestController
@RequestMapping("/cbr/area")
@Slf4j
public class LargeAreaController {

    private LargeAreaService largeAreaService;


    public LargeAreaController(LargeAreaService largeAreaService) {
        this.largeAreaService = largeAreaService;
    }

    //获取所有的大区信息
    @RequestMapping("/get/all")
    public BigdataResult getAllLargeArea() {
        List<LargeArea> allLargeAreaInfo = largeAreaService.getAllLargeAreaInfo();
        return BigdataResult.ok(allLargeAreaInfo);
    }

    //更新大区信息
    @RequestMapping("/update")
    public BigdataResult updateLargeArea(@RequestBody LargeArea largeArea) {
        log.debug("【CBR系统】更新大区信息largeArea:{}",largeArea);
        Boolean aBoolean = largeAreaService.updateLargeArea(largeArea);
        if(aBoolean){
            return BigdataResult.ok();
        }
        return BigdataResult.build(4005,"更新失败！！");
    }

    //删除大区信息
    @RequestMapping("/del")
    public BigdataResult delLargeArea(@RequestBody LargeArea largeArea) {
        log.debug("【CBR系统】删除大区信息largeArea:{}",largeArea);
        Boolean aBoolean = largeAreaService.delLargeArea(largeArea);
        if(aBoolean){
            return BigdataResult.ok();
        }
        return BigdataResult.build(4006,"删除失败！！");
    }

    //添加大区信息
    @RequestMapping("/add")
    public BigdataResult addLargeArea(@RequestBody LargeArea largeArea) {
        Boolean aBoolean = largeAreaService.saveLargeArea(largeArea);
        if(aBoolean){
            return BigdataResult.ok();
        }
        return BigdataResult.build(4007,"添加失败！！");
    }

    //获取大区选单信息
    @RequestMapping("/get/select")
    public BigdataResult getLargeAreaSelect() {
        Map<Integer, String> largeAreaIdAndName = largeAreaService.getLargeAreaIdAndName();
        return BigdataResult.ok(largeAreaIdAndName);
    }
}
