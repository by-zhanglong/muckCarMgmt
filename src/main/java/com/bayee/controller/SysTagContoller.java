package com.bayee.controller;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bayee.dahua.KeepLogin;
import com.bayee.dahua.utils.BaseUserInfo;
import com.bayee.dahua.utils.HttpEnum;
import com.bayee.dahua.utils.HttpTestUtils;
import com.bayee.model.CarControlInfo;
import com.bayee.model.SysTagChirdObject;
import com.bayee.model.SysTagInfo;
import com.bayee.model.SysTagPalceRelation;
import com.bayee.model.SysTagTypeInfo;
import com.bayee.service.SysTagService;

@CrossOrigin()
@Controller
@RequestMapping("/tag")
public class SysTagContoller {

	@Autowired
	private SysTagService sysTagService;
	@Autowired
	private KeepLogin keepLogin;
	
	/**
	 * 获取标签分类
	 * @return
	 */
	@RequestMapping(value="/getTagType",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getTagType() {
		Map<String,Object> res_data=new HashMap<String, Object>();
		try {
			Map<String,Object> para_map=new HashedMap<String, Object>();
			para_map.put("typeStatus", 0);
			List<SysTagTypeInfo> sysTagTypeList=sysTagService.selectSysTagTypeInfoByMap(para_map);
			res_data.put("data", sysTagTypeList);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 获取根节点标签信息
	 * @return
	 */
	@RequestMapping(value="/getRootTag",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getRootTag() {
		Map<String,Object> res_data=new HashMap<String, Object>();
		try {
			Map<String,Object> para_map=new HashedMap<String, Object>();
			para_map.put("tagStatus", 0);
			para_map.put("tagPId", -1);
			List<SysTagInfo> sysTagInfoList=sysTagService.selectSysTagInfoByMap(para_map);
			res_data.put("data", sysTagInfoList);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 获取当前标签的子级
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getchirdTag",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getchirdTag(HttpServletRequest httpReq) {
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
		    int tagId =jSONObject.getInteger("tag_id");
		    int data_type=jSONObject.getInteger("data_type");
			Map<String,Object> para_map=new HashedMap<String, Object>();
			para_map.put("tagStatus", 0);
			para_map.put("tagPId", tagId);
			para_map.put("selectType", data_type);
			List<SysTagChirdObject> sysTagChirdObjectList=sysTagService.selectChirdDataByMap(para_map);
			res_data.put("data", sysTagChirdObjectList);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 获取当前标签的子级
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getchirdTagByPage",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getchirdTagByPage(HttpServletRequest httpReq) {
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
		    int tagId =jSONObject.getInteger("tag_id");
		    int data_type=jSONObject.getInteger("data_type");
		    int curPageInt=(Integer) jSONObject.get("curPage");//当前页数
			int pageSizeInt=(Integer) jSONObject.get("pageSize");//每页条数
			String keyWord=jSONObject.getString("keyWord");//关键字搜索
			Map<String,Object> para_map=new HashedMap<String, Object>();
			para_map.put("tagStatus", 0);
			para_map.put("tagPId", tagId);
			para_map.put("selectType", data_type);
			if(!StringUtils.isEmpty(keyWord)) {
				para_map.put("keyWord", keyWord);
			}
			int start=(curPageInt-1)*pageSizeInt;
			para_map.put("start", start);
			para_map.put("pageSize", pageSizeInt);
			List<SysTagChirdObject> sysTagChirdObjectList=sysTagService.selectChirdDataByMap(para_map);
			int total=sysTagService.selectChirdDataCountByMap(para_map);
			res_data.put("data", sysTagChirdObjectList);
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
	 * 添加标签
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/addTag",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addTag(HttpServletRequest httpReq) {
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
		    SysTagInfo sysTagInfo=JSON.parseObject(jsonData,SysTagInfo.class);
		    sysTagInfo.setTagStatus(0);
		    sysTagInfo.setCreateTime(new Date());
		    String token = keepLogin.getToken(); 
		    if(!StringUtils.isEmpty(httpReq.getHeader("token"))) {//根据请求头获取人员信息
				token=httpReq.getHeader("token");
				 token=token.replace(" ","+");
			}
			String action="/ras/user/info";
			String content="";
			String resData = HttpTestUtils.httpRequest(HttpEnum.GET, BaseUserInfo.ip, Integer.parseInt(BaseUserInfo.port),
					action, token, content);
			if (!StringUtils.isEmpty(resData)) {
				JSONObject jsonObj = JSON.parseObject(resData);
				sysTagInfo.setCreateBy(jsonObj.getString("userName"));
				sysTagInfo.setCreateByName(jsonObj.getString("name"));
			}
		    sysTagService.addSysTagInfo(sysTagInfo);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 获取标签信息
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getTagInfo",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getTagInfo(HttpServletRequest httpReq) {
		String tag_id=httpReq.getParameter("tagId");
		int tagId=0;
		if(!StringUtils.isEmpty(tag_id)) {
			tagId=Integer.parseInt(tag_id);
		}
		Map<String,Object> res_data=new HashMap<String, Object>();
		try {
		    SysTagInfo sysTagInfo=sysTagService.selectSysTagInfoById(tagId);
		    res_data.put("data", sysTagInfo);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 删除标签
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/deleteTag",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteTag(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		String tag_id=httpReq.getParameter("tag_id");
		int tagId=0;
		if(!StringUtils.isEmpty(tag_id)) {
			tagId=Integer.parseInt(tag_id);
		}
		SysTagInfo sysTagInfo=new SysTagInfo();
		sysTagInfo.setTagId(tagId);
		sysTagInfo.setTagStatus(1);
		try {
		    sysTagService.updateSysTagInfo(sysTagInfo);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 更新标签信息
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/updateTag",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateTag(HttpServletRequest httpReq) {
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
		    SysTagInfo sysTagInfo=JSON.parseObject(jsonData,SysTagInfo.class);
		    sysTagService.updateSysTagInfo(sysTagInfo);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * 标签新增点位
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/addTagChannel",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addTagChannel(HttpServletRequest httpReq) {
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
		    int tagId =jSONObject.getInteger("tagId");
		    String channelIds=jSONObject.getString("channelIds");
		    if(!StringUtils.isEmpty(channelIds)) {
		    	Map<String,Object> para_map=new HashedMap<String, Object>();
		    	if(channelIds.indexOf(",")>0) {
		    		String[] channelIdArr=channelIds.split(",");
		    		for(String channelId:channelIdArr) {
		    			para_map.clear();
		    			para_map.put("relPlaceId", channelId);
		    			para_map.put("relTagId", tagId);
		    			List<SysTagPalceRelation> sysTagPalceRelationList=sysTagService.seleteSysTagPalceRelationByMap(para_map);
		    			if(sysTagPalceRelationList.isEmpty()) {
		    				SysTagPalceRelation sr=new SysTagPalceRelation();
			    			sr.setRelPlaceId(Integer.parseInt(channelId));
			    			sr.setRelTagId(tagId);
			    			sr.setRelStatus(0);
			    			sysTagService.insertSysTagPalceRelation(sr);
		    			}
		    		}
		    	}else {
		    		para_map.clear();
	    			para_map.put("relPlaceId", channelIds);
	    			para_map.put("relTagId", tagId);
	    			List<SysTagPalceRelation> sysTagPalceRelationList=sysTagService.seleteSysTagPalceRelationByMap(para_map);
	    			if(sysTagPalceRelationList.isEmpty()) {
	    				SysTagPalceRelation sr=new SysTagPalceRelation();
		    			sr.setRelPlaceId(Integer.parseInt(channelIds));
		    			sr.setRelTagId(tagId);
		    			sr.setRelStatus(0);
		    			sysTagService.insertSysTagPalceRelation(sr);
	    			}
		    	}
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
	 * 标签删除点位
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/deleteTagChannel",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteTagChannel(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_map=new HashedMap<String, Object>();
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
		    int tagId =jSONObject.getInteger("tagId");
		    String channelIds=jSONObject.getString("channelIds");
		    if(!StringUtils.isEmpty(channelIds)) {
		    	if(channelIds.indexOf(",")>0) {
		    		String[] channelIdArr=channelIds.split(",");
		    		for(String channelId:channelIdArr) {
		    			para_map.clear();
		    			para_map.put("relPlaceId", channelId);
		    			para_map.put("relTagId", tagId);
		    			sysTagService.deleteSysTagPalceRelationByMap(para_map);
		    		}
		    	}else {
		    		para_map.clear();
	    			para_map.put("relPlaceId", channelIds);
	    			para_map.put("relTagId", tagId);
	    			sysTagService.deleteSysTagPalceRelationByMap(para_map);
		    	}
		    }
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "数据异常");
		}
		return JSONObject.toJSONString(res_data);
	}
}
