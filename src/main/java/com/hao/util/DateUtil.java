package com.hao.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName DateUtil.java
 * @Description 功能描述： 日期转换
 * @author 吴昊 2018年3月6日20:02:38
 */
public class DateUtil {

    /**
     * 时间是否在配置时间之前
     * 
     * @param configDate
     * @param entryDate
     * @return
     */
    public static boolean isBefore(Date configDate, Date entryDate) {
        boolean bool = entryDate.before(configDate);
        return bool;
    }

    /**
     * 时间是否在配置时间之后
     * 
     * @param configDate
     * @param entryDate
     * @return
     */
    public static boolean isAfter(Date configDate, Date entryDate) {
        boolean bool = entryDate.after(configDate);
        return bool;
    }

    /**
     * 将字符串时间转换成Date类型
     * 
     * @param str
     * @return
     */
    public static Date convertStr2Date(String str) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(str);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    /**
     * 将字符串时间转换成Date类型
     * 
     * @param str
     * @return
     */
    public static Date convertStr3Date(String str) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(str);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    /**
     * 字符串转日期
     * 
     * @param str
     * @return
     */
    public static Date StringToDate(String str) {
        DateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formate.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 加减天数
     * 
     * @param date
     * @return Date
     */
    public static Date increaseDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        return cal.getTime();
    }

    /**
     * 加减小时
     * 
     * @param date
     * @return Date
     */
    public static Date increaseHour(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }

    /**
     * 加减分钟
     * 
     * @param date
     * @return Date
     */
    public static Date increaseMinute(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    /**
     * 加减秒
     * 
     * @param date
     * @return Date
     */
    public static Date increaseSecond(Date date, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * 获取当月最后一天
     * 
     * @param date
     * @return
     */
    public static Date lastDayByMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获取当前月份
     * 
     * @param date
     * @return
     */
    public static Integer getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * yyyy-MM-dd
     * 
     * @param date
     * @return
     */
    public static String dateTo8String(Date date) {
        if (date == null) {
            return null;
        }
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dates = sdf.format(date);
        return dates;
    }

    /**
     * yyyy年MM月dd日 HH时mm分ss秒
     * 
     * @param date
     * @return
     */
    public static String dateTo14StringCH(Date date) {
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        dates = sdf.format(date);
        return dates;
    }

    /**
     * yyyy年MM月dd日 HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static String dateTo8StringCH(Date date) {
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        dates = sdf.format(date);
        return dates;
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @return
     */
    public static String dateTo14String(Date date) {
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dates = sdf.format(date);
        return dates;
    }

    /**
     * yyyy-MM-dd HH:mm
     * 
     * @param date
     * @return
     */
    public static String dateTo12String(Date date) {
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dates = sdf.format(date);
        return dates;
    }

    /**
     * yyyyMMdd
     * 
     * @param date
     * @return
     */
    public static String dateTo8String1(Date date) {
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        dates = sdf.format(date);
        return dates;
    }

    /**
     * yyyy/MM/dd
     * 
     * @param date
     * @return
     */
    public static String dateTo8String2(Date date) {
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dates = sdf.format(date);
        return dates;
    }

    /**
     * yyyy-MM-dd
     * 
     * @param date
     * @return
     */
    public static String dateTo8String3(Date date) {
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dates = sdf.format(date);
        return dates;
    }

    public static String dateToYMD(Date date) {
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        dates = sdf.format(date);
        return dates;
    }

    /**
     * yyyyMMdd
     * 
     * @param date
     * @return
     */
    public static Integer dateTo8Integer(Date date) {
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        dates = sdf.format(date);
        return Integer.valueOf(dates);
    }

    /**
     * yyyyMMdd
     * 
     * @param str
     * @return
     */
    public static Date string8ToDate(String str) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    /** 判断主方法 */
    public static boolean validate(String dateString) {
        // 使用正则表达式 测试 字符 符合 dddd-dd-dd 的格式(d表示数字)
        Pattern p = Pattern.compile("\\d{4}+\\d{1,2}+\\d{1,2}+");
        Matcher m = p.matcher(dateString);
        if (!m.matches()) {
            return false;
        }

        // 得到年月日
        int year = Integer.valueOf(dateString.substring(0, 4));
        int month = Integer.valueOf(dateString.substring(4, 6));
        int day = Integer.valueOf(dateString.substring(6, 8));

        if (month < 1 || month > 12) {
            return false;
        }
        int[] monthLengths = new int[] { 0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (isLeapYear(year)) {
            monthLengths[2] = 29;
        } else {
            monthLengths[2] = 28;
        }
        int monthLength = monthLengths[month];
        if (day < 1 || day > monthLength) {
            return false;
        }
        return true;
    }

    /** 是否是闰年 */
    private static boolean isLeapYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    public static Date now() {
        return new Date();
    }

    /**
     * 根据年月日拼装日期
     * 
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDate(String year, String month, String day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = year + "-" + month + "-" + day;
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    /**
     * 比较两个日期相差多少天
     * 
     * @param fDate
     * @param oDate
     * @return
     */
    public static int countDiffDateDays(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return (day2 - day1);
    }

    public static long countDiffDateDays2(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTimeInMillis(0);
        aCalendar.setTime(fDate);
        fDate = getDate(aCalendar.get(Calendar.YEAR), aCalendar.get(Calendar.MONTH),
                        aCalendar.get(Calendar.DAY_OF_YEAR));
        aCalendar.setTime(oDate);
        oDate = getDate(aCalendar.get(Calendar.YEAR), aCalendar.get(Calendar.MONTH),
                        aCalendar.get(Calendar.DAY_OF_YEAR));
        long quot = (oDate.getTime() - fDate.getTime()) / 1000 / 60 / 60 / 24;
        if (quot < 0) {
            quot = -quot;
        }
        return quot;
    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_YEAR, day);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 过滤掉日期中的时分秒，保留年月日
     * 
     * @param date
     * @return
     */
    public static Date convertDate2Date(Date date) {
        String dateStr = dateTo8String(date);
        Date adate = convertStr2Date(dateStr);
        return adate;
    }

    /**
     * 获取日期年
     * 
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(date);
        return aCalendar.get(Calendar.YEAR);
    }

    /**
     * 今天几号
     * 
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(date);
        return aCalendar.get(Calendar.DATE);
    }

    /**
     * 说明：对两个日期进行比较(只比较日期，不比较时间)
     * 
     * @param date1
     * @param date2
     * @return
     * @author leon
     */
    public static int compareOnlyDate(Date date1, Date date2) {
        String date1str = dateTo8String(date1);
        Date newDate1 = convertStr2Date(date1str);
        String date2str = dateTo8String(date2);
        Date newDate2 = convertStr2Date(date2str);
        return newDate1.compareTo(newDate2);
    }

    /**
     * 说明：对两个日期进行比较(long与date)
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareDate(Long date1, Date date2) {
        if (date1 == null && date2 == null) return true;
        else if (date1 == null && date2 != null) return false;
        else if (date1 != null && date2 == null) return false;
        else {
            if (date1.longValue() == date2.getTime()) return true;
            return false;
        }
    }

    /**
     * 说明：求两个日期时间差
     * 
     * @param date1
     * @param date2
     * @return
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static long getBetweenDays(Date date1, Date date2) {
        if (date2.before(date1)) {// 保证date1在date2之前
            Date date3 = date1;
            date1 = date2;
            date2 = date3;
        }
        long to = date1.getTime();
        long from = date2.getTime();
        return (from - to) / (1000 * 60 * 60 * 24);
    }

    /**
     * 说明：格式化时间
     * 
     * @param date
     * @param format 格式
     * @return
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static String dateToFormat(Date date, String format) {
        String dates = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        dates = sdf.format(date);
        return dates;
    }

    /**
     * 说明：根据传入日期获取前一天日期
     * 
     * @param date
     * @return
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static Date getTheDayBefore(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        Date beforeDate = calendar.getTime();
        return beforeDate;
    }

    /**
     * 说明：根据传入日期获取后一天日期
     * 
     * @param date
     * @return
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static Date getTheDayAfter(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        Date afterDate = calendar.getTime();
        return afterDate;
    }

    /**
     * 说明：加减天数并转换为Timestamp 类型时间
     * 
     * @param date
     * @param days
     * @return
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static Timestamp increaseDateAndToTimestamp(Date date, Number days) {
        Timestamp timestamp = null;
        if (date == null) {
            return timestamp;
        }
        int intDays = days.intValue();
        Date increaseDate = DateUtil.increaseDate(date, intDays);
        timestamp = new Timestamp(increaseDate.getTime());
        return timestamp;
    }

    /**
     * 方法说明：将util.date转换为Timestamp
     * 
     * @param date
     * @return Timestamp
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static Timestamp dateToTimestamp(Date date) {
        Timestamp timestamp = null;
        if (date == null) {
            return timestamp;
        }
        timestamp = new Timestamp(date.getTime());
        return timestamp;
    }

    /**
     * 方法说明：将Timestamp转换成string MM-dd 格式
     * 
     * @param timestamp
     * @return String
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static String timestampToSMMdd(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        Date date = new Date(timestamp.getTime());
        String dateStr = dateToFormat(date, "MM-dd");
        return dateStr;
    }

    /**
     * 方法说明：将Timestamp转换成string MM-dd 格式
     * 
     * @param timestamp
     * @return String
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static String timestampToSYYYYMMdd(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        Date date = new Date(timestamp.getTime());
        String dateStr = dateToFormat(date, "yyyy-MM-dd");
        return dateStr;
    }

    /**
     * 方法说明：将Timestamp转换成string yyyy-MM-dd HH:mm:ss 格式
     * 
     * @param timestamp
     * @return String
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static String timestampTo14String(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        Date date = new Date(timestamp.getTime());
        String dateStr = dateTo14String(date);
        return dateStr;
    }

    /**
     * 说明：计算两个时间差:分为秒，分，时，天，月，年
     * 
     * @param timeStart long
     * @param timeEnd long
     * @return returnStr 返回时间差字符
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static String calcDateDifference(long timeStart, long timeEnd) {
        long between = 0;
        String returnStr = "";
        between = timeEnd - timeStart;// 得到两者的毫秒数

        long second = 1000;// 一秒的毫秒数
        long minute = 60 * second;// 一分钟的毫秒数
        long hour = minute * 60;// 一小时的毫秒数
        long day = hour * 24;// 一天的毫秒数
        long month = day * 30;// 一月的毫秒数
        long year = month * 12;// 一年的毫秒数

        if (between >= 0 && between < second) {
            returnStr = "刚刚";
        } else if (second >= 0 && between < minute) {
            returnStr = between / second + "秒前";
        } else if (between >= minute && between < hour) {
            returnStr = between / minute + "分钟前";
        } else if (between >= hour && between < day) {
            returnStr = between / hour + "小时前";
        } else if (between >= day && between < month) {
            returnStr = between / day + "天前";
        } else if (between >= month && between < year) {
            returnStr = between / month + "个月前";
        } else if (between >= year) {
            returnStr = between / year + "年前";
        }
        return returnStr;
    }

    /**
     * 说明方法描述：将字符串转为date
     * 
     * @param str
     * @return
     * @throws Exception
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static Date strToDate(String str) throws Exception {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
    }

    /**
     * @Description 方法描述： 将10位时间戳转为string
     * @author 吴昊 2018年3月6日20:02:38
     * @param str_num
     * @param format
     * @return
     */
    public static String timestamp2String(String str_num, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (str_num.length() == 13) {
            String date = sdf.format(new Date(Long.parseLong(str_num)));
            return date;
        } else {
            String date = sdf.format(new Date(Integer.parseInt(str_num) * 1000L));
            return date;
        }
    }

    /**
     * @Description 方法描述： 将10位时间戳转为date
     * @author 吴昊 2018年3月6日20:02:38
     * @param str_num
     * @param format
     * @return
     */
    public static Date timestamp2Date(String str_num, String format) {
        if (str_num.length() == 13) {
            return new Date(Long.parseLong(str_num));
        } else {
            return new Date(Integer.parseInt(str_num) * 1000L);
        }
    }

    /**
     * 说明方法描述：将字符串转为Timestamp
     * 
     * @param str
     * @return
     * @throws Exception
     * @author 吴昊 2018年3月6日20:02:38
     */
    public static Timestamp strToTimestamp(String str) throws Exception {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        Timestamp timestamp = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str).getTime());
        return timestamp;
    }

    /**
     * 当天10:15点
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @return 字符串类型时间戳
     */
    public static long toTimePoint(int hour, int minute) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, hour);
        todayStart.set(Calendar.MINUTE, minute);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }

    /**
     * 把日期转换成字符串型
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param date 要转化地时间
     * @param pattern 格式
     * @return 如:2222-02-02
     */
    public static String toString(Date date, String pattern) {
        if (date != null && !date.equals("")) {
            if (pattern == null) {
                pattern = "yyyy-MM-dd";
            }
            String dateString = "";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            try {
                dateString = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return dateString;
        } else {
            return "";
        }

    }

    /**
     * 字符串转日期
     * 
     * @param str
     * @return
     */
    public static Date StringTo10Date(String str) {
        DateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return formate.parse(str);
        } catch (Exception e) {
            return null;
        }
    }
}
