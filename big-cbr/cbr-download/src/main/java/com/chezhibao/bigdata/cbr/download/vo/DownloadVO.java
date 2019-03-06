package com.chezhibao.bigdata.cbr.download.vo;

import lombok.Data;

import java.util.List;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/12/12.
 */
@Data
public class DownloadVO {
    private String realreportId;
    private List<DownloadColumnVO> columns;
    private List<DownloadSqlVO> sqls;
}
