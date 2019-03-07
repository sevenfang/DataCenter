package com.chezhibao.bigdata.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日期工具类
 */
@Slf4j
public class DateUtils
{

	public final static String	yyyy					= "yyyy";

	public final static String	MM						= "MM";

	public final static String	dd						= "dd";

	public final static String	yyyy_MM_dd				= "yyyy-MM-dd";
	
	public final static String  yyyy_MM_dd_CH           = "yyyy年MM月dd日";

	public final static String	yyyyMMdd				= "yyyyMMdd";

	public final static String	yyyy_MM					= "yyyy-MM";

	public final static String	yyyyMM					= "yyyyMM";

	public final static String	HH_mm_ss				= "HH:mm:ss";

	public final static String	HH_mm					= "HH:mm";

	public final static String	yyyy_MM_dd_HH_mm_ss		= "yyyy-MM-dd HH:mm:ss";

	public final static String	yyyyMMdd_HH_mm_ss		= "yyyyMMdd HH:mm:ss";

	public final static String	yyyyMMddHHmmss			= "yyyyMMddHHmmss";

	public final static String	yyyy_MM_dd_HH_mm		= "yyyy-MM-dd HH:mm";

	public final static String	yyyyMMddHHmm			= "yyyyMMddHHmm";

	public final static String	yyyy_MM_dd_HH_mm_ss_SSS	= "yyyy-MM-dd HH:mm:ss.SSS";

	public final static String	yyyyMMddHHmmssSSS		= "yyyyMMddHHmmssSSS";

	/**
	 * 默认为yyyy-MM-dd的格式化
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date)
	{
		return format(date, yyyy_MM_dd);
	}
	
	/**
	 * 得到yyyy年MM月dd日的格式化字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatCH(Date date)
	{
		return format(date, yyyy_MM_dd_CH);
	}
	

	/**
	 * 获取一个简单的日期格式化对象
	 * 
	 * @return 一个简单的日期格式化对象
	 */
	private static SimpleDateFormat getFormatter(String parttern)
	{
		return new SimpleDateFormat(parttern);
	}

	/**
	 * 日期格式化－将<code>Date</code>类型的日期格式化为<code>String</code>型
	 * 
	 * @param date
	 *            待格式化的日期
	 * @param pattern
	 *            时间样式
	 * @return 一个被格式化了的<code>String</code>日期
	 */
	public static String format(Date date, String pattern)
	{
		if (date == null)
		{
			return "";
		}
		else
		{
			return getFormatter(pattern).format(date);
		}
	}

	/**
	 * 默认为yyyy-MM-dd格式的解析
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date parse(String strDate)
	{
		return parse(strDate, yyyy_MM_dd);
	}

	/**
	 * 日期解析－将<code>String</code>类型的日期解析为<code>Date</code>型
	 * 
	 * @param strDate
	 *            待解析的日期字符串
	 * @param pattern
	 *            日期格式
	 * @ExceptionHandler ParseException 如果所给的字符串不能被解析成一个日期
	 * @return 一个被格式化了的<code>Date</code>日期
	 */
	public static Date parse(String strDate, String pattern)
	{
		try
		{
			return getFormatter(pattern).parse(strDate);
		}
		catch (ParseException e)
		{
			log.error("日期转换出错了",e);
		}
		return null;
	}

	/**
	 * 获取日期(java.util.Date)
	 * 
	 * @return
	 */
	public static Date getCurrDate()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * 
	 * 功能描述: <br>
	 * 获取当天，从0点起算
	 * 
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static Date getCurrDay()
	{
		Date currentDate = getCurrDate();
		return parse(format(currentDate));
	}

	/**
	 * 获取指定毫秒数所表示的日期
	 * 
	 * @param millis
	 *            millis the new time in UTC milliseconds from the epoch.
	 * @return
	 */
	public static Date getDate(long millis)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/**
	 * 
	 * 获取间隔日期 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param date
	 *            基准日期
	 * @param field
	 *            指定日期字段
	 * @param intervals
	 *            间隔数
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static Date getDate(Date date, int field, int intervals)
	{
		try
		{
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			if (date != null)
			{
				calendar.setTime(date);
			}
			calendar.set(field, calendar.get(field) + intervals);
			return calendar.getTime();
		}
		catch (Exception e)
		{
			log.error("日期转换出错了",e);
		}
		return null;
	}

	/**
	 * 获取当前日期字符串
	 * 
	 * @return 一个包含年月日<code>String</code>型日期，yyyyMMdd
	 */
	public static String getCurrDateStr()
	{
		return format(getCurrDate(), yyyy_MM_dd);
	}

	/**
	 * 获取当前日期时间字符串，格式: yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 一个包含年月日时分秒的<code>String</code>型日期时间字符串，格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrDateTimeStr()
	{
		return format(getCurrDate(), yyyy_MM_dd_HH_mm_ss);
	}

	public static String getCurrDateTimeMillsStr()
	{
		return format(getCurrDate(), yyyyMMddHHmmssSSS);
	}

	/**
	 * 获取指定毫秒数所表示的日期时间字符串，样式: yyyy-MM-dd HH:mm:ss
	 * 
	 * @param millis
	 *            millis the new time in UTC milliseconds from the epoch.
	 * @return 一个包含年月日时分秒的<code>String</code>型日期时间字符串，格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTimeStr(long millis)
	{
		return format(getDate(millis), yyyy_MM_dd_HH_mm_ss);
	}

	/**
	 * 获取当前年分 样式：yyyy
	 * 
	 * @return 当前年分
	 */
	public static String getYear()
	{
		return format(getCurrDate(), yyyy);
	}

	/**
	 * 获取当前月分 样式：MM
	 * 
	 * @return 当前月分
	 */
	public static String getMonth()
	{
		return format(getCurrDate(), MM);
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 *            基准日期
	 * @param intervals
	 *            间隔月数
	 * @return
	 */
	public static Date getMonth(Date date, int intervals)
	{
		try
		{
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + intervals);
			return calendar.getTime();
		}
		catch (Exception e)
		{
			log.error("日期转换出错了",e);
		}
		return null;
	}

	/**
	 * 获取当前日期号 样式：dd
	 * 
	 * @return 当前日期号
	 */
	public static String getDay()
	{
		return format(getCurrDate(), dd);
	}

	/**
	 * 获取日期
	 * 
	 * @param date
	 *            基准日期
	 * @param intervals
	 *            相隔天数
	 * @return
	 */
	public static Date getDay(Date date, int intervals)
	{
		try
		{
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + intervals);
			return calendar.getTime();
		}
		catch (Exception e)
		{
			log.error("日期转换出错了",e);
		}
		return null;
	}

	/**
	 * 判断当前时间是一年中的第几天
	 * 
	 * @return
	 */
	public static int getDayOfYear()
	{
		try
		{
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(new Date());
			return calendar.get(Calendar.DAY_OF_YEAR);
		}
		catch (Exception e)
		{
			log.error("日期转换出错了",e);
		}
		return 0;
	}

	/**
	 * 判断当前时间是一天中的第几个小时
	 * 
	 * @return
	 */
	public static int getHourOfday()
	{
		try
		{
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			calendar.setTime(new Date());
			return calendar.get(Calendar.HOUR_OF_DAY);
		}
		catch (Exception e)
		{
			log.error("日期转换出错了",e);
		}
		return 0;
	}

	/**
	 * 获取两个日期相差天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getIntevalDays(String startDate, String endDate)
	{
		try
		{
			return getIntevalDays(parse(startDate, yyyy_MM_dd), parse(endDate, yyyy_MM_dd));
		}
		catch (Exception e)
		{
			log.error("日期转换出错了",e);
		}
		return 0L;
	}

	/**
	 * 得到两个时间相差的天数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static long getIntevalDays(Date startDate, Date endDate)
	{
		try
		{
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();

			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			long diff = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

			return (diff / (1000 * 60 * 60 * 24));
		}
		catch (Exception e)
		{
			log.error("日期转换出错了",e);
		}
		return 0L;
	}

	/**
	 * 得到两个时间之间时间数组包括起始时间
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static String[] getBetweenDays(Date startDate, Date endDate)
	{
		String x[] = new String[(int) getIntevalDays(startDate, endDate) + 1];
		if (x.length > 0)
		{
			Date tem = startDate;
			x[0] = format(startDate);
			for (int i = 1; i < x.length; i++)
			{
				tem = getNextDay(tem);
				x[i] = format(tem);
			}
		}
		return x;
	}

	/**
	 * 得到两个时间相差的年数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static int getIntevalYears(Date startDate, Date endDate)
	{
		if (startDate == null || endDate == null)
		{
			return -1;
		}

		Date start = startDate;
		Date end = endDate;
		if (start.after(end))
		{
			start = endDate;
			end = startDate;
		}

		int intevals = 0;
		while (start.before(end))
		{
			intevals++;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(start);
			calendar.add(Calendar.YEAR, 1);
			start = calendar.getTime();
		}
		return intevals;
	}

	/**
	 * 得到两个时间相差的小时数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static double getIntevalHours(Date startDate, Date endDate)
	{
		try
		{
			java.util.Calendar startCalendar = java.util.Calendar.getInstance();
			java.util.Calendar endCalendar = java.util.Calendar.getInstance();

			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			long diff = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

			return ((double) diff / (1000 * 60 * 60));
		}
		catch (Exception ee)
		{
			return 0.0;
		}
	}

	/**
	 * 得到两个时间相差的分钟数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 */
	public static long getIntevalMinutes(Date startDate, Date endDate)
	{
		try
		{
			java.util.Calendar startCalendar = java.util.Calendar.getInstance();
			java.util.Calendar endCalendar = java.util.Calendar.getInstance();

			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			long diff = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

			return diff / (1000 * 60);
		}
		catch (Exception ee)
		{
			return 0;
		}
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getMilliseconds(Date startDate, Date endDate)
	{
		try
		{
			java.util.Calendar startCalendar = java.util.Calendar.getInstance();
			java.util.Calendar endCalendar = java.util.Calendar.getInstance();

			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			long diff = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();

			return diff;
		}
		catch (Exception ee)
		{
			return 0;
		}
	}

	/**
	 * 获取当前日期所在月的第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfMonth()
	{
		return getFirstDayOfMonth(getCurrDate());
	}

	/**
	 * 获取指定日期所在月的第一天
	 * 
	 * @param current
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date)
	{
		try
		{
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.DATE, 1);// 设为当前月的第一天
			return c.getTime();
		}
		catch (Exception e)
		{
			log.error("日期转换出错了",e);
		}
		return null;
	}

	/**
	 * 获取当前日期所在月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfMonth()
	{
		return getLastDayOfMonth(getCurrDate());
	}

	/**
	 * 获取指定日期所在月的最后一天
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date)
	{
		try
		{
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);// 当前月加1变为下一个月
			c.set(Calendar.DATE, 1);// 设为下一个月的第一天
			c.add(Calendar.DATE, -1);// 减一天，变为本月最后一天
			return c.getTime();
		}
		catch (Exception e)
		{
			log.error("日期转换出错了",e);
		}
		return null;
	}

	/**
	 * 判断指定时间是否过期
	 * 
	 * @param datetime
	 *            日期时间字符串
	 * @param parttern
	 *            日期时间格式
	 * @return
	 */
	public static boolean isOverdue(String datetime, String parttern)
	{
		return DateUtils.parse(datetime, parttern).before(DateUtils.getCurrDate());
	}

	/**
	 * 以友好的方式显示过去的时间
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String friendlyTime(Date date)
	{
		// 计算时间差，单位：秒
		int ct = (int) ((System.currentTimeMillis() - date.getTime()) / 1000);
		if (ct < 3600)
		{
			return String.format("%d 分钟之前", ct / 60);
		}
		if (ct >= 3600 && ct < 86400)
		{
			return String.format("%d 小时之前", ct / 3600);
		}
		if (ct >= 86400 && ct < 2592000)
		{ // 86400 * 30
			int day = ct / 86400;
			if (day > 1)
			{
				return String.format("%d 天之前", day);
			}
			else
			{
				return "昨天";
			}
		}
		if (ct >= 2592000 && ct < 31104000)
		{ // 86400 * 30
			return String.format("%d 月之前", ct / 2592000);
		}

		return String.format("%d 年之前", ct / 31104000);

	}

	/**
	 * 
	 * 获取当前时间戳 <br>
	 * 〈功能详细描述〉
	 * 
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static Timestamp getCurrTimeStamp()
	{
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 
	 * 返回日期对应的Timestamp <br>
	 * 〈功能详细描述〉
	 * 
	 * @param dateTime
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static Timestamp getTimeStamp(Date dateTime)
	{
		return new Timestamp(dateTime.getTime());
	}

	/**
	 * 
	 * 日期调整 <br>
	 * 〈功能详细描述〉
	 * 
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	public static Date add(Date date, int field, int amount)
	{
		try
		{
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(field, amount);
			return c.getTime();
		}
		catch (Exception e)
		{
			log.error("日期转换出错了",e);
		}
		return null;
	}

	/**
	 * 获取指定日期下一天
	 */
	public static Date getNextDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		date = calendar.getTime();
		return date;
	}

	/**
	 * 获取来个月份之间相差月份
	 * 
	 * @param startDate
	 *            2014-02
	 * @param endDate
	 *            2016-08
	 * @return
	 */
	public static int getIntevalMonth(String startDate, String endDate)
	{
		String s[] = startDate.split("-");
		String e[] = endDate.split("-");
		int xc = 0;
		if (!s[0].trim().equals(e[0].trim()))
		{
			xc = Integer.parseInt(e[0].trim()) - Integer.parseInt(s[0].trim());
		}
		int size = xc * 12 + (Integer.parseInt(e[1].trim()) - Integer.parseInt(s[1].trim()));
		return size;
	}

	/**
	 * 获取来个月份之间相所有月份（包括起始）
	 * 
	 * @param startDate
	 *            2014-02
	 * @param endDate
	 *            2016-08
	 * @return
	 */
	public static String[] getBetweenMonth(String startDate, String endDate)
	{
		String s[] = startDate.split("-");
		String e[] = endDate.split("-");
		int xc = 0;
		if (!s[0].trim().equals(e[0].trim()))
		{
			xc = Integer.parseInt(e[0].trim()) - Integer.parseInt(s[0].trim());
		}
		int size = xc * 12 + (Integer.parseInt(e[1].trim()) - Integer.parseInt(s[1].trim())) + 1;
		String x[] = new String[size];
		int tem_y = Integer.parseInt(s[0].trim());
		int tem_m = Integer.parseInt(s[1].trim());
		x[0] = startDate;
		for (int i = 1; i < x.length; i++)
		{
			tem_m = tem_m + 1;
			if (tem_m > 12)
			{
				tem_y = tem_y + 1;
				tem_m = 1;
			}
			x[i] = tem_y + "-" + tem_m;
		}
		return x;
	}
}
