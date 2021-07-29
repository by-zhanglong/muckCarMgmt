package com.bayee.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

public class TimeUtils {

	/**
     * 时区转换
     * @param time 时间字符串
     * @param pattern 格式 "yyyy-MM-dd HH:mm"
     * @param nowTimeZone eg:+8，0，+9，-1 等等
     * @param targetTimeZone 同nowTimeZone
     * @return
     */
    public static String timeZoneTransfer(String time, String pattern, String nowTimeZone, String targetTimeZone) {
        if(StringUtils.isEmpty(time)){
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT" + nowTimeZone));
        Date date;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            return "";
        }
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT" + targetTimeZone));
        return simpleDateFormat.format(date);
    }
    public static void main(String[] args) throws Exception {
		
		  NumberFormat nf = NumberFormat.getInstance(); 
		  nf.setGroupingUsed(false);
		  //不用科学计数 
		  double t=1625126528000.0; 
		  Date date=new Date(new Double(t).longValue()); 
		  SimpleDateFormat sdf = new
		  SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		  String result =TimeUtils.timeZoneTransfer(sdf.format(date), "yyyy-MM-dd HH:mm:ss", "+8",
		  "+8"); 
		  Date dateRes=sdf.parse(result);
		  System.out.println(dateRes.getHours()); ; 
		  System.out.println(result);
		 
    	
	}
}
