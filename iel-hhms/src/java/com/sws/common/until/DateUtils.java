package com.sws.common.until;import java.text.ParseException;import java.text.SimpleDateFormat;import java.util.Calendar;import java.util.Date;import java.util.GregorianCalendar;public final class DateUtils {	private static final int[] DAY_OF_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31,			30, 31, 30, 31 };	public static Date addMouth(Date date, int mouthAmount) {		if (date == null) {			return null;		}		Calendar calendar = Calendar.getInstance();		calendar.setTime(date);		calendar.add(2, mouthAmount);		return calendar.getTime();	}	public static Date addDay(Date date, int dayAmount) {		if (date == null) {			return null;		}		Calendar calendar = Calendar.getInstance();		calendar.setTime(date);		calendar.add(5, dayAmount);		return calendar.getTime();	}	public static Date addHour(Date date, int hourAmount) {		if (date == null) {			return null;		}		Calendar calendar = Calendar.getInstance();		calendar.setTime(date);		calendar.add(10, hourAmount);		return calendar.getTime();	}	public static Date addMinute(Date date, int minuteAmount) {		if (date == null) {			return null;		}		Calendar calendar = Calendar.getInstance();		calendar.setTime(date);		calendar.add(12, minuteAmount);		return calendar.getTime();	}	public static int compareHourAndMinute(Date date, Date anotherDate) {		if (date == null) {			date = new Date();		}		if (anotherDate == null) {			anotherDate = new Date();		}		Calendar cal = Calendar.getInstance();		cal.setTime(date);		int hourOfDay1 = cal.get(11);		int minute1 = cal.get(12);		cal.setTime(anotherDate);		int hourOfDay2 = cal.get(11);		int minute2 = cal.get(12);		if (hourOfDay1 > hourOfDay2)			return 1;		if (hourOfDay1 == hourOfDay2) {			return ((minute1 == minute2) ? 0 : (minute1 > minute2) ? 1 : -1);		}		return -1;	}	public static long minOfTwo( Date date){		Calendar dateOne=Calendar.getInstance(),dateTwo=Calendar.getInstance();		dateOne.setTime(new Date()); //设置为当前系统时间 		dateOne.setTime(date);		long timeOne=dateOne.getTimeInMillis();		long timeTwo=dateTwo.getTimeInMillis();		long minute=(timeOne-timeTwo)/(1000*60);//转化minute		return minute;			}	public static long secOfTwo( Date date1,Date date2){        Calendar dateOne=Calendar.getInstance(),dateTwo=Calendar.getInstance();        dateOne.setTime(date1); //设置为当前系统时间         dateTwo.setTime(date2);        long timeOne=dateOne.getTimeInMillis();        long timeTwo=dateTwo.getTimeInMillis();        long minute=(timeOne-timeTwo)/(1000*60*60);//转化秒        return Math.abs(minute);            }	public static int compareIgnoreSecond(Date date, Date anotherDate) {		if (date == null) {			date = new Date();		}		if (anotherDate == null) {			anotherDate = new Date();		}		Calendar cal = Calendar.getInstance();		cal.setTime(date);		cal.set(13, 0);		cal.set(14, 0);		date = cal.getTime();		cal.setTime(anotherDate);		cal.set(13, 0);		cal.set(14, 0);		anotherDate = cal.getTime();		return date.compareTo(anotherDate);	}		public static int compareByYMD(Date date, Date anotherDate) {		if (date == null) {			date = new Date();		}		if (anotherDate == null) {			anotherDate = new Date();		}		return date2StrByYMD(date).compareTo(date2StrByYMD(anotherDate));	}	public static String currentDate2String() {		return date2StrByYMDHMS(new Date());	}	public static String currentDate2StrByYMD() {		return date2StrByYMD(new Date());	}	public static Date currentEndDate() {		return getEndDate(new Date());	}	public static Date currentStartDate() {		return getStartDate(new Date());	}	public static String date2String(Date date) {		return date2String(date, "yyyy-MM-dd HH:mm:ss.SSS");	}	public static String date2String(Date date, String pattern) {		if (date == null)			return null;		return new SimpleDateFormat(pattern).format(date);	}	public static String date2StrByY(Date date) {		return date2String(date, "yyyy");	}	public static String date2StrByYM(Date date) {		return date2String(date, "yyyy-MM");	}	public static String date2StrByYMD(Date date) {		return date2String(date, "yyyy-MM-dd");	}	public static String date2StrByMD(Date date) {		return date2String(date, "MM-dd");	}	public static String date2StrByMM(Date date) {		return date2String(date, "MM");	}	public static String date2StrByDD(Date date) {		return date2String(date, "dd");	}	public static String date2StrByHM(Date date) {		return date2String(date, "HH:mm");	}	public static String date2StrByYMDHM(Date date) {		return date2String(date, "yyyy-MM-dd HH:mm");	}	public static String date2StringByHMS(Date date) {		return date2String(date, " HH:mm:ss");	}	public static String date2StrByYMDHMS(Date date) {		return date2String(date, "yyyy-MM-dd HH:mm:ss");	}	public static String getChineseWeekNumber(String englishWeekName) {		if ("monday".equalsIgnoreCase(englishWeekName)) {			return "一";		}		if ("tuesday".equalsIgnoreCase(englishWeekName)) {			return "二";		}		if ("wednesday".equalsIgnoreCase(englishWeekName)) {			return "三";		}		if ("thursday".equalsIgnoreCase(englishWeekName)) {			return "四";		}		if ("friday".equalsIgnoreCase(englishWeekName)) {			return "五";		}		if ("saturday".equalsIgnoreCase(englishWeekName)) {			return "六";		}		if ("sunday".equalsIgnoreCase(englishWeekName)) {			return "日";		}		return null;	}	public static Date getDate(int year, int month, int date) {		return getDate(year, month, date, 0, 0);	}	public static Date getDate(int year, int month, int date, int hourOfDay,			int minute) {		return getDate(year, month, date, hourOfDay, minute, 0);	}	public static Date getDate(int year, int month, int date, int hourOfDay,			int minute, int second) {		Calendar cal = Calendar.getInstance();		cal.set(year, month - 1, date, hourOfDay, minute, second);		cal.set(14, 0);		return cal.getTime();	}	public static Integer getDayOfWeek(Date date) {		Calendar cal = Calendar.getInstance();		cal.setTime(date);		return cal.get(7);	}	public static Date getEndDate(Date date) {		if (date == null) {			return null;		}		Calendar cal = Calendar.getInstance();		cal.setTime(date);		cal.set(11, 23);		cal.set(12, 59);		cal.set(13, 59);		cal.set(14, 999);		return cal.getTime();	}	public static int getMaxDayOfMonth(int year, int month) {		if ((month == 1) && (isLeapYear(year)))			return 29;		return DAY_OF_MONTH[month];	}	public static Date getNextDay(Date date) {		return addDay(date, 1);	}	public static Date getStartDate(Date date) {		if (date == null) {			return null;		}		Calendar cal = Calendar.getInstance();		cal.setTime(date);		cal.set(11, 0);		cal.set(12, 0);		cal.set(13, 0);		cal.set(14, 0);		return cal.getTime();	}	public static String getTime(Date date) {		if (date == null) {			return null;		}		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");		return format.format(date);	}	public static String getTimeIgnoreSecond(Date date) {		if (date == null) {			return null;		}		SimpleDateFormat format = new SimpleDateFormat("HH:mm");		return format.format(date);	}	public static boolean isLeapYear(int year) {		Calendar calendar = Calendar.getInstance();		return ((GregorianCalendar) calendar).isLeapYear(year);	}		public static Date str2DateByYMDHMS(String str) {		return str2Date(str, "yyyy-MM-dd HH:mm:ss");	}	public static Date str2DateByYMD(String str) {		return str2Date(str, "yyyy-MM-dd");	}	public static Date str2DateByYM(String str) {		return str2Date(str, "yyyy-MM");	}	public static Date str2Date(String str, String pattern) {		if (null == str || "".equals(str)) {			return null;		}		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);		Date date = null;		try {			date = dateFormat.parse(str);		} catch (ParseException localParseException) {		}		return date;	}	public static int getWeekOfYear(Date date) {		Calendar cal = Calendar.getInstance();		cal.setTime(date);		return cal.get(3);	}	public static int get(Date date, int type) {		Calendar calendar = Calendar.getInstance();		calendar.setTime(date);		return calendar.get(type);	}	public static int getYear(Date date) {		return get(date, Calendar.YEAR);	}	public static int getMonth(Date date) {		return get(date, Calendar.MONTH);	}	public static int getDay(Date date) {		return get(date, Calendar.DAY_OF_MONTH);	}	public static int getHour(Date date) {		return get(date, Calendar.HOUR_OF_DAY);	}	public static int getMin(Date date) {		return get(date, Calendar.MINUTE);	}	public static int getSecond(Date date) {		return get(date, Calendar.SECOND);	}	public static Date getStartMonth(Date date) {		Calendar calendar = Calendar.getInstance();		calendar.set(getYear(date), getMonth(date), 1);		return getStartDate(calendar.getTime());	}	public static Date getEndMonth(Date date) {		Calendar calendar = Calendar.getInstance();		calendar.set(getYear(date), getMonth(date), getMaxDayOfMonth(getYear(date), getMonth(date)));		return getEndDate(calendar.getTime());	}	public static Date string2DateYM(String str) {		return str2Date(str, "yyyy-MM");	}	public static void main(String[] args) {				/*Date date = new Date();		System.out.println(getYear(date));		System.out.println(getMonth(date));		System.out.println(getDay(date));*/				//System.out.println(compareIgnoreSecond(new Date(), str2Date("2015-10-10")));			}	//一天的起止时间	public static String getStartDay(int year,int month,int day){		Calendar calendar = Calendar.getInstance();		calendar.set(year, month, day);		return date2StrByYMDHMS(getStartDate(calendar.getTime()));	}	public static String getEndDay(int year,int month,int day){		Calendar calendar = Calendar.getInstance();		calendar.set(year, month, day);		return date2StrByYMDHMS(getEndDate(calendar.getTime()));	}	//一月的起止时间	public static Date getStartMonth(int year, int month) {		Calendar calendar = Calendar.getInstance();		calendar.set(year, month, 1);		return getStartDate(calendar.getTime());	}	public static Date getEndMonth(int year, int month) {		Calendar calendar = Calendar.getInstance();		calendar.set(year, month, getMaxDayOfMonth(year, month));		return getEndDate(calendar.getTime());	}	//一年的起止时间	public static Date getStartYear(int year) {		Calendar calendar = Calendar.getInstance();		calendar.set(year, 0, 1);		return getStartDate(calendar.getTime());	}	public static Date getEndYear(int year) {		Calendar calendar = Calendar.getInstance();		calendar.set(year, 11, getMaxDayOfMonth(year, 11));		return getEndDate(calendar.getTime());	}	/**	 * 取得当前日期所在周的第一天	 */	public static Date getStartWeek(Date date) {		Calendar c = Calendar.getInstance();		c.setFirstDayOfWeek(Calendar.MONDAY);		c.setTime(date);		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // 星期一		return c.getTime();	}	/**	 * 取得当前日期所在周的最后一天	 */	public static Date getEndWeek(Date date) {		Calendar c = Calendar.getInstance();		c.setFirstDayOfWeek(Calendar.MONDAY);		c.setTime(date);		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // 星期天		return getEndDate(c.getTime());	}	public static Date getWeek2(Date date) {		Calendar c = Calendar.getInstance();		c.setFirstDayOfWeek(Calendar.MONDAY);		c.setTime(date);		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()+1); // 星期二		return c.getTime();	}	public static Date getWeek3(Date date) {		Calendar c = Calendar.getInstance();		c.setFirstDayOfWeek(Calendar.MONDAY);		c.setTime(date);		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()+2); // 星期三		return c.getTime();	}	public static Date getWeek4(Date date) {		Calendar c = Calendar.getInstance();		c.setFirstDayOfWeek(Calendar.MONDAY);		c.setTime(date);		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()+3); // 星期四		return c.getTime();	}	public static Date getWeek5(Date date) {		Calendar c = Calendar.getInstance();		c.setFirstDayOfWeek(Calendar.MONDAY);		c.setTime(date);		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()+4); // 星期五		return c.getTime();	}	public static Date getWeek6(Date date) {		Calendar c = Calendar.getInstance();		c.setFirstDayOfWeek(Calendar.MONDAY);		c.setTime(date);		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()+5); // 星期六		return c.getTime();	}		public static String getChineseWordByCount(int i) {		if(1 == i) {			return "一";		}		if(2 == i) {			return "二";		}		if(3 == i) {			return "三";		}		if(4 == i) {			return "四";		}		if(5 == i) {			return "五";		}		if(6 == i) {			return "六";		}		if(7 == i) {			return "七";		}		if(8 == i) {			return "八";		}		if(9 == i) {			return "九";		}		if(10 == i) {			return "十";		}		if(11 == i) {			return "十一";		}		if(12 == i) {			return "十二";		}		return null;	}/*	public static String getStartTime(){			}*/	public static int getMonthSpace(Date startDate,Date endDate){		int result=0,year=0,month=0;        year=getYear(endDate)-getYear(startDate);        month=getMonth(endDate)-getMonth(startDate);        result=year*(12+1)+month+1;        return result;    }	/*     * 将时间戳转换为时间    */   public static String stampToDate(String s){       String res;       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       long lt = new Long(s);       Date date = new Date(lt);       res = simpleDateFormat.format(date);       return res;   }      /**	 * 时间戳转换工具，将date类型的数据转换成String，并将时分秒改为0	 * @param date	 * @return	 * @author wwg	 */	public static String dateToString(Date date){       String res;       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");       res = simpleDateFormat.format(date);       return res;   }		/**	 * 计算两个Date类型之间的天数	 * @param date1	 * @param date2	 * @return	 * @author wwg	 */	public static int daysOfTwo(Date date1, Date date2) {	       Calendar aCalendar = Calendar.getInstance();	       aCalendar.setTime(date1);	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);	       aCalendar.setTime(date2);	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);	       return Math.abs(day2 - day1);	}		public static long daysOfTwoDate( Date date1,Date date2){        Calendar dateOne=Calendar.getInstance(),dateTwo=Calendar.getInstance();        dateOne.setTime(date1); //设置为当前系统时间         dateTwo.setTime(date2);        long timeOne=dateOne.getTimeInMillis();        long timeTwo=dateTwo.getTimeInMillis();        long days=(timeOne-timeTwo)/(1000*60*60*24);//转化天        return days;            }	}