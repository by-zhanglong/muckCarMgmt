package com.bayee.controller;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bayee.dahua.KeepLogin;
import com.bayee.dahua.utils.BaseUserInfo;
import com.bayee.dahua.utils.HttpEnum;
import com.bayee.dahua.utils.HttpTestUtils;
import com.bayee.model.CarControlInfo;
import com.bayee.model.CarEarlyWarning;
import com.bayee.model.SysCarInfo;
import com.bayee.model.SysRuleList;
import com.bayee.model.WarnCarInfo;
import com.bayee.service.CarService;
import com.bayee.service.SysRuleTypeService;
import com.bayee.util.ImportExcelUtil;
@CrossOrigin()
@Controller
@RequestMapping("/car")
public class CarController {
	@Autowired
	private CarService carService;
	@Autowired
	private KeepLogin keepLogin;
	@Autowired
	private SysRuleTypeService sysRuleTypeService;
	
	private String img_host="http://41.225.9.47:8314";
	/**
	 * 渣土车辆显示列表
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getWarnCarList",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getWarnCarList(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
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
		    JSONObject jSONObject=JSON.parseObject(jsonData);
		    String warnCNum=(String) jSONObject.get("warnCNum");//车牌
			String warnCTimeStart=(String) jSONObject.get("warnCTimeStart");//开始时间
			String warnCTimeEnd=(String) jSONObject.get("warnCTimeEnd");//结束时间
			Integer warnCPlace=(Integer) jSONObject.get("warnCPlace");//点位信息
			String warnCColor=(String) jSONObject.get("warnCColor");//颜色
			Object warnItemObj=jSONObject.get("warnItem");//赋分项
			int curPageInt=(Integer) jSONObject.get("curPage");//当前页数
			int pageSizeInt=(Integer) jSONObject.get("pageSize");//每页条数
			String warnSort=(String) jSONObject.get("warnSort");//排序字段
			boolean warnInvalidNum=jSONObject.getBoolean("warnInvalidNum");//是否排除无效车牌
			int start=(curPageInt-1)*pageSizeInt;
			para_data.put("start", start);
			para_data.put("pageSize", pageSizeInt);
			if(!StringUtils.isEmpty(warnCNum)){
				para_data.put("warnCNum", warnCNum);
			}
			if(!StringUtils.isEmpty(warnCTimeStart)){
				para_data.put("warnCTimeStart", warnCTimeStart);
			}
			if(!StringUtils.isEmpty(warnCTimeEnd)){
				para_data.put("warnCTimeEnd", warnCTimeEnd);
			}
			if(!StringUtils.isEmpty(warnCPlace)){
				para_data.put("warnCPlace", warnCPlace);
			}
			if(!StringUtils.isEmpty(warnCColor)){
				para_data.put("warnCColor", warnCColor);
			}
			if(!warnInvalidNum) {
				para_data.put("isActive", "Y");
			}
			if(StringUtils.isEmpty(warnSort)) {
				para_data.put("warnSort", "0");
			}else {
				para_data.put("warnSort", warnSort);
			}
			String warnItem="";
			if(null!=warnItemObj) {
				warnItem=warnItemObj.toString();
			}
			if(!StringUtils.isEmpty(warnItem)){
				/*if(warnItem.indexOf(",")>0) {
					String warnItemStr="";
					for(String data:warnItem.split(",")) {
						warnItemStr+="INSTR(warn_item,'"+data+"')>0 or ";
					}
					if(warnItemStr.indexOf("or")>0){
						warnItemStr=warnItemStr.substring(0,warnItemStr.lastIndexOf("or"));
					}
					para_data.put("warnItemStr", warnItemStr);
				}else {*/
					para_data.put("warnItem", warnItem+",");
				//}
				
			}
			para_data.put("isNullWarnCplace", "Y");
			List<WarnCarInfo> warnCarList = carService.selectWarnCarByParamMap(para_data);
			for(WarnCarInfo warnCarInfo:warnCarList) {
				if(!warnCarInfo.getWarnCUdf1().startsWith("http")) {
					String img_url=img_host+warnCarInfo.getWarnCUdf1();
					warnCarInfo.setWarnCUdf1(img_url);
				}
			}
			int total=carService.selectWarnCarCountByParamMap(para_data);
			res_data.put("data", warnCarList);
			res_data.put("total", total);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 渣土车辆显示列表
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getEarlyWarnCarList",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getEarlyWarnCarList(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
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
		    JSONObject jSONObject=JSON.parseObject(jsonData);
		    String keyWord=(String) jSONObject.get("keyWord");//关键字（车牌、点位信息）
			Object warnItemObj=jSONObject.get("ruleListType");//赋分项
			int curPageInt=(Integer) jSONObject.get("curPage");//当前页数
			int pageSizeInt=(Integer) jSONObject.get("pageSize");//每页条数
			//String warnSort=(String) jSONObject.get("warnSort");//排序字段
			boolean warnInvalidNum=false;
			int start=(curPageInt-1)*pageSizeInt;
			para_data.put("start", start);
			para_data.put("pageSize", pageSizeInt);
			if(!StringUtils.isEmpty(keyWord)){
				para_data.put("keyWord", keyWord);
			}
			if(!warnInvalidNum) {
				para_data.put("isActive", "Y");
			}
			String warnItem="";
			if(null!=warnItemObj) {
				warnItem=warnItemObj.toString();
			}
			if(!StringUtils.isEmpty(warnItem)){
				/*if(warnItem.indexOf(",")>0) {
					String warnItemStr="";
					for(String data:warnItem.split(",")) {
						warnItemStr+="INSTR(warn_item,'"+data+"')>0 or ";
					}
					if(warnItemStr.indexOf("or")>0){
						warnItemStr=warnItemStr.substring(0,warnItemStr.lastIndexOf("or"));
					}
					para_data.put("warnItemStr", warnItemStr);
				}else {*/
					para_data.put("warnItem", warnItem+",");
				//}
				
			}
			List<WarnCarInfo> warnCarList = carService.selectEarlyWarnCarByParamMap(para_data);
			for(WarnCarInfo warnCarInfo:warnCarList) {
				if(!warnCarInfo.getWarnCUdf1().startsWith("http")) {
					String img_url=img_host+warnCarInfo.getWarnCUdf1();
					warnCarInfo.setWarnCUdf1(img_url);
				}
				if(null!=warnCarInfo.getWarnCUdf2()&&warnCarInfo.getWarnCUdf2().contains(",")) {
					String[] typeArr=warnCarInfo.getWarnCUdf2().split(",");
					String types="";
					for(String  type:typeArr) {
						if(!StringUtils.isEmpty(type)&&!types.contains(type)) {//类型不为空且类型统计字符串中不包含
							types+=type+",";
						}
					}
					if(types.indexOf(",")>0) {
						types=types.substring(0,types.lastIndexOf(","));
					}
					warnCarInfo.setWarnCUdf2(types);
				}
				if(null==warnCarInfo.getControlId()) {
					warnCarInfo.setControlId(0);
				}
			}
			int total=carService.selectEarlyWarnCarCountByParamMap(para_data);
			if(total>=100) {
				total=100;
			}
			res_data.put("data", warnCarList);
			res_data.put("total", total);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	/**
	 * 渣土车辆详情
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getWarnCarInfor",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getWarnCarInfor(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
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
		    JSONObject jSONObject=JSON.parseObject(jsonData);
		    String warnCNum=(String) jSONObject.get("warnCNum");//车牌
			String warnCTime=(String) jSONObject.get("warnCTime");//开始时间
			String keyWord=httpReq.getParameter("keyWord");//关键字
			int curPageInt=(Integer) jSONObject.get("curPage");//当前页数
			int pageSizeInt=(Integer) jSONObject.get("pageSize");//每页条数
			int start=(curPageInt-1)*pageSizeInt;
			para_data.put("start", start);
			para_data.put("pageSize", pageSizeInt);
			if(!StringUtils.isEmpty(warnCNum)){
				para_data.put("warnCNum", warnCNum);
			}
			if(!StringUtils.isEmpty(warnCTime)){
				para_data.put("warnCTime", warnCTime);
			}
			
			if(!StringUtils.isEmpty(keyWord)){
				para_data.put("keyWord", keyWord);
			}
			para_data.put("score", "Y");
			List<WarnCarInfo> warnCarList = carService.selectWarnCarScoreByParamMap(para_data);
			for(WarnCarInfo warnCarInfo:warnCarList) {
				if(!warnCarInfo.getWarnCUdf1().startsWith("http")) {
					String img_url=img_host+warnCarInfo.getWarnCUdf1();
					warnCarInfo.setWarnCUdf1(img_url);
				}
			}
			 int total=carService.selectWarnCarScoreCountByParamMap(para_data);
			 para_data.remove("score");
			 
			 //调用大华接口查询车辆最近10天最新的5条轨迹信息
			 String action = "/multiDataMiningService/rest/track/queryTrack";
			 Map<String,Object> para_map=new HashMap<String, Object>();
			 Date endDate=new Date();
			 Calendar c = Calendar.getInstance();
	         c.add(Calendar.DATE, -10);
	         Date startDate=c.getTime();
	         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	         if(warnCNum.contains("挂")) {
	        	 para_map.put("objId", warnCNum+"_15");//车牌号+车辆类型
	         }else {
	        	 para_map.put("objId", warnCNum+"_01");//车牌号+车辆类型
	         }
			 para_map.put("objType", 1);//数据类型 1是车辆 6是人员
			 para_map.put("startTime", df.format(startDate));
			 para_map.put("endTime", df.format(endDate));
			 para_map.put("advance", true);
			 para_map.put("pageSize", 10);
			 para_map.put("currentPage", 1);
			 String content=JSONObject.toJSONString(para_map);
			 String token=keepLogin.getToken();
			 String resData=HttpTestUtils.httpRequest(HttpEnum.POST, keepLogin.ip, Integer.parseInt(keepLogin.port), action, token, content);
			 //解析接口返回的数据并封装成前台展示的数据格式
			 //JSONObject jSONOData=JSON.parseObject(resData);
			 //JSONObject jSONOData_data=jSONOData.getJSONObject("data");
			 List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
			 //if(null!=jSONOData_data) {
				// JSONArray jSONOData_data_dataList=jSONOData_data.getJSONArray("dataList");
				 //if(null!=jSONOData_data_dataList) {
					// for(int i=0;i<jSONOData_data_dataList.size();i++) {
						 //Map<String,Object> data_map=new HashMap<String, Object>();
							//data_map.put("channelName", jSONOData_data_dataList.getJSONObject(i).get("channelName"));//通道名称
							//data_map.put("capTime", jSONOData_data_dataList.getJSONObject(i).get("capDateStr"));//抓拍时间
							//String img_url="";
							//if(null!=jSONOData_data_dataList.getJSONObject(i).getString("imgUrl")) {
							//img_url=jSONOData_data_dataList.getJSONObject(i).getString("imgUrl");
							//	if(!img_url.startsWith("http")) {
									//img_url=img_host+img_url;
							//	}
							//}
							
							//data_map.put("imgUrl", img_url);
							//data_map.put("plateNum", warnCNum);
							//data_map.put("channelX", jSONOData_data_dataList.getJSONObject(i).getDouble("gpsX"));
							//data_map.put("channelY", jSONOData_data_dataList.getJSONObject(i).getDouble("gpsY"));
							//dataList.add(data_map);
					 //}
				 //}
			 //}
			 
			List<WarnCarInfo> recordList = carService.selectWarnCarByParamMap(para_data);
			//List<StorgeEvent> storgeEventList=carService.selectStorgEventByplateNum(warnCNum);
			
			  for(WarnCarInfo warnCarInfo:recordList) {
				  Map<String,Object> data_map=new HashMap<String, Object>(); 
				  data_map.put("channelName",warnCarInfo.getChannelName()); 
				  data_map.put("capTime", warnCarInfo.getWarnCTime());
				   if(!warnCarInfo.getWarnCUdf1().startsWith("http")) { 
					   String img_url=img_host+warnCarInfo.getWarnCUdf1();
					   warnCarInfo.setWarnCUdf1(img_url); 
				   } 
				   data_map.put("imgUrl",warnCarInfo.getWarnCUdf1()); 
				   data_map.put("plateNum",warnCarInfo.getWarnCNum()); 
				   data_map.put("channelX",warnCarInfo.getChannelX()); 
				   data_map.put("channelY",warnCarInfo.getChannelY());
				   dataList.add(data_map);
			  }
			para_data.clear();
			para_data.put("warnCNum", warnCNum);
			para_data.put("score", "Y");
			List<WarnCarInfo> warnCarInfoList=carService.selectWarnCarByParamMap(para_data);
			int sumScore=0;
			for(WarnCarInfo warnCarInfo :warnCarInfoList) {
				if(null!=warnCarInfo.getWarnCTotal()) {
					sumScore+=warnCarInfo.getWarnCTotal();
				}
			}
			para_data.clear();
			para_data.put("cCarNum", warnCNum);
			List <CarControlInfo > carControlInfoList=carService.selectCarControlInfoByParamMap(para_data);
			boolean isControl=false;
			if(null!=carControlInfoList&&carControlInfoList.size()>0) {
				isControl=true;
			}
			res_data.put("warnDetailList", warnCarList);
			res_data.put("warnDetailTotal", total);
			res_data.put("recordList", dataList);
			res_data.put("sumScore", sumScore);
			res_data.put("isControl", isControl);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	/**
	 * 白名单/黑名单的列表
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getSysCarList",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getSysCarList(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
		String carType=httpReq.getParameter("carType");//类型
		String keyWord=httpReq.getParameter("keyWord");//搜索关键字
		String curPage=httpReq.getParameter("curPage");//当前页数
		String pageSize=httpReq.getParameter("pageSize");//每页条数
		if(!StringUtils.isEmpty(carType)){
			para_data.put("carType", carType);
		}
		if(!StringUtils.isEmpty(keyWord)){
			para_data.put("keyWord", keyWord);
		}
		int curPageInt=1;
		int pageSizeInt=10;
		if(!StringUtils.isEmpty(curPage)){
			curPageInt=Integer.parseInt(curPage);
		}
		if(!StringUtils.isEmpty(pageSize)){
			pageSizeInt=Integer.parseInt(pageSize);
			
		}
		int start=(curPageInt-1)*pageSizeInt;
		para_data.put("start", start);
		para_data.put("pageSize", pageSizeInt);
		try {
			List<SysCarInfo> warnCarList = carService.selectSysCarListByParamMap(para_data);
			for(SysCarInfo sysCarInfo:warnCarList) {
				if(null==sysCarInfo.getConId()) {
					sysCarInfo.setConId(Long.parseLong(0+""));
				}
			}
			int total=carService.selectSysCarCountByParamMap(para_data);
			res_data.put("data", warnCarList);
			res_data.put("total", total);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 白名单/黑名单的新增
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/addSysCar",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addSysCar(HttpServletRequest httpReq) {
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
			String jsonData=info.toString();//httpReq.getParameter("jsonData");
			SysCarInfo sysCarInfo=JSON.parseObject(jsonData,SysCarInfo.class);
			carService.insertSysCarSelective(sysCarInfo);
			if(1==sysCarInfo.getCarType()) {
				WarnCarInfo warnCarInfo=new WarnCarInfo();
				warnCarInfo.setWarnCNum(sysCarInfo.getCarNum());
				warnCarInfo.setWarnItem(11+"");
				Map<String,Object> para_map=new HashMap<String, Object>();
				para_map.put("listTypeId", 11);
				para_map.put("listStatus", "Y");
				List<SysRuleList> sysRuleList=sysRuleTypeService.selectSysRuleListByMap(para_map);
				if(null!=sysRuleList&&sysRuleList.size()>0) {
					warnCarInfo.setWarnCTotal(sysRuleList.get(0).getListValueScore());
					warnCarInfo.setWarnCUdf2("黑名单");
					warnCarInfo.setWarnCUdf3(sysRuleList.get(0).getListId()+"");
				}
				carService.insertWarnCarInfoSelective(warnCarInfo);
			}
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 白名单/黑名单的删除
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/deleteSysCar",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteSysCar(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		try {
				//用获取数据流的方式，直接获取post过来的所有数据流
			String carId=httpReq.getParameter("carId");
			int carIdInt=0;
			if(null!=carId&&""!=carId) {
				carIdInt=Integer.parseInt(carId);
			}
			SysCarInfo sysCarInfo=carService.getSysCarInfo(carIdInt);
			if(1==sysCarInfo.getCarType()) {
				Map<String,Object> para_map=new HashMap<String, Object>();
				para_map.put("warnItem", "11");
				para_map.put("warnCNum", sysCarInfo.getCarNum());
				carService.deleteWarnCarInfoByMap(para_map);
			}
			carService.deleteSysCarByCarId(carIdInt);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	/**
	 * 獲取白名单/黑名单相信信息
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getSysCarInfo",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getSysCarInfo(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		try {
			String carId=httpReq.getParameter("carId");
			int carIdInt=0;
			if(null!=carId&&""!=carId) {
				carIdInt=Integer.parseInt(carId);
			}
			SysCarInfo sysCarInfo=carService.getSysCarInfo(carIdInt);
			if(2==sysCarInfo.getCarType()) {//红名单车辆查询是否布控
				Map<String,Object> para_map=new HashMap<String, Object>();
				para_map.put("cCarNum", sysCarInfo.getCarNum());
				List <CarControlInfo > carControlInfoList=carService.selectCarControlInfoByParamMap(para_map);
				if(null!=carControlInfoList&&carControlInfoList.size()>0) {
					sysCarInfo.setConId(carControlInfoList.get(0).getcId());
				}else {
					sysCarInfo.setConId(Long.parseLong("0"));
				}
				
			}
			res_data.put("res_code", 0);
			res_data.put("data", sysCarInfo);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 白名单/黑名单的修改
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/updateSysCar",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateSysCar(HttpServletRequest httpReq) {
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
			SysCarInfo sysCarInfo=JSON.parseObject(jsonData,SysCarInfo.class);
			carService.updateSysCarByPrimaryKeySelective(sysCarInfo);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 刷新数据
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/changeData",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String changeWarnCarInfor() {
		Map<String,Object> res_data=new HashMap<String, Object>();
		try {
			carService.changeWarnCarInfor();
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 根据车牌获取车辆信息
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getCarInforByNum",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCarInforByNum(HttpServletRequest httpReq) {
		 String warnCNum=httpReq.getParameter("warnCNum");
		 String action = "/multiDataMiningService/rest/dossier/queryByCar";
		 Map<String,Object> para_map=new HashMap<String, Object>();
		 para_map.put("objId", warnCNum+"_02");
		 para_map.put("objType", 1);
		 String content="?objId="+warnCNum+"_01"+"&objType=1";
		 String token=keepLogin.getToken();
		 String resData=HttpTestUtils.httpRequest(HttpEnum.GET, keepLogin.ip, Integer.parseInt(keepLogin.port), action, token, content);
		 return resData;
	}
	
	/**
	 * 车辆布控
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/addCarControlByNum",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addCarControlByNum(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		 try {
			 StringBuffer info=new java.lang.StringBuffer();
			 	InputStream in=httpReq.getInputStream();
			 	BufferedInputStream buf=new BufferedInputStream(in);
			    byte[] buffer=new byte[1024];
			    int iRead;
			    while((iRead=buf.read(buffer))!=-1)
			    {
			     info.append(new String(buffer,0,iRead,"UTF-8"));
			    }
			String jsonReqData=info.toString();
			JSONObject jSONObject=JSON.parseObject(jsonReqData);
			String warnCNum=(String) jSONObject.get("warnCNum");//车牌
			String action = "/multiDataMiningService/rest/repositoryMember/add";
			//List<Map<String, Object>> req_data = new ArrayList<Map<String, Object>>();
			Map<String, Object> para_map = new HashMap<String, Object>();
			para_map.put("repositoryCode", BaseUserInfo.repositoryCode);
			para_map.put("repositoryType", 1);
			if(warnCNum.contains("挂")) {
				para_map.put("plateType", "15");
			}else {
				para_map.put("plateType", "01");
			}
			List<Object> memmemberRel=new ArrayList<Object>();
			String name = keepLogin.getName();
			if(StringUtils.isEmpty(name)) {
				name="市局演示用户";
			}
			para_map.put("responPerson", name);
			para_map.put("idType", "111");
			para_map.put("memmemberRel", memmemberRel);
			para_map.put("isSync", 0);
			para_map.put("plateNum", warnCNum);
			//req_data.add(para_map);
			String content = JSONObject.toJSONString(para_map);
			String token = keepLogin.getToken();
			String resData = HttpTestUtils.httpRequest(HttpEnum.POST, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
					action, token, content);
			if (!StringUtils.isEmpty(resData)) {
				JSONObject jsonData = JSON.parseObject(resData);
				if ("200".equals(jsonData.getString("code"))) {
					JSONObject jsonResults = jsonData.getJSONObject("results");
					String memberCode = jsonResults.getString("memberCode");
					para_map.clear();
					action = "/multiDataMiningService/rest/repositoryMember/approval";
					para_map.put("repositoryCode", BaseUserInfo.repositoryCode);
					para_map.put("repositoryType", 1);
					para_map.put("approvalType", 1);
					List<String> memberCodeList = new ArrayList<String>();
					memberCodeList.add(memberCode);
					para_map.put("memberCodeList", memberCodeList);
					content = JSONObject.toJSONString(para_map);
					HttpTestUtils.httpRequest(HttpEnum.PUT, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
							action, token, content);
					if(!StringUtils.isEmpty(httpReq.getHeader("token"))) {
						token=httpReq.getHeader("token");
						 token=token.replace(" ","+");
					}
					action="/ras/user/info";
					content="";
					resData = HttpTestUtils.httpRequest(HttpEnum.GET, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
							action, token, content);
					CarControlInfo carControlInfo = new CarControlInfo();
					if (!StringUtils.isEmpty(resData)) {
						jsonData = JSON.parseObject(resData);
						carControlInfo.setCreatBy(jsonData.getString("userName"));
						carControlInfo.setCreateByName(jsonData.getString("name"));
					}
					carControlInfo.setcCarNum(warnCNum);
					carControlInfo.setcObjCode(memberCode);
					carControlInfo.setcStatus(0);
					carControlInfo.setCreateTime(new Date());
					carService.addCarControlInfo(carControlInfo);
					res_data.put("res_code", 0);
				}else {
					res_data.put("res_code", 1);
					res_data.put("errmsg", "布控失败");
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "布控失败");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	
	/**
	 * 车辆批量布控
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/batchAddCarControlByNum",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String batchAddCarControlByNum(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		 try {
			 	StringBuffer info=new java.lang.StringBuffer();
			 	InputStream in=httpReq.getInputStream();
			 	BufferedInputStream buf=new BufferedInputStream(in);
			    byte[] buffer=new byte[1024];
			    int iRead;
			    while((iRead=buf.read(buffer))!=-1)
			    {
			     info.append(new String(buffer,0,iRead,"UTF-8"));
			    }
			    String jsonReqData=info.toString();
			    JSONObject jSONObject=JSON.parseObject(jsonReqData);
			    String warnCNums=(String) jSONObject.get("warnCNums");//车牌
			    String[] warnCNumArr=warnCNums.split(",");
				String action = "/multiDataMiningService/rest/repositoryMember/batchAdd";
				List<Map<String, Object>> req_data = new ArrayList<Map<String, Object>>();
				if(null!=warnCNumArr&&warnCNumArr.length>0) {
					for(String warnCNum:warnCNumArr) {
						Map<String, Object> para_map = new HashMap<String, Object>();
						para_map.put("repositoryCode", BaseUserInfo.repositoryCode);//布控库编码
						para_map.put("repositoryType", 1);//布控库类型
						if(warnCNum.contains("挂")) {//车辆类型
							para_map.put("plateType", "01");
						}else {
							para_map.put("plateType", "15");
						}
						para_map.put("plateNum", warnCNum);//车牌号码
						String name = keepLogin.getName();//责任人
						if(StringUtils.isEmpty(name)) {
							name="市局演示用户";
						}
						para_map.put("responPerson", name);
						para_map.put("idType", "111");
						req_data.add(para_map);
					}
				}
				Map<String, Object> send_map = new HashMap<String, Object>();
				send_map.put("memberList", req_data);
				String content = JSONObject.toJSONString(send_map);
				String token = keepLogin.getToken();
				String resData = HttpTestUtils.httpRequest(HttpEnum.POST, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
						action, token, content);
				if (!StringUtils.isEmpty(resData)) {
					JSONObject jsonData = JSON.parseObject(resData);
					if ("200".equals(jsonData.getString("code"))) {
						action="/multiDataMiningService/rest/repositoryMember/queryByStatus";
						send_map.clear();
						send_map.put("repositoryCode", BaseUserInfo.repositoryCode);//布控库编码
						send_map.put("repositoryType", 1);//布控库类型
						send_map.put("status", 10);//查询待审核布控车辆信息
						send_map.put("start", 1);//数据的起始位置
						send_map.put("limit", 2000);//数据的起始位置
						content = JSONObject.toJSONString(send_map);
						resData=HttpTestUtils.httpRequest(HttpEnum.POST, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
								action, token, content);
						jsonData=JSON.parseObject(resData);
						JSONObject jsonResults = jsonData.getJSONObject("results");
						JSONArray dataList = jsonResults.getJSONArray("list");
						List<String> memberCodeList = new ArrayList<String>();
						if(null!=dataList) {
							for(int i=0;i<dataList.size();i++) {
								memberCodeList.add(dataList.getJSONObject(i).getString("memberCode"));
							}
						}
						send_map.clear();
						action = "/multiDataMiningService/rest/repositoryMember/approval";
						send_map.put("repositoryCode", BaseUserInfo.repositoryCode);
						send_map.put("repositoryType", 1);
						send_map.put("approvalType", 1);
						send_map.put("memberCodeList", memberCodeList);
						content = JSONObject.toJSONString(send_map);
						HttpTestUtils.httpRequest(HttpEnum.PUT, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
								action, token, content);
						//获取操作人和操作数据库
						if(!StringUtils.isEmpty(httpReq.getHeader("token"))) {
							token=httpReq.getHeader("token");
							 token=token.replace(" ","+");
						}
						action="/ras/user/info";
						content="";
						resData = HttpTestUtils.httpRequest(HttpEnum.GET, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
								action, token, content);
						//插入数据
						String createBy="";
						String createByName="";
						if (!StringUtils.isEmpty(resData)) {
							jsonData = JSON.parseObject(resData);
							createBy=jsonData.getString("userName");
							createByName=jsonData.getString("name");
						}
						if(null!=dataList) {
							for(int i=0;i<dataList.size();i++) {
								CarControlInfo carControlInfo = new CarControlInfo();
								carControlInfo.setcCarNum(dataList.getJSONObject(i).getString("plateNum"));//获取车牌号码
								carControlInfo.setcObjCode(dataList.getJSONObject(i).getString("memberCode"));//获取布控库中成员编码
								carControlInfo.setcStatus(0);
								carControlInfo.setCreatBy(createBy);
								carControlInfo.setCreateByName(createByName);
								carControlInfo.setCreateTime(new Date());
								carService.addCarControlInfo(carControlInfo);
							}
						}
					}
				} 
				res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "布控失败");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 车辆撤控
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/revokeCarControlByNum",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String revokeCarControlByNum(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		 try {
			 	StringBuffer info=new java.lang.StringBuffer();
			 	InputStream in=httpReq.getInputStream();
			 	BufferedInputStream buf=new BufferedInputStream(in);
			    byte[] buffer=new byte[1024];
			    int iRead;
			    while((iRead=buf.read(buffer))!=-1)
			    {
			     info.append(new String(buffer,0,iRead,"UTF-8"));
			    }
			    String jsonReqData=info.toString();
			    JSONObject jSONObject=JSON.parseObject(jsonReqData);
			    int cId=jSONObject.getInteger("cId");//布控id
			    String action = "/multiDataMiningService/rest/repositoryMember/delete";
			    CarControlInfo carControlInfo=carService.getCarControlInfo(Long.parseLong(cId+""));
				Map<String, Object> para_map = new HashMap<String, Object>();
				para_map.put("repositoryCode", BaseUserInfo.repositoryCode);
				para_map.put("repositoryType", 1);
				List<String> memberCodeList = new ArrayList<String>();
				memberCodeList.add(carControlInfo.getcObjCode());
				para_map.put("memberCodeList", memberCodeList);
				String content = JSONObject.toJSONString(para_map);
				String token = keepLogin.getToken();
				String resData = HttpTestUtils.httpRequest(HttpEnum.DELETE, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
						action, token, content);
			if (!StringUtils.isEmpty(resData)) {
				JSONObject jsonData = JSON.parseObject(resData);
				if ("200".equals(jsonData.getString("code"))) {
					action = "/multiDataMiningService/rest/repositoryMember/approval";
					para_map.put("approvalType", 1);
					content = JSONObject.toJSONString(para_map);
					HttpTestUtils.httpRequest(HttpEnum.PUT, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
							action, token, content);
					
					//获取操作人和操作数据库
					if(!StringUtils.isEmpty(httpReq.getHeader("token"))) {
						token=httpReq.getHeader("token");
						 token=token.replace(" ","+");
					}
					action="/ras/user/info";
					content="";
					resData = HttpTestUtils.httpRequest(HttpEnum.GET, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
							action, token, content);
					String createBy="";
					String createByName="";
					if (!StringUtils.isEmpty(resData)) {
						jsonData = JSON.parseObject(resData);
						createBy=jsonData.getString("userName");
						createByName=jsonData.getString("name");
					}
					para_map.clear();
					para_map.put("cStatus", 1);
					para_map.put("updateTime", new Date());
					para_map.put("updateBy", createBy);
					para_map.put("updateByName", createByName);
					para_map.put("whcObjCode", carControlInfo.getcObjCode());
					carService.updateCarControlInfoByMap(para_map);
				}
			} 
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "撤控失败！");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	
	
	/**
	 * 车辆批量撤控
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/batchRevokeCarControlByNum",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String batchRevokeCarControlByNum(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		 try {
			 StringBuffer info=new java.lang.StringBuffer();
			 	InputStream in=httpReq.getInputStream();
			 	BufferedInputStream buf=new BufferedInputStream(in);
			    byte[] buffer=new byte[1024];
			    int iRead;
			    while((iRead=buf.read(buffer))!=-1)
			    {
			     info.append(new String(buffer,0,iRead,"UTF-8"));
			    }
			    String jsonReqData=info.toString();
			    JSONObject jSONObject=JSON.parseObject(jsonReqData);
			    String cIds=jSONObject.getString("cIds");//布控id
			   if(null!=cIds&&!"".equals(cIds)) {
				String[] idArr=cIds.split(",");
				List<String> memberCodeList = new ArrayList<String>();
				for(String idStr:idArr) {
					CarControlInfo carControlInfo=carService.getCarControlInfo(Long.parseLong(idStr));
					memberCodeList.add(carControlInfo.getcObjCode());
				}
				String action = "/multiDataMiningService/rest/repositoryMember/delete";
				Map<String, Object> para_map = new HashMap<String, Object>();
				para_map.put("repositoryCode", BaseUserInfo.repositoryCode);
				para_map.put("repositoryType", 1);
				para_map.put("memberCodeList", memberCodeList);
				String content = JSONObject.toJSONString(para_map);
				String token = keepLogin.getToken();
				String resData = HttpTestUtils.httpRequest(HttpEnum.DELETE, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
						action, token, content);
				if (!StringUtils.isEmpty(resData)) {
					JSONObject jsonData = JSON.parseObject(resData);
					if ("200".equals(jsonData.getString("code"))) {
						action = "/multiDataMiningService/rest/repositoryMember/approval";
						para_map.put("approvalType", 1);
						content = JSONObject.toJSONString(para_map);
						HttpTestUtils.httpRequest(HttpEnum.PUT, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
								action, token, content);
						//获取操作人和操作数据库
						if(!StringUtils.isEmpty(httpReq.getHeader("token"))) {
							token=httpReq.getHeader("token");
							 token=token.replace(" ","+");
						}
						action="/ras/user/info";
						content="";
						resData = HttpTestUtils.httpRequest(HttpEnum.GET, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
								action, token, content);
						String createBy="";
						String createByName="";
						if (!StringUtils.isEmpty(resData)) {
							jsonData = JSON.parseObject(resData);
							createBy=jsonData.getString("userName");
							createByName=jsonData.getString("name");
						}
						for(String cObjCode: memberCodeList) {
							para_map.clear();
							para_map.put("cStatus", 1);
							para_map.put("updateTime", new Date());
							para_map.put("updateBy", createBy);
							para_map.put("updateByName", createByName);
							para_map.put("whcObjCode", cObjCode);
							carService.updateCarControlInfoByMap(para_map);
						}
					}
				} 
				res_data.put("res_code", 0);
			}else {
				res_data.put("res_code", 1);
				res_data.put("errmsg", "参数有误！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "撤控失败！");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 根据车牌获取车辆轨迹
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getCarRecordByNum",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCarRecordByNum(HttpServletRequest httpReq) {
		 String warnCNum=httpReq.getParameter("warnCNum");
		 String startTime=httpReq.getParameter("startTime");
		 String endTime=httpReq.getParameter("endTime");
		 String action = "/multiDataMiningService/rest/track/queryTrack";
		 Map<String,Object> para_map=new HashMap<String, Object>();
		 if(warnCNum.contains("挂")) {
        	 para_map.put("objId", warnCNum+"_15");//车牌号+车辆类型
         }else {
        	 para_map.put("objId", warnCNum+"_01");//车牌号+车辆类型
         }
		 para_map.put("objType", 1);
		 para_map.put("startTime", startTime);
		 para_map.put("endTime", endTime);
		 para_map.put("advance", true);
		 para_map.put("pageSize", 20);
		 para_map.put("currentPage", 1);
		 String content=JSONObject.toJSONString(para_map);
		 String token=keepLogin.getToken();
		 String resData=HttpTestUtils.httpRequest(HttpEnum.POST, keepLogin.ip, Integer.parseInt(keepLogin.port), action, token, content);
		 return resData;
	}
	
	/**
	 * 已布控车辆列表
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getCarControlList",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCarControlList(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
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
		    JSONObject jSONObject=JSON.parseObject(jsonData);
		    String keyWord=(String) jSONObject.get("keyWord");//关键字（车牌、布控人信息）
			int curPageInt=(Integer) jSONObject.get("curPage");//当前页数
			int pageSizeInt=(Integer) jSONObject.get("pageSize");//每页条数
			String startDate=(String) jSONObject.get("startDate");//布控时间查询-开始时间
			String endDate=(String) jSONObject.get("endDate");//布控时间查询-结束时间
			boolean warnInvalidNum=false;
			int start=(curPageInt-1)*pageSizeInt;
			para_data.put("start", start);
			para_data.put("pageSize", pageSizeInt);
			if(!StringUtils.isEmpty(keyWord)){
				para_data.put("keyWord", keyWord);
			}
			if(!warnInvalidNum) {
				para_data.put("isActive", "Y");
			}
			if(!StringUtils.isEmpty(startDate)){
				para_data.put("startDate", startDate);
			}
			if(!StringUtils.isEmpty(endDate)){
				para_data.put("endDate", endDate);
			}
			List<CarControlInfo> carControlInfoList = carService.selectCarControlInfoByParamMap(para_data);
			List<Map<String,Object>> data_List=new ArrayList<Map<String,Object>>();
			for(CarControlInfo carControlInfo:carControlInfoList) {
				Map<String,Object> data_map=new HashMap<String, Object>();
				data_map.put("conId", carControlInfo.getcId());
				data_map.put("conCarNum", carControlInfo.getcCarNum());
				data_map.put("conObjCode", carControlInfo.getcObjCode());
				data_map.put("creatBy", carControlInfo.getCreatBy());
				data_map.put("createByName", carControlInfo.getCreateByName());
				data_map.put("createTime", carControlInfo.getCreateTime());
				data_List.add(data_map);
			}
			
			int total=carService.selectCarControlCountByParamMap(para_data);
			
			res_data.put("data", data_List);
			res_data.put("total", total);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	
	/**
	 * 车辆预警信息
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getCarEarlyWarningList",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCarEarlyWarningList(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
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
		    JSONObject jSONObject=JSON.parseObject(jsonData);
		    String keyWord=(String) jSONObject.get("keyWord");//关键字（车牌、预警地点信息）
			int curPageInt=(Integer) jSONObject.get("curPage");//当前页数
			int pageSizeInt=(Integer) jSONObject.get("pageSize");//每页条数
			String startDate=(String) jSONObject.get("startDate");//预警时间查询-开始时间
			String endDate=(String) jSONObject.get("endDate");//预警时间查询-结束时间
			boolean warnInvalidNum=false;
			int start=(curPageInt-1)*pageSizeInt;
			para_data.put("start", start);
			para_data.put("pageSize", pageSizeInt);
			if(!StringUtils.isEmpty(keyWord)){
				para_data.put("keyWord", keyWord);
			}
			if(!warnInvalidNum) {
				para_data.put("isActive", "Y");
			}
			if(!StringUtils.isEmpty(startDate)){
				para_data.put("startDate", startDate);
			}
			if(!StringUtils.isEmpty(endDate)){
				para_data.put("endDate", endDate);
			}
			List<CarEarlyWarning> carControlInfoList = carService.selectCarEarlyWarningInfoByParamMap(para_data);
			List <Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
			for(CarEarlyWarning carEarlyWarning:carControlInfoList) {
				Map<String,Object> data_map=new HashMap<String, Object>();
				data_map.put("warnId", carEarlyWarning.getwId());
				data_map.put("warnChannelCode", carEarlyWarning.getwChannelCode());
				data_map.put("warnChannelName", carEarlyWarning.getwChannelName());
				data_map.put("warnGpsX", carEarlyWarning.getwGpsX());
				data_map.put("warnGpsY", carEarlyWarning.getwGpsY());
				data_map.put("warnCarNum", carEarlyWarning.getwCarNum());
				data_map.put("warnImgUrl", carEarlyWarning.getwImgUrl());
				data_map.put("warnCapDateStr", carEarlyWarning.getwCapDateStr());
				dataList.add(data_map);
			}
			
			int total=carService.selectCarEarlyWarningCountByParamMap(para_data);
			
			res_data.put("data", dataList);
			res_data.put("total", total);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	/*
	 * 描述：通过 jquery.form.js 插件提供的ajax方式上传文件
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="importCarInfo",produces = "application/json;charset=UTF-8")
	public  String  ajaxUploadExcel(HttpServletRequest request) {
		Map<String, Object> res_data = new HashMap<String, Object>();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			InputStream in = null;
			List<List<Object>> listob = null;
			MultipartFile file = multipartRequest.getFile("upfile");
			String carType=multipartRequest.getParameter("carType");
			if(StringUtils.isEmpty(carType)) {
				carType="0";
			}
			if (file.isEmpty()) {
				res_data.put("res_code", 1);
				res_data.put("errmsg", "文件不存在！");
			}else {
				in = file.getInputStream();
				listob = new ImportExcelUtil().getBankListByExcel(in, file.getOriginalFilename());
				Map<String,Object> para_map=new HashMap<String, Object>();
				//该处可调用service相应方法进行数据保存到数据库中，现只对数据输出
				for (int i = 0; i < listob.size(); i++) {
					List<Object> lo = listob.get(i);
					SysCarInfo sysCarInfo=new SysCarInfo();
					sysCarInfo.setCarNum(String.valueOf(lo.get(0)));
					if(listob.size()>1) {
						sysCarInfo.setCarUser(String.valueOf(lo.get(1)));
					}
					if(listob.size()>2) {
						sysCarInfo.setCarUserIdCard(String.valueOf(lo.get(2)));
					}
					if(listob.size()>3) {
						sysCarInfo.setCarUserCompany(String.valueOf(lo.get(3)));
					}
					if(listob.size()>4) {
						sysCarInfo.setCarUserMobile(String.valueOf(lo.get(4)));
					}
					if(listob.size()>5) {
						sysCarInfo.setCardType(String.valueOf(lo.get(5)));
					}
					sysCarInfo.setCarType(Integer.parseInt(carType));
					para_map.clear();
					para_map.put("carNum", String.valueOf(lo.get(0)));
					carService.selectSysCarCountByParamMap(para_map);
					carService.insertSysCarSelective(sysCarInfo);
				}
				res_data.put("res_code", 0);
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "导入异常！");
		}
		return JSONObject.toJSONString(res_data);
	}
	
}
