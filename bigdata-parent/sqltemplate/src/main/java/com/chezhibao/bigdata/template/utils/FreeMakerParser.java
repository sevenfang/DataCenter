package com.chezhibao.bigdata.template.utils;

import com.chezhibao.bigdata.template.exception.SqlTemplateException;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WangCongJun
 * @date 2018/4/26
 * Created by WangCongJun on 2018/4/26.
 */
public class FreeMakerParser
{
    private static final String				DEFAULT_TEMPLATE_KEY		= "default_template_key";
    private static final String				DEFAULT_TEMPLATE_EXPRESSION	= "default_template_expression";
    private static final Configuration CONFIGURER					= new Configuration();
    static
    {
        CONFIGURER.setClassicCompatible(true);
        CONFIGURER.setSharedVariable("hash", new BigdataHash());
    }
    /**
     * 配置SQL表达式缓存
     */
    private static Map<String, Template> templateCache				= new HashMap<String, Template>();

    /**
     * 分库表达式缓存
     */
    private static Map<String, Template>	expressionCache				= new HashMap<String, Template>();

    /**
     *
     * 处理模型中涉及的sql语句 <br>
     * 〈功能详细描述〉
     *
     * @param sql
     *            sql语句
     * @param root
     *            封装数据
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String process(String sql, Map<String, Object> root)
    {
        StringReader reader = null;
        StringWriter out = null;
        Template template = null;
        try
        {
            template = createTemplate(DEFAULT_TEMPLATE_EXPRESSION, new StringReader(sql));
            out = new StringWriter();
            template.process(root, out);
            return out.toString();
        }
        catch (Exception e)
        {
            throw new SqlTemplateException(e);
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                return null;
            }
        }
    }

    private static Template createTemplate(String templateKey, StringReader reader) throws IOException
    {
        Template template = new Template(templateKey, reader, CONFIGURER);
        template.setNumberFormat("#");
        return template;
    }

    /**
     *
     * 处理sqlmap中的sql <br>
     * 〈功能详细描述〉
     *
     * @param root
     *            封装数据
     * @param sql
     *            sql【模版】内容
     * @param sqlId
     *            sqlID
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String process(Map<String, Object> root, String sql, String sqlId)
    {
        StringReader reader = null;
        StringWriter out = null;
        Template template = null;
        try
        {
            if (templateCache.get(sqlId) != null)
            {
                template = templateCache.get(sqlId);
            }
            if (template == null)
            {
                reader = new StringReader(sql);
                template = createTemplate(DEFAULT_TEMPLATE_KEY, reader);
                templateCache.put(sqlId, template);
            }
            out = new StringWriter();
            template.process(root, out);
            return out.toString();
        }
        catch (Exception e)
        {
            throw new SqlTemplateException(e);
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                return null;
            }
        }
    }
}

