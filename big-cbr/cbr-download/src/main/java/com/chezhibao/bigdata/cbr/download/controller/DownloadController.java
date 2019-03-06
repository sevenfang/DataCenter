package com.chezhibao.bigdata.cbr.download.controller;

import com.chezhibao.bigdata.cbr.download.bo.DownloadColumnBO;
import com.chezhibao.bigdata.cbr.download.bo.DownloadSqlBO;
import com.chezhibao.bigdata.cbr.download.dto.DownloadDTO;
import com.chezhibao.bigdata.cbr.download.service.DownloadColumnService;
import com.chezhibao.bigdata.cbr.download.service.DownloadDataService;
import com.chezhibao.bigdata.cbr.download.service.DownloadSqlService;
import com.chezhibao.bigdata.cbr.download.vo.DownloadDataVO;
import com.chezhibao.bigdata.cbr.download.vo.DownloadVO;
import com.chezhibao.bigdata.common.pojo.BigdataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/12.
 */
@RestController
@RequestMapping("/cbr")
@Slf4j
public class DownloadController {

    @Autowired
    private DownloadSqlService downloadSqlService;

    @Autowired
    private DownloadDataService downloadDataService;

    @Autowired
    private DownloadColumnService downloadColumnService;

    @PostMapping("/{realreportId}/download")
    public BigdataResult save(@PathVariable String realreportId,@RequestBody DownloadVO downloadVO){
        downloadVO.setRealreportId(realreportId);
        List<DownloadColumnBO> downloadColumnBO = DownloadDTO.getDownloadColumnBO(downloadVO);
        for(DownloadColumnBO columnBO:downloadColumnBO){
            downloadColumnService.saveOrUpdateColumn(columnBO);
        }
        List<DownloadSqlBO> downloadSqlBO = DownloadDTO.getDownloadSqlBO(downloadVO);
        for (DownloadSqlBO sqlBO : downloadSqlBO){
            downloadSqlService.saveOrUpdateSql(sqlBO);
        }
        List<DownloadColumnBO> allColumns = downloadColumnService.getAllColumns(realreportId);
        List<DownloadSqlBO> allSql = downloadSqlService.getAllSql(realreportId);
        if(allColumns.size()>0&&allSql.size()>0){
            DownloadVO downloadVO1 = DownloadDTO.getDownloadVO(realreportId, allColumns, allSql);
            return BigdataResult.ok(downloadVO1);
        }

        return BigdataResult.build(500,"创建失败！！");
    }

    @GetMapping("/download/{realreportId}")
    public BigdataResult save(@PathVariable("realreportId") String realreportId){
        List<DownloadColumnBO> allColumns = downloadColumnService.getAllColumns(realreportId);
        List<DownloadSqlBO> allSql = downloadSqlService.getAllSql(realreportId);
        DownloadVO downloadVO = DownloadDTO.getDownloadVO(realreportId, allColumns, allSql);
        return BigdataResult.ok(downloadVO);
    }
    @PostMapping("/download/data")
    public BigdataResult download(@RequestBody DownloadDataVO downloadDataVO){
        List<LinkedHashMap<String, Object>> download = downloadDataService.download(downloadDataVO.getRealreportId(), downloadDataVO.getParams());
        return BigdataResult.ok(download);
    }

}
