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
    	/* ��ǰʱ��ǰX���ӻ�ȡ */
    	Calendar beforeTime = Calendar.getInstance();
    	String endTime = sdf.format(beforeTime.getTime()); //��ǰʱ��
    	beforeTime.add(Calendar.MINUTE, 15);// X����֮ǰ��ʱ��
		Date beforeD = beforeTime.getTime();
		String startTime = sdf.format(beforeD);  // ǰX����ʱ��
		para_map.clear();
		if(!StringUtils.isEmpty(startTime)){
			para_map.put("warnCTimeStart", startTime);
		}
		if(!StringUtils.isEmpty(endTime)){
			para_map.put("warnCTimeEnd", endTime);
		}
		para_map.put("isActive", "Y");
		//��ѯʱ�䷶Χ�����г�����Ϣ
		List<WarnCarInfo> warnCarList = warnCarInfoMapper.selectWarnCarByParamMap(para_map);
    	System.out.println("doCronTask��������..."+beforeTime);
    	
    	for(SysRuleList data:sysRuleList) {//�ڵغ����㵹����
    		  if(2==data.getListTypeId()) {//���㵹����
    			  	//�ҳ����д������㵹�����λ
    			    para_map.clear();
    	    		para_map.put("tagType", 2);//���㵹����
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
        							itemNames="���㵹��";
        						}else {
        							 itemNames=warnCarInfo.getWarnCUdf2()+",���㵹��";
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
    		  }else if(3==data.getListTypeId()) {//�ڵ�
    			//�ҳ����кڵصĵ�λ
    			para_map.clear();
  	    		para_map.put("tagType", 5);//�ڵ�
  	    		List<SysTagChirdObject> sysTagChirdObjectList=sysTagInfoMapper.selectDataByTypeMap(para_map);
  	    		String palces="";
	    		for(SysTagChirdObject sysTagChirdObject:sysTagChirdObjectList) {
	    			if(!palces.contains(sysTagChirdObject.getObjCode())) {
	    				palces+=sysTagChirdObject.getObjCode()+",";
	    			}
	    		}
	    		
	    		//�ж������ںڵص�λ�ĳ�����Ϣ������
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
    							itemNames="�ڵ�";
    						}else {
    							 itemNames=warnCarInfo.getWarnCUdf2()+",�ڵ�";
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
