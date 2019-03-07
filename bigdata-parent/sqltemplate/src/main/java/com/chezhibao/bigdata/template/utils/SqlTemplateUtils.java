package com.chezhibao.bigdata.template.utils;

import com.chezhibao.bigdata.template.enums.SqlTemplateEnum;
import com.chezhibao.bigdata.template.exception.SqlTemplateException;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
@Slf4j
public class SqlTemplateUtils {

    private final static Pattern inPattern = Pattern.compile("\\(#\\{(.+)\\}\\)");

    /**
     * 获取sqlContainer
     * @param locationPattern 支持ant风格
     * @return
     * @throws Exception
     */
    public static Map<String,Map<String,String>> getSqlContainer(String locationPattern) {
        Map<String,Map<String,String>> sqlContainer = new HashMap<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(locationPattern);
            for (Resource resource : resources) {
                String path =resource.getURI().toString();
                //处理window和Linux系统路径问题
                path=path.replaceAll("/","\\\\");
                log.debug("【sql模板处理】模板文件路径：{}", path);
                //将文件后缀名去掉，并将路径改成xx.xx.xx
                String filename = getFolderPathName(locationPattern,path);
                log.debug("【sql模板处理】转换后的路径：{}", filename);
                InputStream inputStream = resource.getInputStream();
                sqlContainer.put(filename, xmlString2Map(inputStream));
            }
        }catch (IOException e){
            log.error("【sql模板】",e);
            throw  new SqlTemplateException(SqlTemplateEnum.READ_SQL_TEMPLATE_ERROR);
        }
        return sqlContainer;
    }

    /**
     * 转换路径 成test.test
     * @param locationPattern classpath:sqltemplates\**\.xml
     * @param rawPath C:\Users\czb123\IdeaProjects\CZB\big-parent\whtc\target\classes\sqltemplates\test\test.xml
     * @return
     */
    private static String getFolderPathName(String locationPattern,String rawPath){
//        String replace = locationPattern.replace("classpath*:", "");
        String replace = locationPattern.split(":")[1];
        String folderName = replace.split("/")[0];
        String path = rawPath.split(folderName)[1].substring(1).split("\\.")[0];
        log.debug("【sql模板处理】转换前的路径：{}", path);
        String[] split = path.split("\\\\");
        StringBuilder builder = new StringBuilder();
        for (int i =0;i<split.length;i++){
            //防止最后出现一个.
            if(i==split.length-1){
                builder.append(split[i]);
            }else{
                builder.append(split[i]).append(".");
            }
        }
        return builder.toString();
    }

    /**
     * Xml string转换成Map
     *
     * @param inputStream
     * @return
     */
    private static Map<String, String> xmlString2Map(InputStream inputStream) {
        Map<String, String> map = new HashMap<>();
        // 创建saxReader对象
        SAXReader reader = new SAXReader();
        // 通过read方法读取一个文件 转换成Document对象
        Document document;
        try {
            document = reader.read(inputStream);
            //获取根节点元素对象
            Element node = document.getRootElement();
            recGetXmlElementValue(node, map);
        } catch (DocumentException e) {
            log.error("sql模板转换出错了",e);
        }
        return map;
    }

    /**
     * 循环解析xml
     *
     * @param ele
     * @param map
     * @return
     */
    @SuppressWarnings({"unchecked"})
    private static void recGetXmlElementValue(Element ele, Map<String, String> map) {
        List<Element> eleList = ele.elements();
        if (eleList.size() == 0) {
            map.put(ele.attributeValue("id"), ele.getTextTrim());
        } else {
            for (Iterator<Element> iter = eleList.iterator(); iter.hasNext(); ) {
                Element innerEle = iter.next();
                recGetXmlElementValue(innerEle, map);
            }
        }
    }



    public static String handlerSqlIn(String script){
        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            script = matcher.replaceAll("(<foreach collection=\"$1\" item=\"__item\" separator=\",\" >#{__item}</foreach>)");
        }

        script = "<script>" + script + "</script>";
        return script;

    }
}
