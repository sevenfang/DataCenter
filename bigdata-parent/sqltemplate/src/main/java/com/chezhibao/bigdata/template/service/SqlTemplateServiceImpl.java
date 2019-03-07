package com.chezhibao.bigdata.template.service;

import com.chezhibao.bigdata.template.config.SqlTemplateConfigration;
import com.chezhibao.bigdata.template.enums.SqlTemplateEnum;
import com.chezhibao.bigdata.template.exception.SqlTemplateException;
import com.chezhibao.bigdata.template.utils.FreeMakerParser;
import com.chezhibao.bigdata.template.utils.SqlTemplateUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/25
 * Created by WangCongJun on 2018/4/25.
 */
@Data
@Slf4j
public class SqlTemplateServiceImpl implements SqlTemplateService {

    private SqlTemplateConfigration sqlTemplateConfigration;

    private Map<String, Map<String, String>> sqlContainer;

    private static final String SQL_TEMPLATE_PATH = "classpath*:sqltemplates/**/*.xml";

    public SqlTemplateServiceImpl() {
    }

    public SqlTemplateServiceImpl(SqlTemplateConfigration sqlTemplateConfigration) {
        //不在使用配置这里写死

        sqlContainer = SqlTemplateUtils.getSqlContainer(SQL_TEMPLATE_PATH);
//        sqlContainer = SqlTemplateUtils.getSqlContainer(sqlTemplateConfigration.getPath());
    }

    @Override
    public String getSql(String sqlId, Map<String, Object> params) {
        Assert.notNull(sqlId, "sqlId can not be null!");
        int i = sqlId.lastIndexOf(".");

        if (i == -1) {
            throw new SqlTemplateException(SqlTemplateEnum.SQLID_FORMAT_ERROR);
        }
        //获取sqlID中文件路径
        String filePath = sqlId.substring(0, i);
        Map<String, String> stringStringMap = sqlContainer.get(filePath);
        if (CollectionUtils.isEmpty(stringStringMap)) {
            log.error("【sql模板处理】sqlContainer中{}.xml不存在", filePath);
            throw new SqlTemplateException(SqlTemplateEnum.SQL_XMLFILE_NOT_EXIST);
        }
        //获取sqlID中sql的id值
        String id = sqlId.substring(i + 1);
        String sql = stringStringMap.get(id);
        if (StringUtils.isEmpty(sql)) {
            throw new SqlTemplateException(SqlTemplateEnum.SQLID_NOT_EXIST);
        }
        // sql还需要freemarker转换
        return getSqlFromStringTemplate(sql, params);
    }

    @Override
    public String getSqlFromStringTemplate(String sqlStringTemplate, Map<String, Object> params) {
        // sql还需要freemarker转换
        //多加一层 用params.**访问数据
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("params", params);
        //在freemarker中#{}是会被解析的，解决与mybatis冲突了
        String s = sqlStringTemplate.replaceAll("#\\{", "|=|");
        String process = FreeMakerParser.process(s, stringObjectHashMap);

        String s1 = process.replaceAll("\\|=\\|", "#\\{");
        //处理in语句

//        String s2 = SqlTemplateUtils.handlerSqlIn(s1);

        return s1;
    }

    public static void main(String[] args) {
        String s = "classpath*:sqltemplates/**/*.xml";
        System.out.println(s.split(":")[1]);
    }
}
