package com.bayee.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.util.StringUtils;

public class TimeUtils {

	/**
     * ʱ��ת��
     * @param time ʱ���ַ���
     * @param pattern ��ʽ "yyyy-MM-dd HH:mm"
     * @param nowTimeZone eg:+8��0��+9��-1 �ȵ�
     * @param targetTimeZone ͬnowTimeZone
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
		  //���ÿ�ѧ���� 
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
