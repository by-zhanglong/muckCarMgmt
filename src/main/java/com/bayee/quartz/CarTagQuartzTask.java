package com.bayee.quartz;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.bayee.dao.SysPalceInfoMapper;
import com.bayee.dao.SysRuleListMapper;
import com.bayee.dao.SysRuleTypeMapper;
import com.bayee.dao.SysTagInfoMapper;
import com.bayee.dao.SysTagPalceRelationMapper;
import com.bayee.dao.WarnCarInfoMapper;
import com.bayee.model.SysPalceInfo;
import com.bayee.model.SysRuleList;
import com.bayee.model.SysTagChirdObject;
import com.bayee.model.WarnCarInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
public class CarTagQuartzTask {
	String[] configLocations = new String[]{"spring-mybatis.xml","spring-mvc.xml"};
	ApplicationContext ac = new ClassPathXmlApplicationContext(configLocations);
	WarnCarInfoMapper warnCarInfoMapper=(WarnCarInfoMapper)ac.getBean("warnCarInfoMapper");
	SysPalceInfoMapper  sysPalceInfoMapper=(SysPalceInfoMapper)ac.getBean("sysPalceInfoMapper");
	SysRuleListMapper sysRuleListMapper=(SysRuleListMapper)ac.getBean("sysRuleListMapper");
	SysTagPalceRelationMapper sysTagPalceRelationMapper=(SysTagPalceRelationMapper)ac.getBean("sysTagPalceRelationMapper");
	SysTagInfoMapper sysTagInfoMapper=(SysTagInfoMapper)ac.getBean("sysTagInfoMapper");
	SysRuleTypeMapper sysRuleTypeMapper=(SysRuleTypeMapper)ac.getBean("sysRuleTypeMapper");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void doCronTask(){
    	//List<SysPalceInfo> sysPalceInfoList=sysPalceInfoMapper.selectSysPalceInfoByParams(new SysPalceInfo());
    	Map<String,Object> para_map=new HashMap<String, Object>();
		para_map.put("listTypeIdArr", "(2,3)");
		para_map.put("listStatus", "Y");
		List<SysRuleList> sysRuleList=sysRuleListMapper.selectSysRuleListByMap(para_map);
    	/* 当前时间前X分钟获取 */
    	Calendar beforeTime = Calendar.getInstance();
    	String endTime = sdf.format(beforeTime.getTime()); //当前时间
    	beforeTime.add(Calendar.MINUTE, 15);// X分钟之前的时间
		Date beforeD = beforeTime.getTime();
		String startTime = sdf.format(beforeD);  // 前X分钟时间
		para_map.clear();
		if(!StringUtils.isEmpty(startTime)){
			para_map.put("warnCTimeStart", startTime);
		}
		if(!StringUtils.isEmpty(endTime)){
			para_map.put("warnCTimeEnd", endTime);
		}
		para_map.put("isActive", "Y");
		//查询时间范围内所有车辆信息
		List<WarnCarInfo> warnCarList = warnCarInfoMapper.selectWarnCarByParamMap(para_map);
    	System.out.println("doCronTask正在运行..."+beforeTime);
    	
    	for(SysRuleList data:sysRuleList) {//黑地和易倾倒区域
    		  if(2==data.getListTypeId()) {//易倾倒区域
    			  	//找出所有打标的易倾倒区域点位
    			    para_map.clear();
    	    		para_map.put("tagType", 2);//易倾倒区域
    	    		List<SysTagChirdObject> sysTagChirdObjectList=sysTagInfoMapper.selectDataByTypeMap(para_map);
    	    		String palces="";
    	    		for(SysTagChirdObject sysTagChirdObject:sysTagChirdObjectList) {
    	    			if(!palces.contains(sysTagChirdObject.getObjCode())) {
    	    				palces+=sysTagChirdObject.getObjCode()+",";
    	    			}
    	    		}
    	    		for(WarnCarInfo warnCarInfo:warnCarList) {
    	    			if(palces.contains(warnCarInfo.getWarnCPlace())) {
    	    				if(StringUtils.isEmpty(warnCarInfo.getWarnItem())||!warnCarInfo.getWarnItem().contains("2")) {
    							int score=warnCarInfo.getWarnCTotal()+data.getListValueScore();
        						String items="";
        						String itemNames="";
        						String itemLists="";
        						if(StringUtils.isEmpty(warnCarInfo.getWarnItem())) {
        							items="2";
        						}else {
        							if(items.startsWith("1,")) {
        								items="1,2,"+warnCarInfo.getWarnItem().substring(2,warnCarInfo.getWarnItem().length());
        							}else {
        								items="2,"+warnCarInfo.getWarnItem();
        							}
        							
        						}
        						if(StringUtils.isEmpty(warnCarInfo.getWarnCUdf2())) {
        							itemNames="易倾倒地";
        						}else {
        							 itemNames=warnCarInfo.getWarnCUdf2()+",易倾倒地";
        						}
        						if(StringUtils.isEmpty(warnCarInfo.getWarnCUdf3())) {
        							itemLists=data.getListId()+"";
        						}else {
        							itemLists=warnCarInfo.getWarnCUdf3()+","+data.getListId();
        						}
        						warnCarInfo.setWarnCTotal(score);
        						warnCarInfo.setWarnItem(items);
        						warnCarInfo.setWarnCUdf2(itemNames);
        						warnCarInfo.setWarnCUdf3(itemLists);
        						warnCarInfoMapper.updateByPrimaryKeySelective(warnCarInfo);
    						}
    	    			}
					}
    		  }else if(3==data.getListTypeId()) {//黑地
    			//找出所有黑地的点位
    			para_map.clear();
  	    		para_map.put("tagType", 5);//黑地
  	    		List<SysTagChirdObject> sysTagChirdObjectList=sysTagInfoMapper.selectDataByTypeMap(para_map);
  	    		String palces="";
	    		for(SysTagChirdObject sysTagChirdObject:sysTagChirdObjectList) {
	    			if(!palces.contains(sysTagChirdObject.getObjCode())) {
	    				palces+=sysTagChirdObject.getObjCode()+",";
	    			}
	    		}
	    		
	    		//判定出现在黑地点位的车辆信息并赋分
	    		for(WarnCarInfo warnCarInfo:warnCarList) {
	    			if(palces.contains(warnCarInfo.getWarnCPlace())) {
	    				if(StringUtils.isEmpty(warnCarInfo.getWarnItem())||!warnCarInfo.getWarnItem().contains("3")) {
							int score=warnCarInfo.getWarnCTotal()+data.getListValueScore();
    						String items="";
    						String itemNames="";
    						String itemLists="";
    						if(StringUtils.isEmpty(warnCarInfo.getWarnItem())) {
    							items="3";
    						}else {
    							if(items.startsWith("1,")||items.startsWith("2,")) {
    								items=warnCarInfo.getWarnItem().substring(0,1)+"3,"+warnCarInfo.getWarnItem().substring(2,warnCarInfo.getWarnItem().length());
    							}else if(items.startsWith("1,2,")){
    								items=warnCarInfo.getWarnItem().substring(0,3)+"3,"+warnCarInfo.getWarnItem().substring(4,warnCarInfo.getWarnItem().length());
    							}else {
    								items="3,"+warnCarInfo.getWarnItem();
    							}
    							
    						}
    						if(StringUtils.isEmpty(warnCarInfo.getWarnCUdf2())) {
    							itemNames="黑地";
    						}else {
    							 itemNames=warnCarInfo.getWarnCUdf2()+",黑地";
    						}
    						if(StringUtils.isEmpty(warnCarInfo.getWarnCUdf3())) {
    							itemLists=data.getListId()+"";
    						}else {
    							itemLists=warnCarInfo.getWarnCUdf3()+","+data.getListId();
    						}
    						warnCarInfo.setWarnCTotal(score);
    						warnCarInfo.setWarnItem(items);
    						warnCarInfo.setWarnCUdf2(itemNames);
    						warnCarInfo.setWarnCUdf3(itemLists);
    						warnCarInfoMapper.updateByPrimaryKeySelective(warnCarInfo);
						}
	    			}
				}
    		  }
    			
    	}
    	
    }
}
