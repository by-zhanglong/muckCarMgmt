package com.bayee.controller;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bayee.model.SysRuleList;
import com.bayee.model.SysRuleType;
import com.bayee.service.CarService;
import com.bayee.service.SysRuleTypeService;
@CrossOrigin()
@Controller
@RequestMapping("/rule")
public class RuleController {
	Logger logger=Logger.getLogger(this.getClass());
	@Autowired
	private SysRuleTypeService sysRuleTypeService;
	@Autowired
	private CarService carService;
	
	@RequestMapping(value="/getTypeList",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getRuleType(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_map=new HashMap<String, Object>();
		try {
			//用获取数据流的方式，直接获取post过来的所有数据流
			StringBuffer info=new java.lang.StringBuffer();
			InputStream in=httpReq.getInputStream();
		    BufferedInputStream buf=new BufferedInputStream(in);
		    byte[] buffer=new byte[1024];
		    int iRead;
		    while((iRead=buf.read(buffer))!=-1)
		    {
		     info.append(new String(buffer,0,iRead,"UTF-8"));
		    }
		    String jsonData=info.toString();
		    if(!StringUtils.isEmpty(jsonData)) {
		    	JSONObject jSONObject=JSON.parseObject(jsonData);
			    String warnCNum=(String) jSONObject.get("warnCNum");//车牌
				String warnCTimeStart=(String) jSONObject.get("warnCTimeStart");//开始时间
				String warnCTimeEnd=(String) jSONObject.get("warnCTimeEnd");//结束时间
				Integer warnCPlace=(Integer) jSONObject.get("warnCPlace");//点位信息
				String warnCColor=(String) jSONObject.get("warnCColor");//颜色
				boolean warnInvalidNum=jSONObject.getBoolean("warnInvalidNum");//是否排除无效车牌
				if(!StringUtils.isEmpty(warnCNum)){
					para_map.put("warnCNum", warnCNum);
				}
				if(!StringUtils.isEmpty(warnCTimeStart)){
					para_map.put("warnCTimeStart", warnCTimeStart);
				}
				if(!StringUtils.isEmpty(warnCTimeEnd)){
					para_map.put("warnCTimeEnd", warnCTimeEnd);
				}
				if(!StringUtils.isEmpty(warnCPlace)){
					para_map.put("warnCPlace", warnCPlace);
				}
				if(!StringUtils.isEmpty(warnCColor)){
					para_map.put("warnCColor", warnCColor);
				}
				if(!warnInvalidNum) {
					para_map.put("isActive", "Y");
				}
				para_map.put("isNullWarnCplace", "Y");
		    }
			List<SysRuleType> sysRuleTypeList = sysRuleTypeService.selectAllData();
			List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
			for(SysRuleType sysRuleType:sysRuleTypeList) {
				Map<String,Object> dataMap=new HashMap<String, Object>();
				dataMap.put("typeId", sysRuleType.getTypeId());
				dataMap.put("typeName", sysRuleType.getTypeName());
				 if(!StringUtils.isEmpty(jsonData)) {
					 para_map.put("warnItem", sysRuleType.getTypeId()+",");
					 dataMap.put("count", carService.selectWarnCarCountByParamMap(para_map));
				 }
				dataList.add(dataMap);
			}
			res_data.put("data", dataList);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		logger.debug("/rule/getTypeList"+res_data);
		return JSONObject.toJSONString(res_data);
	}
	
	@RequestMapping(value="/addRuleList",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addRuleList(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		try {
			//用获取数据流的方式，直接获取post过来的所有数据流
			StringBuffer info=new java.lang.StringBuffer();
			InputStream in=httpReq.getInputStream();
		    BufferedInputStream buf=new BufferedInputStream(in);
		    byte[] buffer=new byte[1024];
		    int iRead;
		    while((iRead=buf.read(buffer))!=-1)
		    {
		     info.append(new String(buffer,0,iRead,"UTF-8"));
		    }
		    String jsonData=info.toString();
			//String jsonData=httpReq.getParameter("jsonData");
			List<SysRuleList> SysRuleData=JSONArray.parseArray(jsonData, SysRuleList.class);
			sysRuleTypeService.deleteAllSysRuleList();
			for(SysRuleList sysRuleList:SysRuleData) {
				sysRuleTypeService.insertSysRuleListSelective(sysRuleList);
			}
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		logger.debug("/rule/addRuleList"+res_data);
		return JSONObject.toJSONString(res_data);
	}
	
	@RequestMapping(value="/getRuleList",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getRuleList() {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
		para_data.put("listStatus", "Y");
		try {
			List<SysRuleList> sysRuleTypeList = sysRuleTypeService.selectSysRuleListByMap(para_data);
			if(null==sysRuleTypeList||sysRuleTypeList.size()==0) {
				sysRuleTypeList=new ArrayList<SysRuleList>();
				List<SysRuleType> sysRuleTypeData = sysRuleTypeService.selectAllData();
				for(SysRuleType sysRuleType:sysRuleTypeData) {
					SysRuleList sysRuleList=new SysRuleList();
					sysRuleList.setListTypeId(sysRuleType.getTypeId());
					sysRuleTypeList.add(sysRuleList);
				}
			}
			res_data.put("data", sysRuleTypeList);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		logger.debug("/rule/getRuleList"+res_data);
		return JSONObject.toJSONString(res_data);
	}
}
