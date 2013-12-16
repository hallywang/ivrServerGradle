package com.emag.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * viavame All Rights Reserved@2008-2009 ����: ������ ����: 2008-11-26 ʱ��: 15:24:19 ˵��: ����ʵ�֡������� �޸���:������ �޸�ʱ��: 2008-11-26
 * 15:24:19
 */

public class TimeUtil {

    /**
     * @param m ��m=-1��������һ  m=0���ر���һ  m=1��������ֵ��������һ��
     * @return yyyy-MM-dd
     */
    public static String getModayOfWeek(int m) {  //��ǰ�����ڼ�
        int dayOfweek = GregorianCalendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfweek == 0)
            dayOfweek = 7;
        Calendar cal = Calendar.getInstance();
        if (m == -1)//��������һ��yyyy-MM-dd���ڸ�ʽ)
            m = -(dayOfweek - 1) - 7;
        else if (m == 0)//���ر���һ��yyyy-MM-dd���ڸ�ʽ
            m = -(dayOfweek - 1);
        else//==1��������ֵ ��������һ��yyyy-MM-dd���ڸ�ʽ
            m = 7 - dayOfweek + 1;
        cal.add(Calendar.DATE, m);


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//���������������Ҫ�ĸ��ָ�ʽ��
        return formatter.format(cal.getTime());
    }


    /**
     * @param m ��m=-1��������һ  m=0���ر���һ  m=1��������ֵ��������һ��
     * @return date ����
     */
    public static Date getModayOfWeekDate(int m) {  //��ǰ�����ڼ�
        int dayOfweek = GregorianCalendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfweek == 0)
            dayOfweek = 7;
        Calendar cal = Calendar.getInstance();
        if (m == -1)//��������һ��yyyy-MM-dd���ڸ�ʽ)
            m = -(dayOfweek - 1) - 7;
        else if (m == 0)//���ر���һ��yyyy-MM-dd���ڸ�ʽ
            m = -(dayOfweek - 1);
        else//==1��������ֵ ��������һ��yyyy-MM-dd���ڸ�ʽ
            m = 7 - dayOfweek + 1;
        cal.add(Calendar.DATE, m);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);


        return (cal.getTime());
    }

    /**
     * ��ȡĳһ������ڸ�ʽyyyy-MM-dd
     *
     * @param m (m=0���� m=1����)
     * @return str
     */
    public static String getDate(int m) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, m);


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//���������������Ҫ�ĸ��ָ�ʽ��
        return formatter.format(cal.getTime());
    }

    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");//���������������Ҫ�ĸ��ָ�ʽ��
        return formatter.format(cal.getTime());
    }

    /**
     * @param d
     * @return
     */
    public static String getDate2(Date d) {

        return getDate(d, "yyyy-MM-dd");

    }


    /**
     * @param d
     * @return
     */
    public static String getDate(Date d, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);//���������������Ҫ�ĸ��ָ�ʽ��
        return formatter.format(d);

    }


    public static Date parseDate(String date) {
        return TimeUtil.parseDateByString(date, "yyyy-MM-dd", 0);
    }

    public static String getDateStr(long millis, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        Formatter ft = new Formatter(Locale.CHINA);
        if (format == null || "".equals(format)) {
            format = "%1$tY��%1$tm��%1$td��%1$tA��%1$tT %1$tp";
        }
        return ft.format(format, cal).toString();
    }

    public static String getDateStrFormat(long millis, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);//���������������Ҫ�ĸ��ָ�ʽ��
        return formatter.format(cal.getTime());

    }

    /**
     * @param date   ���� �� 2008-9-1
     * @param format �� yyyy-mm-dd
     * @param time   0 ��ʾ����ʱ��Ϊ0��0��0�룬1 ��ʾ����Ϊ23:59:59
     * @return
     */
    public static Date parseDateByString(String date, String format, int time) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);//���������������Ҫ�ĸ��ָ�ʽ��
        if (time == 1) {
            date = date + " 23:59:59";
            format = format + " HH:mm:ss";
            formatter = new SimpleDateFormat(format);//���������������Ҫ�ĸ��ָ�ʽ��
        }


        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * @param date   ���� �� 2008-9-1
     * @param format �� yyyy-mm-dd
     * @return
     */
    public static Date parseDateByString(String date, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);//���������������Ҫ�ĸ��ָ�ʽ��
        return formatter.parse(date);

    }

    /**
     * @param m (m=0���� m=1����,-1 ����)
     * @return str
     */
    public static Date getDateBefore(int m) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, m);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);


        return cal.getTime();
    }


    /**
     * �õ�Сʱ�ͷ���
     *
     * @param d ����
     * @return 15��32
     */
    public static String getTime(Date d) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(d);
        String mi = "";

        mi = cal.get(Calendar.MINUTE) < 10 ? "0" + cal.get(Calendar.MINUTE) : "" + cal.get(Calendar.MINUTE);

        return cal.get(Calendar.HOUR_OF_DAY) + ":" + mi;
    }


    /**
     * �ж��Ƿ��ǵ��µ�һ�� 0���� 1�� ����һ������Ϊһ��
     *
     * @return
     */
    public static int isFirstWeek() {
        int flag = 0;
        GregorianCalendar cal = new GregorianCalendar();
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0)
            week = 7;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (week >= day)
            flag = 1;
        return flag;

    }


    /**
     * ȡ��һ�ܵĵ�һ������
     *
     * @return
     */
    public static String getFirstDateOfLastWeek() {
        String firstDate = null;
        GregorianCalendar cal = new GregorianCalendar();
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        /*
           * int month=cal.get(Calendar.MONTH)+1; int year=cal.get(Calendar.YEAR);
           */
        if (week == 0)
            week = 7;
        cal.add(Calendar.DATE, -(week + 6));
        SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date d = cal.getTime();
        firstDate = aSimpleDateFormat.format(d);
        /*
           * int lastmonth=cal.get(Calendar.MONTH)+1; if(lastmonth!=month){
           * if(month<10) firstDate=year+"-0"+month+"-"+"01"; else
           * firstDate=year+"-"+month+"-"+"01"; }else{ SimpleDateFormat
           * aSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); Date
           * d=cal.getTime(); firstDate=aSimpleDateFormat.format(d); }
           */

        return firstDate;
    }

    /**
     * ��ȡ�������һ���ʱ��
     *
     * @param flag �Ƿ����õ�ǰʱ���ʱ����ı�־��<br/> 0 ��ʾ����Ҫ�����շ��ص�Ϊ���ܵ�һ���0ʱ0��0�� <br/> 1 ��ʾʹ��23ʱ59��59��999���� ������  ��ʾ���õ�ǰʱ���ʱ���� <br/>
     * @return Date���͵�ʱ��
     */
    public static Date getLastDOfLastWeek(int flag) {
        String lastDate = null;
        GregorianCalendar cal = new GregorianCalendar();
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {
            week = 7;
        }
        cal.add(Calendar.DATE, -week);
        if (flag == 0) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        } else if (flag == 1) {
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
        }

        return cal.getTime();
    }

    /**
     * ��ȡ���ܵ�һ���ʱ��
     *
     * @param flag �Ƿ����õ�ǰʱ���ʱ����ı�־��<br/> 0 ��ʾ����Ҫ�����շ��ص�Ϊ���ܵ�һ���0ʱ0��0�� <br/> 1 ��ʾʹ��23ʱ59��59��999���� <br/> ��������ʾ���õ�ǰʱ���ʱ����
     *             <br/>
     * @return Date���͵�ʱ��
     */
    public static Date getFirstDOfLastWeek(int flag) {
        GregorianCalendar cal = new GregorianCalendar();
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {
            week = 7;
        }
        cal.add(Calendar.DATE, -(week + 6));
        if (flag == 0) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        } else if (flag == 1) {
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
        }

        return cal.getTime();
    }


    /**
     * ȡ��һ�����һ������
     *
     * @return String���͵�ʱ��
     */
    public static String getLastDateOfLastWeek() {
        String lastDate = null;
        GregorianCalendar cal = new GregorianCalendar();
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0)
            week = 7;
        cal.add(Calendar.DATE, -week);
        SimpleDateFormat aSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date d = cal.getTime();
        lastDate = aSimpleDateFormat.format(d);
        return lastDate;
    }


    /**
     * ��ȡ����ʱ��
     *
     * @param rtnFlag     ����ֵ���ͣ�0����Date���͵�ʱ�䣬1����14λString���͵�ʱ��
     * @param timeExtFlag ��βʱ���־��0��ʾ0ʱ0��0��0���룬1��ʾ23ʱ59��59��999���룬������ʾ��ǰʱ���ʱ�������
     * @param dayOfWeek   �ܼ���ȡֵ��ΧΪ0-7�� <br/> 0�������գ�<br/> 1������һ��<br/> 2�����ܶ���<br/> 3����������<br/> 4�������ģ�<br/> 5�������壬<br/>
     *                    6����������<br/> 7�������գ�����ϰ�߽�������Ϊһ�ܵ����һ�죬 ����������ʵ���ϱ��ܵĵ�һ�� <br> >7���κδ���7�����ֽ��ᱻ��ȡ�ࡱ����
     *                    <0���κ�С��0�����ֽ��ᱻ������ֵ������
     * @return ����Object���͵�ʱ�䣬�����������ʵ�������������ת��
     */
    public static Object getLastWeekTime(int rtnFlag, int timeExtFlag, int dayOfWeek) {
        Object rtnObj = null;
        dayOfWeek = Math.abs(dayOfWeek); //�Ⱦ���ֵ
        if (dayOfWeek > 7) {
            dayOfWeek = dayOfWeek % 7; //ȡ�����
        }

        GregorianCalendar cal = new GregorianCalendar();
        int curWeekNo = cal.get(Calendar.DAY_OF_WEEK) - 1; //�����ڡ������ڼ���0�����죬1����һ��6������
        cal.add(Calendar.DATE, -(7 + curWeekNo - dayOfWeek));
        if (timeExtFlag == 0) {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        } else if (timeExtFlag == 1) {
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
        } else {
            //ʱ������������á���ǰʱ�䡱��ʱ���������
        }

        rtnObj = cal.getTime();
        if (rtnFlag != 0) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            rtnObj = df.format(rtnObj);
        }

        return rtnObj;
    }

    /**
     * ��ȡһ��ʱ����ʱ��  <br> ����Ҫ��ȡ�����ڡ�֮��һ��Сʱ���ʱ��
     *
     * @param baseDate ����ʱ��,
     * @param field    ���ͣ�y���꣬M���£�d���죬h��Сʱ��m�����ӣ�s����
     * @param amount   ����ʱ����
     * @return ����һ��Date���͵�ʱ��
     */
    public static Date getNDateLater(Date baseDate, String field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(baseDate);

        int f = Calendar.SECOND;
        if ("y".equals(field)) {
            f = Calendar.YEAR;
        } else if ("M".equals(field)) {
            f = Calendar.MONTH;
        } else if ("d".equals(field)) {
            f = Calendar.DAY_OF_MONTH;
        } else if ("h".equals(field)) {
            f = Calendar.HOUR_OF_DAY;
        } else if ("m".equals(field)) {
            f = Calendar.MINUTE;
        }

        cal.add(f, amount);

        return cal.getTime();
    }

    public static String formatTimeString(String timeStr) {
        if (StringUtil.isEmpty(timeStr)) {
            return "";
        }

        String rtnCode = "";
        if (timeStr.length() == 14) {
            //��14λʱ���ʽ��Ϊ 2010-07-27 11:34:20
            rtnCode = timeStr.substring(0, 4) + "-" + timeStr.substring(4, 6) + "-" + timeStr.substring(6, 8) + " "
                    + timeStr.substring(8, 10) + ":" + timeStr.substring(10, 12) + ":" + timeStr.substring(12, 14);

        }

        return rtnCode;
    }


    /**
     * �������ڼ��ֵ
     *
     * @param from       ��ʼʱ��
     * @param to         ����ʱ��
     * @param showSecond �Ƿ���ʾ��
     * @return x��xСʱx��x��
     * @editor:RongWei
     * @editDate:2010-11-02
     */
    public static String getTimeDis(String from, String to, boolean showSecond) {

        StringBuffer buff = new StringBuffer();
        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
        Date begin = null;
        Date end = null;
        try {
            begin = dfs.parse(from);
            end = dfs.parse(to);
            long between = (end.getTime() - begin.getTime()) / 1000;
            long day1 = between / (24 * 3600);
            long hour1 = between % (24 * 3600) / 3600;
            long minute1 = between % 3600 / 60;
            long second1 = between % 60;

            if (day1 > 0) {
                buff.append(StringUtil.addZeroToNum(String.valueOf(day1), 2));
                buff.append("��");
                return buff.toString();
            }
            if (hour1 > 0) {
                buff.append(StringUtil.addZeroToNum(String.valueOf(hour1), 2));
                buff.append("ʱ");
                return buff.toString();
            }
            if (minute1 > 0) {
                buff.append(StringUtil.addZeroToNum(String.valueOf(minute1), 2));
                buff.append("��");
                return buff.toString();
            }
            if (second1 > 0 & showSecond) {
                buff.append(StringUtil.addZeroToNum(String.valueOf(second1), 2));
                buff.append("��");
                return buff.toString();
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return buff.toString();
    }

    public static Long getSecondDis(String from, String to) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
        Date begin = null;
        Date end = null;
        try {
            begin = dfs.parse(from);
            end = dfs.parse(to);

            return (end.getTime() - begin.getTime()) / 1000;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    /**
     * ����ʱ��ľ��룬��λΪ��
     *
     * @param start ��ʼʱ�䣬��ֵС
     * @param stop  ����ʱ�䣬��ֵ��
     * @return
     */
    public static Long getSecondDis(Date start, Date stop) {
        try {
            return (stop.getTime() - start.getTime()) / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0L;
    }

    /**
     * ��ȡ�������������
     *
     * @param from ��ʼ����
     * @param to   ��������
     * @return �������������
     */
    public static long getDaysDis(Date from, Date to) {
        return (to.getTime() - from.getTime()) / (1000 * 60 * 60 * 24);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        long time = Long.parseLong("1384998349648");
        System.out.println(time);
        System.out.println(getDateStr(time, ""));
        System.out.println(getDateStrFormat(time, ""));
    }

    /**
     * ��ȡָ������ǰ(���)N�µ�����
     *
     * @param _date  ָ������(yyyy-MM-dd)
     * @param _scope N��
     * @return
     * @author:RongWei
     * @date:2010-11-22
     */
    public static String getPre2NextMthDate(String _date, int _scope) {
        Date dt = null;
        if (StringUtil.isEmpty(_date)) {
            dt = new Date();
        } else {
            try {
                dt = new SimpleDateFormat("yyyy-MM-dd").parse(_date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Calendar cd = new GregorianCalendar();
        cd.setTime(dt);
        cd.add(GregorianCalendar.MONTH, _scope);

        return getDate(cd.getTime(), "yyyy-MM-dd");
    }


    /**
     * ��ȡָ������ǰN�µ������·�
     *
     * @param _date  ָ������(yyyy-MM-dd)
     * @param _scope N��
     * @return
     * @author:RongWei
     * @date:2010-11-22
     */
    public static String[] getPreMths(String _date, int _scope) {
        Date dt = null;
        if (StringUtil.isEmpty(_date)) {
            dt = new Date();
        } else {
            try {
                dt = new SimpleDateFormat("yyyy-MM-dd").parse(_date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[] preMths = new String[_scope];
        //ѭ��
        for (int i = 0; i < _scope; i++) {
            Calendar cd = new GregorianCalendar();
            cd.setTime(dt);
            cd.add(GregorianCalendar.MONTH, -i);
            preMths[i] = getDate(cd.getTime(), "yyyyMM");
        }
        Arrays.sort(preMths);
        return preMths;

    }


    /**
     * ��ȡ��ǰ���ڵĿ�ʼʱ������ʱ��
     *
     * @param timeType 0-��ʼʱ�䣬1-����ʱ��
     * @return
     * @author RongWei
     * @date 2011-02-11
     */
    public static Date getCurrDateS2ETime(int timeType) {
        return parseDateByString(getDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd", timeType);
    }

    /**
     * ��ȡ��һ������,һ��Ϊ��һ������
     *
     * @param m �������ܣ�-1�����ܣ�-2��������
     * @return String������
     * @author ����|
     * @date 2011-03-28
     */
    public static String getMondayDate(int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH, m);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
        return dateFm.format(calendar.getTime());
    }

    /**
     * ��ȡ���յ�����,һ��Ϊ��һ������
     *
     * @param m ��:���ܣ�-1��������
     * @return String������
     * @author ����|
     * @date 2011-03-28
     */
    public static String getSundayDate(int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH, m);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
        return dateFm.format(calendar.getTime());
    }

    /**
     * ��ȡһ�������һ������
     *
     * @param m �������£�-1�����£�-2:������
     * @return String������
     * @author ����|
     * @date 2011-03-28
     */
    public static String getMonthLastDayDate(int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, m);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//һ���µ�����
        calendar.set(Calendar.DAY_OF_MONTH, maxDay);
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
        return dateFm.format(calendar.getTime());
    }

    /**
     * ��ȡһ���µ�һ������
     *
     * @param m �������£�-1�����£�-2:������
     * @return String������
     * @author ����|
     * @date 2011-03-28
     */
    public static String getMonthFirstDayDate(int m) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, m);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
        return dateFm.format(calendar.getTime());
    }

    public static String getUTCTime(String yyyyMMddHHmmss) {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            cal.setTime(dateFormat.parse(yyyyMMddHHmmss));
            int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
            int dstOffset = cal.get(Calendar.DST_OFFSET);
            cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        } catch (Exception ex) {
            cal.setTime(new Date());
        }

        return dateFormat.format(cal.getTime());
    }

    public static String getTimeFromUTC(String utcTime) {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            cal.setTime(dateFormat.parse(utcTime));
            int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
            int dstOffset = cal.get(Calendar.DST_OFFSET);
            cal.add(Calendar.MILLISECOND, +(zoneOffset + dstOffset));
        } catch (Exception ex) {
            cal.setTime(new Date());
        }

        return dateFormat.format(cal.getTime());
    }

    /**
     * ��ȡ��ǰʱ������N���ʱ��
     */
    public static String getAfterSecondsDate(Date d, String format, int defer) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, defer);
        SimpleDateFormat formater = new SimpleDateFormat(format);//���������������Ҫ�ĸ��ָ�ʽ��
        return formater.format(calendar.getTime());

    }
}
