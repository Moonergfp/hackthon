package com.hack.reflection;

import com.google.common.base.Splitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Test {
   private static final SimpleDateFormat SELECTION_OPT_DATE_FORMAT = new SimpleDateFormat("yyyy/M/d");
   private static final SimpleDateFormat SELECTION_OPT_WMS_PARAMS_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

   @org.junit.Test
   public  void test() throws ParseException {
      String timeSpanStr = "2017/1/7-2017/1/13";

      List<String> timeSpans = Splitter.on('-').omitEmptyStrings().splitToList(timeSpanStr);

      Date startDate = SELECTION_OPT_DATE_FORMAT.parse(timeSpans.get(0));
      Date endDate = SELECTION_OPT_DATE_FORMAT.parse(timeSpans.get(1));
      String startTimeStr = SELECTION_OPT_WMS_PARAMS_DATE_FORMAT.format(startDate);
      String endTimeStr = SELECTION_OPT_WMS_PARAMS_DATE_FORMAT.format(endDate);

      Calendar lastStartCal = Calendar.getInstance();
      Calendar lastEndCal = Calendar.getInstance();
      lastStartCal.setTime(startDate);
      lastEndCal.setTime(endDate);
      lastStartCal.add(Calendar.DATE, -7);
      lastEndCal.add(Calendar.DATE, -7);


      String lastStartTimeStr = SELECTION_OPT_WMS_PARAMS_DATE_FORMAT.format(lastStartCal.getTime());
      String lastEndTimeStr = SELECTION_OPT_WMS_PARAMS_DATE_FORMAT.format(lastEndCal.getTime());

      System.out.println(startTimeStr);
      System.out.println(endTimeStr);
      System.out.println(lastStartTimeStr);
      System.out.println(lastEndTimeStr);
   }


    /**
     * 测试Integer decode方法
     */
    @org.junit.Test
    public void testDecode(){
       String emp = "guofeipeng;;wangshiyuan;";
       List<String> misIdList = Splitter.on(";").omitEmptyStrings().splitToList(emp);
       System.out.println(misIdList);

   }

   public class ABc{

   }
}
