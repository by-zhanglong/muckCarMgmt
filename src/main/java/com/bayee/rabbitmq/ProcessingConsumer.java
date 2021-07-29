package com.bayee.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bayee.dao.ProcessingEventImagelistMapper;
import com.bayee.dao.ProcessingEventMapper;
import com.bayee.dao.SysCarInfoMapper;
import com.bayee.dao.SysPalceInfoMapper;
import com.bayee.dao.SysRuleListMapper;
import com.bayee.dao.WarnCarInfoMapper;
import com.bayee.model.ProcessingEvent;
import com.bayee.model.ProcessingEventImagelist;
import com.bayee.model.SysPalceInfo;
import com.bayee.model.SysRuleList;
import com.bayee.model.WarnCarInfo;
import com.bayee.util.TimeUtils;
public class ProcessingConsumer implements MessageListener{
	//private static final ObjectMapper MAPPER = new ObjectMapper();
	String[] configLocations = new String[]{"spring-mybatis.xml","spring-mvc.xml"};
	ApplicationContext ac = new ClassPathXmlApplicationContext(configLocations);
	ProcessingEventMapper processingEventMapper=(ProcessingEventMapper)ac.getBean("processingEventMapper");
	ProcessingEventImagelistMapper processingEventImagelistMapper=(ProcessingEventImagelistMapper)ac.getBean("processingEventImagelistMapper");
	WarnCarInfoMapper warnCarInfoMapper=(WarnCarInfoMapper)ac.getBean("warnCarInfoMapper");
	SysRuleListMapper sysRuleListMapper=(SysRuleListMapper)ac.getBean("sysRuleListMapper");
	SysPalceInfoMapper  sysPalceInfoMapper=(SysPalceInfoMapper)ac.getBean("sysPalceInfoMapper");
	SysCarInfoMapper  sysCarInfoMapper=(SysCarInfoMapper)ac.getBean("sysCarInfoMapper");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	boolean isInit=false;
	String channelCodes="";
	@Override
	public void onMessage(Message message) {
		try {
			String bodyStr = new String(message.getBody(), "UTF-8");
			JSONObject jsonObject=JSONObject.parseObject(bodyStr);
			JSONArray imageList=(JSONArray)jsonObject.get("imageList");
			ProcessingEvent processingEvent =JSON.parseObject(bodyStr,ProcessingEvent.class);
			processingEventMapper.insertSelective(processingEvent);
			List<ProcessingEventImagelist> processingEventImagelist=JSON.parseArray(JSONObject.toJSONString(imageList), ProcessingEventImagelist.class);
			String image_url="";
			for(ProcessingEventImagelist image:processingEventImagelist) {
				image.setPicRecordId(processingEvent.getRecordId());
				if(0==image.getImgType()) {
					image_url=image.getImgUrl();
				}
				//processingEventImagelistMapper.insert(image);
			}
			if(!isInit) {
				 List<SysPalceInfo> sysPalceInfoList=sysPalceInfoMapper.selectSysPalceInfoByParams(new SysPalceInfo());
				 for(SysPalceInfo sysPalceInfo:sysPalceInfoList) {
					 channelCodes+=sysPalceInfo.getPalceNo()+",";
				 }
				 isInit=true;
			}
			 //如果是渣土车，直接插入预警表
			if(("Z-Dregs".equals(processingEvent.getCarAttribute())
					&&("01".equals(processingEvent.getPlateTypeA())||"15".equals(processingEvent.getPlateTypeA()))
					)&&channelCodes.contains(processingEvent.getChannelCode())) {//车辆用途是渣土车、车辆类型是大货车或挂车 、抓拍地点为系统已接入的
				Map<String,Object> para_map=new HashMap<String, Object>();
				//para_map.put("listTypeId", 1);
				para_map.put("listStatus", "Y");
				List<SysRuleList> sysRuleList=sysRuleListMapper.selectSysRuleListByMap(para_map);
				WarnCarInfo warnCarInfo=new WarnCarInfo();
				warnCarInfo.setWarnCColor(processingEvent.getCarColorA());
				warnCarInfo.setWarnCNum(processingEvent.getPlateNumA());
				Date date=new Date(new Double(processingEvent.getCapTime()).longValue());
		    	String dateStr = TimeUtils.timeZoneTransfer(sdf.format(date), "yyyy-MM-dd HH:mm:ss", "+8", "+8");
		    	warnCarInfo.setWarnCTime(dateStr);
		    	warnCarInfo.setWarnCPlace(processingEvent.getChannelCode());
		    	warnCarInfo.setWarnCUdf1(image_url);
		    	warnCarInfo.setWarnCUdf4(processingEvent.getRecordId());
		    	para_map.clear();
		    	para_map.put("carType", 0);
		    	para_map.put("carNum", processingEvent.getPlateNumA());
		    	int dataCount=sysCarInfoMapper.selectSysCarCountByParamMap(para_map);
		    	int hour=sdf.parse(dateStr).getHours();
		    	int score=0;
		    	String item="";
		    	String type="";
		    	String typeList="";//具体的规则项
		    	boolean ejFlag=false;//夜间出行标识
		    	boolean wpFlag=false;//外拍赋分标识
		    	if(dataCount==0) {
		    		for(SysRuleList data:sysRuleList) {
			    		if(1==data.getListTypeId()) {//凌晨出行
			    			if(!StringUtils.isEmpty(data.getListValueFir())&&!StringUtils.isEmpty(data.getListValueSec())) {//存在起止时间
				    			int startHour=Integer.parseInt(data.getListValueFir());
				    			int endHour=Integer.parseInt(data.getListValueSec());
				    			if(startHour<endHour) {//开始时间小于结束时间 同一天
				    				if(startHour<=hour&&hour<endHour) {
					    				score+=data.getListValueScore();
					    				ejFlag=true;
					    				typeList+=data.getListId()+",";
					    			}
				    			}else if(startHour>endHour) {//跨天设置
				    				if(endHour==0) {
				    					if(startHour<=hour&&hour<24) {
						    				score+=data.getListValueScore();
						    				ejFlag=true;
						    				typeList+=data.getListId()+",";
						    			}
				    				}else if(endHour>0) {
				    					if((startHour<=hour&&hour<24)||(0<=hour&&hour<endHour)) {
						    				score+=data.getListValueScore();
						    				ejFlag=true;
						    				typeList+=data.getListId()+",";
						    			}
				    				}
				    			}else {
				    				if(startHour<=hour&&hour<=endHour) {//开始时间和结束时间相同
					    				score+=data.getListValueScore();
					    				ejFlag=true;
					    				typeList+=data.getListId()+",";
					    			}
				    			}
				    			
				    		}
			    		}else if(4==data.getListTypeId()) {//外牌
			    			if(!StringUtils.isEmpty(data.getListValueFir())) {//外牌
			    				if(!StringUtils.isEmpty(processingEvent.getPlateNumA())
			    				&&processingEvent.getPlateNumA().contains(data.getListValueFir())) {//牌照不为空且包含系统设置的车牌区域
			    					score+=data.getListValueScore();
			    					wpFlag=true;
			    					typeList+=data.getListId()+",";
			    				}
			    			}
			    		}	
			    	}
		    	}
		    	if(ejFlag) {
		    		item+="1,";
	    			type+="凌晨出行,";
		    	}
		    	if(wpFlag) {
		    		item+="4,";
	    			type+="外牌,";
		    	}
		    	if(item.lastIndexOf(",")>0) {
		    		item=item.substring(0,item.lastIndexOf(","));
		    	}
		    	if(type.lastIndexOf(",")>0) {
		    		type=type.substring(0,type.lastIndexOf(","));
		    	}
		    	if(typeList.lastIndexOf(",")>0) {
		    		typeList=typeList.substring(0,typeList.lastIndexOf(","));
		    	}
		    	if("Z-Grab".equals(processingEvent.getCarAttribute())) {
		    		warnCarInfo.setWarnItem("");
			    	warnCarInfo.setWarnCUdf2("");
			    	warnCarInfo.setWarnCUdf3("");
			    	warnCarInfo.setWarnCTotal(0);
		    	}else {
		    		warnCarInfo.setWarnItem(item);
			    	warnCarInfo.setWarnCUdf2(type);
			    	warnCarInfo.setWarnCUdf3(typeList);
			    	warnCarInfo.setWarnCTotal(score);
		    	}
		    	warnCarInfo.setWarnCUdf5(processingEvent.getCarAttribute());
		    	warnCarInfoMapper.insertSelective(warnCarInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
