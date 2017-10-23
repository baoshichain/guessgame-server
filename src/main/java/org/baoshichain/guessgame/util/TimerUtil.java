package org.baoshichain.guessgame.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimerUtil {
	/**
	 * 获取当前星期几
	 * 
	 * @param
	 * @return 星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获取当前时间
	 * 
	 * @return 1437022686305
	 */
	public static long getCurrentTime() {
		return System.currentTimeMillis();
	}

	/**
	 * 获取当前时间 格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 2015-07-16 13:01:13
	 */
	public static String getCurrentTimes() {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String times = dateFormater.format(date);
		return times;
	}

	public static String getCurrentTimer2s(String time) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分");
		Date date = new Date();
		try {
			date = dateFormater.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFormater2.format(date);
	}

	public static String getCurrentTimer3(String time) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date();
		try {
			date = dateFormater.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFormater2.format(date);
	}

	public static String getCurrentTimer4(String time) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormater2 = new SimpleDateFormat("yy年MM月dd日");
		SimpleDateFormat dateFormater3 = new SimpleDateFormat("MM月dd日");
		Date date = new Date();
		try {
			date = dateFormater.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFormater3.format(date);
	}

	public static String getCurrentTimerFormat(String time) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
		Date date = new Date();
		try {
			date = dateFormater.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateFormater2.format(date);
	}

	/**
	 * 将yyyy-MM-dd HH:mm:ss格式字符串转为12345678格式
				*
	 * @param "2015-07-16 13:01:13
				* @return 1437022873000
				*/
		public static long strToDate(String str) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * 转格林时间
	 * 
	 * @param "2015-07-16 13:01:13
	 * @return Thu Jul 16 13:01:13 CST 2015
	 */
	public static Date toDate(String sdate) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormater.parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 转多少分钟之前
	 * 
	 * @param "2015-07-16 13:01:13
	 * @return 21分钟前 (注意如果手机时间不正确，是不对应的)
	 */
	public static String friendly_time(String sdate) {
		SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.format(cal.getTime());
		String paramDate = dateFormater2.format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.format(time);
		}
		return ftime;
	}

	/**
	 * 判断时间是否为今日
	 * 
	 * @param "2015-07-16 13:01:13
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.format(today);
			String timeDate = dateFormater2.format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 获取今天 月 日
	 * 
	 * @return 2015-07-16
	 */
	public static String getToday() {
		SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");
		Date time = new Date();
		String timeDate = dateFormater2.format(time);
		return timeDate;
	}

	/**
	 * 获取今天 月 日
	 * 
	 * @return 2015年9月16日
	 */
	public static String getTodays() {
		SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy年MM月dd日");
		Date time = new Date();
		String timeDate = dateFormater2.format(time);
		return timeDate;
	}

	/**
	 * 获取昨天时间
	 */
	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
		return yesterday;
	}

	/**
	 * 日期date转为格式日期
	 * 
	 * @param
	 * @return 2015-07-16 12:33:50
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	/**
	 * from: 1900 9 7 to: 毫秒数 （日期转为毫秒数）
	 */
	public static long dateTostr(int year, int month, int day) {
		java.text.DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.sql.Timestamp stime = new java.sql.Timestamp(year - 1900, month - 1, day, 0, 0, 0, 0);
		System.out.println("timestamp = " + stime);
		System.out.println("millis = " + stime.getTime());
		return stime.getTime();
	}

	// 比较两个时间大小 true begin<end false begin>end
	// 2016-03-12 2016-04-05
	public static boolean compareTime(String begin, String end) {
		String[] begintime = begin.split("-");
		String[] endtime = end.split("-");
		// 判断 年 大小
		if (Integer.parseInt(begintime[0]) > Integer.parseInt(endtime[0])) { // begin>end
			return false;
		} else if (Integer.parseInt(begintime[0]) == Integer.parseInt(endtime[0])) {
			if (Integer.parseInt(begintime[1]) > Integer.parseInt(endtime[1])) {
				return false;
			} else if (Integer.parseInt(begintime[1]) == Integer.parseInt(endtime[1])) {
				if (Integer.parseInt(begintime[2]) > Integer.parseInt(endtime[2])) {
					return false;
				} else {
					return true;
				}
			}
		}
		return true;
	}

	// 比较两个时间大小 true begin<end false begin>end
	// 2016-03-12 12:23   2016-04-05 13:45
	public static int compare_date(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if(dt1.before(dt2)) {
				System.out.println("dt1 小于 dt2");
				return 1;
			}else {
				System.out.println("dt1 大于 dt2");
				return -1;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	public static String getStandardDate(String timeStr) {

		StringBuffer sb = new StringBuffer();

		long t = Long.parseLong(timeStr);
		long time = System.currentTimeMillis() - (t * 1000);
		long mill = (long) Math.ceil(time / 1000);// 秒前

		long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

		long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

		long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

		if (day - 1 > 0) {
			sb.append(day + "天");

		} else if (hour - 1 > 0) {
			if (hour >= 24) {
				sb.append("1天");
			} else {
				sb.append(hour + "小时");
			}
		} else if (minute - 1 > 0) {
			if (minute == 60) {
				sb.append("1小时");
			} else {
				sb.append(minute + "分钟");
			}
		} else if (mill - 1 > 0) {
			if (mill == 60) {
				sb.append("1分钟");
			} else {
				sb.append(mill + "秒");
			}
		} else {
			sb.append("刚刚");
		}
		if (!sb.toString().equals("刚刚")) {
			sb.append("前");
		}
		return sb.toString();
	}

	/**
	 * 计算当前时间至结束时间
	 * 
	 * @param endTime
	 * @return
	 */
	public static String calculateSurplusTime(String endTime) {
		String surplus_time = "";
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = df.parse(endTime);
			long time = date.getTime() - System.currentTimeMillis();
			if (time > 0) {
				long days = time / (1000 * 60 * 60 * 24);
				long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
				long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
				long seconds = (time % (1000 * 60)) / 1000;
				if (days > 0) {
					surplus_time += days + "天";
				}
				if (hours > 0) {
					surplus_time += hours + "小时";
				}
				if (days <= 0 && hours <= 0) {
					if (minutes > 0) {
						surplus_time += minutes + "分钟";
					} else {
						if (seconds < 60) {
							surplus_time += seconds + "秒";
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return surplus_time;
	}

	// 比较 专场是否开始 或者是否结束 0 未开始 1 进行中 2 结束
	public static String compareTime2(String begintime, String endtime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = new Date();
			Date date1 = df.parse(begintime);
			Date date2 = df.parse(endtime);

			// if(date.compareTo(date1)>=)
			/*
			 * // 结束 if (System.currentTimeMillis() - date2.getTime()) { return
			 * "2"; } // 进行中 if ((System.currentTimeMillis() - date2.getTime())
			 * < 0 && (date1.getTime() - System.currentTimeMillis() > 0)) {
			 * return "1"; } //未开始 if ((System.currentTimeMillis() -
			 * date1.getTime()) < 0) { return "0"; }
			 */
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "2";
	}

	public static String getCompareResult(String str,String str1){
		String time;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date d1 = df.parse(str); //"2004-03-26 13:31:40"
			Date d2 = df.parse(str1); //"2004-01-02 11:30:24"
			long date = d1.getTime() - d2.getTime();
			long day = date / (1000 * 60 * 60 * 24);
			long hour = (date / (1000 * 60 * 60) - day * 24);
			long min = ((date / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (date/1000 - day*24*60*60 - hour*60*60 - min*60);
			System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
			if(day==0){
				if(hour==0){
					if(min==0){
						return s+"秒";
					}else{
						return min+"分"+s+"秒";
					}
				}else{
					return hour+"小时"+min+"分"+s+"秒";
				}
			}else{
				return day+"天"+hour+"小时"+min+"分"+s+"秒";
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
}
