package com.chezhibao.bigdata.dbms.server.download.export;

import lombok.Data;

import java.sql.Statement;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2018/11/12.
 */
@Data
public class ExportServer {
    private Statement statement;
    private Boolean start;
}
