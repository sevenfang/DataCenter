package com.chezhibao.bigdata.template.utils;

import com.chezhibao.bigdata.common.utils.BigdataUtils;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

import java.util.List;

public class BigdataHash implements TemplateMethodModel
{

	@Override
	@SuppressWarnings("rawtypes")
	public Object exec(List args) throws TemplateModelException
	{
		String paramValue = args.get(0).toString();
		if (paramValue != null)
		{
			return BigdataUtils.HashAlgorithm.KETAMA_HASH.hash(BigdataUtils.computeMd5(paramValue), 0);
		}
		return 0;
	}
}