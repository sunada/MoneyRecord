package com.springapp.mvc.Util;

import com.springapp.mvc.Model.Fund;
import com.springapp.mvc.Model.Interval;
import com.springapp.mvc.Model.Week;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/27.
 */
public class NetDay {
    private static Logger log = LoggerFactory.getLogger(NetDay.class);
    private ArrayList<String> holidays = new ArrayList<String>(){
        {
            add("2016-04-04");//清明
            add("2016-05-02");//五一
            add("2016-06-09");//端午
            add("2016-06-10");
            add("2016-09-15"); //中秋
            add("2016-09-16");
            add("2016-10-03"); //国庆
            add("2016-10-04");
            add("2016-10-05");
            add("2016-10-06");
            add("2016-10-07");
        }
    };

    public ArrayList<Integer> dealHoliday(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Integer> list = new ArrayList<Integer>();
        Date d;
        for(String h : holidays){
            try {
                d = sdf.parse(h);
                cal.setTime(d);
                list.add(cal.get(Calendar.DAY_OF_YEAR));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return list;
    }

    public boolean isWorkDay(Date date){
        ArrayList<Integer> list = dealHoliday();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1; //周n 判断是否周末
        int day = cal.get(Calendar.DAY_OF_YEAR);   //年月日 判断是否节日
        if(week == 6 || week == 0 || list.contains(day)){ //0代表周日，6代表周六
            return false;
        }
        return true;
    }

    //得到最近的开市日期
    public Date getNetDay(){
        Date date = new Date();
        return getNetDay(date);
    }

    //得到离给定日子最近的开市日期
    public Date getNetDay(Date date){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTime(date);
        while(!isWorkDay(date)){
            cal.add(Calendar.DATE, -1);
            date = cal.getTime();
        }
        return date;
    }

    public Date getNextAipDate(Date start, Fund fund){
        Date date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        Interval inter = fund.getInterval();
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1; //周n
        if(inter == Interval.MONTH){
            while(cal.get(Calendar.DATE) != fund.getDate()){
                cal.add(Calendar.DATE, 1);
            }
        }else{
            while(week != fund.getWeek().getIndex() + 1){
                cal.add(Calendar.DATE, 1);
                week = cal.get(Calendar.DAY_OF_WEEK) - 1;
            }
        }

        int i;
        int mul = -1;
        if(fund.getInterval() == Interval.MONTH){
            i = Calendar.MONTH;
        }else{
            i = Calendar.WEEK_OF_YEAR;
            if(fund.getInterval() == Interval.TWOWEEKS){
                mul = -2;
            }
        }
        cal.add(i, mul);
        return cal.getTime();
    }
}
