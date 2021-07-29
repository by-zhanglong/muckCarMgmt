package com.bayee.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bayee.dahua.utils.BaseUserInfo;
import com.bayee.dahua.utils.HttpEnum;
import com.bayee.dahua.utils.HttpTestUtils;
import com.bayee.dao.CarEarlyWarningMapper;
import com.bayee.dao.SysTimeRecordMapper;
import com.bayee.model.CarEarlyWarning;
import com.bayee.model.SysTimeRecord;

public class CarAlarmTask {
	String[] configLocations = new String[]{"spring-mybatis.xml","spring-mvc.xml"};
	ApplicationContext ac = new ClassPathXmlApplicationContext(configLocations);
	SysTimeRecordMapper sysTimeRecordMapper=(SysTimeRecordMapper)ac.getBean("sysTimeRecordMapper");
	CarEarlyWarningMapper carEarlyWarningMapper=(CarEarlyWarningMapper)ac.getBean("carEarlyWarningMapper");
	String action="/multiDataMiningService/rest/alarm/history";
	 public void doCronTask(){
		 BaseUserInfo bu=new BaseUserInfo();
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String token=bu.getToken();
		 Map<String,Object> para_map=new HashMap<String,Object>();
		 para_map.put("recTime",0); 
		 Date startDate=sysTimeRecordMapper.selectMaxRecordTime(para_map);
		 Date endDate=new Date();
		 if(null==startDate) {
			 Calendar c = Calendar.getInstance();
	         c.add(Calendar.DATE, -10);
	         startDate = c.getTime();
		 }
		 para_map.clear();
		 para_map.put("tagCode", bu.repositoryCode);
		 para_map.put("startTimeStr", sdf.format(startDate));
		 para_map.put("endTimeStr", sdf.format(endDate));
		 para_map.put("threshold", 85);
		 para_map.put("start", 1);
		 para_map.put("limit", 2000);
		 String content=JSONObject.toJSONString(para_map);
		 String resData=HttpTestUtils.httpRequest(HttpEnum.POST, bu.ip, Integer.parseInt(bu.port), action, token, content);
		 if (!StringUtils.isEmpty(resData)) {
			 JSONObject jsonData = JSON.parseObject(resData);
				if ("200".equals(jsonData.getString("code"))
						&&jsonData.getInteger("totalCount")>0) {//状态为200,总条数大于0
					JSONArray dataList=jsonData.getJSONArray("results");
					if(null!=dataList) {
						for(int i=0;i<dataList.size();i++) {
							CarEarlyWarning  carEarlyWarning=new CarEarlyWarning();
							carEarlyWarning.setwCarNum(dataList.getJSONObject(i).getString("plateNum"));
							carEarlyWarning.setwChannelCode(dataList.getJSONObject(i).getString("channelCode"));
							carEarlyWarning.setwChannelName(dataList.getJSONObject(i).getString("channelName"));
							carEarlyWarning.setwCapDateStr(dataList.getJSONObject(i).getString("capTimeStr"));
							carEarlyWarning.setwGpsX(dataList.getJSONObject(i).getDouble("gpsX"));
							carEarlyWarning.setwGpsY(dataList.getJSONObject(i).getDouble("gpsY"));
							carEarlyWarning.setwImgUrl(dataList.getJSONObject(i).getString("imgUrl1"));
							carEarlyWarning.setwInfo(dataList.getJSONObject(i).toJSONString());
							carEarlyWarningMapper.insertSelective(carEarlyWarning);
						}
					}
				}
				SysTimeRecord sysTimeRecord=new SysTimeRecord();
				sysTimeRecord.setRecTime(endDate);
				sysTimeRecord.setRecType(0);
				sysTimeRecordMapper.insertSelective(sysTimeRecord);
		 }
	 }
	
	
}
