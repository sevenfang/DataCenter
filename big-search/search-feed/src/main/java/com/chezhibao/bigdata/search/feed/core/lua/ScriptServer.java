package com.chezhibao.bigdata.search.feed.core.lua;

import com.chezhibao.bigdata.template.enums.SqlTemplateEnum;
import com.chezhibao.bigdata.template.exception.SqlTemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chezhibao.bigdata.search.common.SearchLogUtils.FEED_LOG;

/**
 * @author WangCongJun
 * Created by WangCongJun on 2019/1/21.
 */
@Service
public class ScriptServer {
    private static final String LUA_SCRIPT_PATH = "classpath*:lua/**/*.lua";


    private Map<String, String> luaScriptContainer;

    public ScriptServer() throws Exception {
        this.luaScriptContainer = ScriptUtils.luaScriptBOMap(LUA_SCRIPT_PATH);
    }

    public String getLuaScriptByName(String name){
        return luaScriptContainer.get(name);
    }


}

class ScriptUtils{
    public static Map<String, String> luaScriptBOMap(String scriptLocation) throws Exception {
        Map<String,String> luaScriptContainer = new HashMap<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(scriptLocation);
            for (Resource resource : resources) {
                String path =resource.getURI().toString();
                //处理window和Linux系统路径问题
                path=path.replaceAll("/","\\\\");
                FEED_LOG.debug("【实时推荐】lua脚本文件路径：{}", path);
                //将文件后缀名去掉，并将路径改成xx.xx.xx
                String filename = getFolderPathName(scriptLocation,path);
                FEED_LOG.debug("【实时推荐】lua脚本转换后的路径：{}", filename);
                InputStream inputStream = resource.getInputStream();
                List<String> strings = IOUtils.readLines(inputStream, "UTF-8");
                StringBuilder builder = new StringBuilder();
                for(String s : strings){
                    builder.append(s).append("\n");
                }
                luaScriptContainer.put(filename, builder.toString());
            }
        }catch (IOException e){
            FEED_LOG.error("【sql模板】",e);
            throw  new SqlTemplateException(SqlTemplateEnum.READ_SQL_TEMPLATE_ERROR);
        }
        return luaScriptContainer;
    }

    /**
     * 转换路径 成test.test
     *
     * @param locationPattern classpath:lua\**\.lua
     * @param rawPath         C:\Users\czb123\IdeaProjects\CZB\big-parent\whtc\target\classes\lua\test\lua.xml
     * @return
     */
    private static String getFolderPathName(String locationPattern, String rawPath) {
        String replace = locationPattern.split(":")[1];
        String folderName = replace.split("/")[0];
        String path = rawPath.split(folderName)[1].substring(1).split("\\.")[0];
        FEED_LOG.debug("【实时推荐】转换前的路径：{}", path);
        String[] split = path.split("\\\\");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            //防止最后出现一个.
            if (i == split.length - 1) {
                builder.append(split[i]);
            } else {
                builder.append(split[i]).append(".");
            }
        }
        return builder.toString();
    }
}
