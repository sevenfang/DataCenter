package com.chezhibao.bigdata.cbr.download.dto;

import com.chezhibao.bigdata.cbr.download.bo.DownloadColumnBO;
import com.chezhibao.bigdata.cbr.download.bo.DownloadSqlBO;
import com.chezhibao.bigdata.cbr.download.vo.DownloadColumnVO;
import com.chezhibao.bigdata.cbr.download.vo.DownloadSqlVO;
import com.chezhibao.bigdata.cbr.download.vo.DownloadVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/12.
 */
public class DownloadDTO {
    public static List<DownloadColumnBO> getDownloadColumnBO(DownloadVO downloadVO){
        List<DownloadColumnBO> downloadColumnBOS = new ArrayList<>();
        List<DownloadColumnVO> columns = downloadVO.getColumns();
        String realreportId = downloadVO.getRealreportId();
        DownloadColumnBO downloadColumnBO;
        for (DownloadColumnVO c : columns){
            downloadColumnBO = new DownloadColumnBO();
            BeanUtils.copyProperties(c,downloadColumnBO);
            downloadColumnBO.setRealreportId(realreportId);
            downloadColumnBOS.add(downloadColumnBO);
        }
        return downloadColumnBOS;
    }
    public static List<DownloadSqlBO> getDownloadSqlBO(DownloadVO downloadVO){
        List<DownloadSqlBO> downloadSqlBOS = new ArrayList<>();
        List<DownloadSqlVO> sqls = downloadVO.getSqls();
        String realreportId = downloadVO.getRealreportId();
        DownloadSqlBO downloadSqlBO;
        for (DownloadSqlVO s : sqls){
            downloadSqlBO = new DownloadSqlBO();
            BeanUtils.copyProperties(s,downloadSqlBO);
            downloadSqlBO.setRealreportId(realreportId);
            downloadSqlBOS.add(downloadSqlBO);
        }
        return downloadSqlBOS;
    }

    public static DownloadVO getDownloadVO(String realreeportId,List<DownloadColumnBO> downloadColumnBOS,List<DownloadSqlBO>downloadSqlBOS){
        DownloadVO downloadVO = new DownloadVO();
        downloadVO.setRealreportId(realreeportId);
        List<DownloadColumnVO> columns = new ArrayList<>();
        List<DownloadSqlVO> sqls = new ArrayList<>();
        downloadVO.setColumns(columns);
        downloadVO.setSqls(sqls);
        DownloadColumnVO downloadColumnVO;
        DownloadSqlVO downloadSqlVO;
        for (DownloadColumnBO columnBO : downloadColumnBOS){
            downloadColumnVO = new DownloadColumnVO();
            BeanUtils.copyProperties(columnBO,downloadColumnVO);
            columns.add(downloadColumnVO);
        }
        for(DownloadSqlBO sqlBO : downloadSqlBOS){
            downloadSqlVO = new DownloadSqlVO();
            BeanUtils.copyProperties(sqlBO,downloadSqlVO);
            sqls.add(downloadSqlVO);
        }
        return downloadVO;
    }
}
