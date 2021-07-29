package com.bayee.quartz;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.bayee.dao.SysPalceInfoMapper;
import com.bayee.dao.SysRuleListMapper;
import com.bayee.dao.WarnCarInfoMapper;
import com.bayee.model.SysPalceInfo;
import com.bayee.model.SysRuleList;
import com.bayee.model.WarnCarInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
public class CarQuartzTask {
	String[] configLocations = new String[]{"spring-mybatis.xml","spring-mvc.xml"};
	ApplicationContext ac = new ClassPathXmlApplicationContext(configLocations);
	WarnCarInfoMapper warnCarInfoMapper=(WarnCarInfoMapper)ac.getBean("warnCarInfoMapper");
	SysPalceInfoMapper  sysPalceInfoMapper=(SysPalceInfoMapper)ac.getBean("sysPalceInfoMapper");
	SysRuleListMapper sysRuleListMapper=(SysRuleListMapper)ac.getBean("sysRuleListMapper");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void doCronTask(){
    	List<SysPalceInfo> sysPalceInfoList=sysPalceInfoMapper.selectSysPalceInfoByParams(new SysPalceInfo());
    	Map<String,Object> para_map=new HashMap<String, Object>();
		para_map.put("listTypeId", 10);
		para_map.put("listStatus", "Y");
		List<SysRuleList> sysRuleList=sysRuleListMapper.selectSysRuleListByMap(para_map);
    	/* 当前时间前X分钟获取 */
    	Calendar beforeTime = Calendar.getInstance();
    	String endTime = sdf.format(beforeTime.getTime()); //当前时间
    	System.out.println("doCronTask正在运行..."+beforeTime);
    	//for(SysPalceInfo sysPalceInfo:sysPalceInfoList) {
    	for(SysRuleList data:sysRuleList) {//车队出行设置的规则
    		if(!StringUtils.isEmpty(data.getListValueFir())&&!StringUtils.isEmpty(data.getListValueSec())) {
    			int minute=Integer.parseInt(data.getListValueFir());
    			int carTotal=Integer.parseInt(data.getListValueSec());
    			beforeTime.add(Calendar.MINUTE, -minute);// X分钟之前的时间
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
    			//循环点位信息
    			for(SysPalceInfo sysPalceInfo:sysPalceInfoList) {
    				int count=0;
    				String carnum="";
    				List<WarnCarInfo> warnCarList_palce=new ArrayList<WarnCarInfo>();
    				for(WarnCarInfo warnCarInfo:warnCarList) {
    					//当前点位且未统计的车牌（避免重复），并将数据放到新的集合中
        				if(sysPalceInfo.getPalceNo().equals(warnCarInfo.getWarnCPlace())) {
        					if(!carnum.contains(warnCarInfo.getWarnCNum())) {
        						carnum+=warnCarInfo.getWarnCNum()+",";
        						count++;
        					}
        					warnCarList_palce.add(warnCarInfo);
        				}
        			}
    				
    				if(null!=warnCarList_palce&&count>=carTotal) {//数量大于等于预定数量时，将当前时间范围内所有车辆赋分
    					for(WarnCarInfo warnCarInfo:warnCarList_palce) {
    						//if(sysPalceInfo.getPalceNo().equals(warnCarInfo.getWarnCPlace())){
	    						if(StringUtils.isEmpty(warnCarInfo.getWarnItem())||!warnCarInfo.getWarnItem().contains("10")) {
	    							int score=warnCarInfo.getWarnCTotal()+data.getListValueScore();
	        						String items="";
	        						String itemNames="";
	        						String itemLists="";
	        						if(StringUtils.isEmpty(warnCarInfo.getWarnItem())) {
	        							items="10";
	        						}else {
	        							items=warnCarInfo.getWarnItem()+",10";
	        						}
	        						if(StringUtils.isEmpty(warnCarInfo.getWarnCUdf2())) {
	        							itemNames="车队出行";
	        						}else {
	        							 itemNames=warnCarInfo.getWarnCUdf2()+",车队出行";
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
    						//}
    						
    					}
    				}
    			}
    		}
    	}
    	
    }
}
