package com.bayee.controller;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bayee.model.SysPalceInfo;
import com.bayee.service.SysPalceInfoService;
@CrossOrigin()
@Controller
@RequestMapping("/palce")
public class SysPlaceInfoController {
	Logger logger=Logger.getLogger(this.getClass());
	@Autowired
	private SysPalceInfoService sysPalceInfoService;
	
	@RequestMapping(value="/getDataList",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getDataList(HttpServletRequest httpReq) {
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
		    String jsonData=info.toString();
		    JSONObject jSONObject=JSON.parseObject(jsonData);
		    String keyWord=jSONObject.getString("keyWord");//关键字
			int pageSizeInt=(Integer) jSONObject.get("pageSize");//每页条数
			String palceId=jSONObject.getString("palceId");
			String palceX=jSONObject.getString("palceX");
			String palceY=jSONObject.getString("palceY");
			String palceNo=jSONObject.getString("palceNo");
			/**
			 * 判断参数是否为空，动态查询数据
			 */
			SysPalceInfo paraSysPalceInfo=new SysPalceInfo();
			if(null!=keyWord&&!"".equals(keyWord)) {
				paraSysPalceInfo.setPalceName(keyWord);
			}
			int placeId=0;
			if(null!=palceId&&!"".equals(palceId)) {
				try {
					placeId=Integer.parseInt(palceId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(placeId!=0) {
					paraSysPalceInfo.setPalceId(placeId);
				}
			}
			if(null!=palceX&&!"".equals(palceX)) {
				paraSysPalceInfo.setPalceX(palceX);
			}
			if(null!=palceY&&!"".equals(palceY)) {
				paraSysPalceInfo.setPalceY(palceY);
			}
			if(null!=palceNo&&!"".equals(palceNo)) {
				paraSysPalceInfo.setPalceNo(palceNo);
			}
			paraSysPalceInfo.setLimitCount(pageSizeInt);
			List<SysPalceInfo> sysPalceInfoList = sysPalceInfoService.selectSysPalceInfoByParams(paraSysPalceInfo);
			res_data.put("data", sysPalceInfoList);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		logger.debug("/palce/getDataList"+res_data);
		return JSONObject.toJSONString(res_data);
	}
	
	
	@RequestMapping(value="/getPageDataList",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getPageDataList(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
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
		    String keyWord=jSONObject.getString("keyWord");//关键字
		    int curPageInt=(Integer) jSONObject.get("curPage");//当前页数
			int pageSizeInt=(Integer) jSONObject.get("pageSize");//每页条数
			int start=(curPageInt-1)*pageSizeInt;
			para_data.put("start", start);
			para_data.put("pageSize", pageSizeInt);
			/**
			 * 判断参数是否为空，动态查询数据
			 */
			if(null!=keyWord&&!"".equals(keyWord)) {
				para_data.put("keyWord",keyWord);
			}
			List<SysPalceInfo> sysPalceInfoList = sysPalceInfoService.selectSysPalceInfoByParamMap(para_data);
			int total=sysPalceInfoService.selectSysPalceInfoCountByParamMap(para_data);
			res_data.put("data", sysPalceInfoList);
			res_data.put("total", total);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
}
