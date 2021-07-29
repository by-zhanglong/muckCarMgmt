package com.bayee.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bayee.dao.CarControlInfoMapper;
import com.bayee.dao.CarEarlyWarningMapper;
import com.bayee.dao.StorgeEventMapper;
import com.bayee.dao.SysCarInfoMapper;
import com.bayee.dao.SysRuleListMapper;
import com.bayee.dao.WarnCarInfoMapper;
import com.bayee.dao.WarnDetailMapper;
import com.bayee.model.CarControlInfo;
import com.bayee.model.CarEarlyWarning;
import com.bayee.model.StorgeEvent;
import com.bayee.model.SysCarInfo;
import com.bayee.model.SysRuleList;
import com.bayee.model.WarnCarInfo;
import com.bayee.model.WarnDetail;
import com.bayee.util.TimeUtils;

@Service
public class CarService {
	@Autowired
	private WarnCarInfoMapper warnCarInfoMapper;
	@Autowired
	private SysCarInfoMapper sysCarInfoMapper;
	@Autowired
	private WarnDetailMapper warnDetailMapper;
	@Autowired
	private StorgeEventMapper storgeEventMapper;
	@Autowired
	private SysRuleListMapper sysRuleListMapper;
	@Autowired
	private CarControlInfoMapper carControlInfoMapper;
	@Autowired
	private CarEarlyWarningMapper carEarlyWarningMapper;
	/**
	 * 获取渣土车轨迹信息
	 * @param map
	 * @return
	 */
	public List<WarnCarInfo> selectWarnCarByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectWarnCarByParamMap(map);
	}
	
	/**
	 * 获取渣土车轨迹总数
	 * @param map
	 * @return
	 */
	public int selectWarnCarCountByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectWarnCarCountByParamMap(map);
	}
	
	/**
	 * 获取渣土车赋分前100车辆
	 * @param map
	 * @return
	 */
	public List<WarnCarInfo> selectEarlyWarnCarByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectEarlyWarnCarByParamMap(map);
	}
	/**
	 * 获取渣土车存在赋分的车辆总数
	 * @param map
	 * @return
	 */
	public int selectEarlyWarnCarCountByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectEarlyWarnCarCountByParamMap(map);
	}
	
	/**
	 * 查询车辆赋分记录
	 * @param map
	 * @return
	 */
	public List<WarnCarInfo> selectWarnCarScoreByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectWarnCarScoreByParamMap(map);
	}
	
	/**
	 * 查询车辆赋分次数
	 * @param map
	 * @return
	 */
	public int selectWarnCarScoreCountByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectWarnCarScoreCountByParamMap(map);
	}
	
	/**
	 * 插入渣土车二次分析数据和本次轨迹赋分情况
	 * @param warnCarInfo
	 * @return
	 */
	public int insertWarnCarInfoSelective(WarnCarInfo warnCarInfo){
		return warnCarInfoMapper.insertSelective(warnCarInfo);
	}
	
	/**
	 * 删除渣土车赋分记录
	 * @param map
	 * @return
	 */
	public int deleteWarnCarInfoByMap(Map<String, Object> map){
		return  warnCarInfoMapper.deleteWarnCarInfoByMap(map);
	}
	
	/**
	 * 车辆档案-列表
	 * @param map
	 * @return
	 */
	public List<SysCarInfo> selectSysCarListByParamMap(Map<String, Object> map){
		return sysCarInfoMapper.selectSysCarListByParamMap(map);
	}
	
	/**
	 * 车辆档案-黑、白、红名单总数
	 * @param map
	 * @return
	 */
	public int selectSysCarCountByParamMap(Map<String, Object> map){
		return sysCarInfoMapper.selectSysCarCountByParamMap(map);
	}
	
	/**
	 * 档案管理-车辆新增
	 * @param sysCarInfo
	 * @return
	 */
	public int insertSysCarSelective(SysCarInfo sysCarInfo){
		return sysCarInfoMapper.insertSelective(sysCarInfo);
	}
	
	/**
	 * 档案管理-车辆基本信息查询
	 * @param carId
	 * @return
	 */
	public SysCarInfo getSysCarInfo(int carId){
		return sysCarInfoMapper.selectByPrimaryKey(carId);
	}
	
	/**
	 * 档案管理-逻辑删除或更改车辆属性
	 * @param sysCarInfo
	 * @return
	 */
	public int updateSysCarByPrimaryKeySelective(SysCarInfo sysCarInfo){
		return sysCarInfoMapper.updateByPrimaryKeySelective(sysCarInfo);
	}
	
	/**
	 * 根据赋分规则的ID查询已赋分车辆个数
	 * @param ruleTypeId
	 * @return
	 */
	public int selectcountByRuleTypeId(int ruleTypeId) {
		return warnDetailMapper.selectcountByRuleTypeId(ruleTypeId);
	}
	
	/**
	 * 根据id删除档案中车辆信息
	 * @param carId
	 * @return
	 */
	public int deleteSysCarByCarId(int carId) {
		return sysCarInfoMapper.deleteByPrimaryKey(carId);
	}
	
	/**
	 * 赋分明细查询（已废弃）
	 * @param map
	 * @return
	 */
	public List<WarnDetail> selectWarnDetailListByParaMap(Map<String, Object> map){
		return warnDetailMapper.selectWarnDetailListByParaMap(map);
	}
	/**
	 * 赋分明细查询（已废弃）
	 * @param map
	 * @return
	 */
    public int selectWarnDetailcountByParaMap(Map<String, Object> map) {
    	return warnDetailMapper.selectWarnDetailcountByParaMap(map);
    }
    /**
     * 根据车牌查询轨迹（暂时废弃）
     * @param plateNumA
     * @return
     */
    public List<StorgeEvent> selectStorgEventByplateNum(String plateNumA){
    	return storgeEventMapper.selectStorgEventByplateNum(plateNumA);
    }
    
    /**
     * 根据渣土车轨迹表中主键ID，获取本次抓拍信息
     * @param carId
     * @return
     */
    public WarnCarInfo getWarnCarInfo(int carId){
		return warnCarInfoMapper.selectByPrimaryKey(carId);
	}
    
    /**
     * 添加车辆布控
     * @param carControlInfo
     * @return
     */
    public int addCarControlInfo(CarControlInfo carControlInfo) {
    	return carControlInfoMapper.insertSelective(carControlInfo);
    }
    
    /**
     * 获取布控信息
     * @param cId
     * @return
     */
    public CarControlInfo getCarControlInfo(Long cId) {
    	return carControlInfoMapper.selectByPrimaryKey(cId);
    }
    /**
     * 更新车辆的布控状态和其他属性
     * @param map
     * @return
     */
    public int updateCarControlInfoByMap(Map<String,Object> map) {
    	return carControlInfoMapper.updateCarControlInfoByMap(map);
    }
    /**
     * 查询布控车辆总数
     * @param map
     * @return
     */
    public int selectCarControlCountByParamMap(Map<String,Object> map) {
    	return carControlInfoMapper.selectCarControlCountByParamMap(map);
    }
    
    /**
     * 查询已布控车辆信息
     * @param map
     * @return
     */
    public List<CarControlInfo> selectCarControlInfoByParamMap(Map<String,Object> map) {
    	return carControlInfoMapper.selectCarControlInfoByParamMap(map);
    }
    /**
     * 查询存在预警信息的车辆总数
     * @param map
     * @return
     */
    public int selectCarEarlyWarningCountByParamMap(Map<String,Object> map) {
    	return carEarlyWarningMapper.selectCarEarlyWarningCountByParamMap(map);
    }
    
    /**
     * 车辆查询存在预警的最新信息集合
     * @param map
     * @return
     */
    public List<CarEarlyWarning> selectCarEarlyWarningInfoByParamMap(Map<String,Object> map) {
    	return carEarlyWarningMapper.selectCarEarlyWarningInfoByParamMap(map);
    }
    
    /**
     * top5预警点位
     */
    public List<CarEarlyWarning> getTopFiveChannelInfo(){
    	return carEarlyWarningMapper.getTopFiveChannelInfo();
    }
    
    /**
     * 根据参数map 查询预警信息
     * @param map
     * @return
     */
    public  List<CarEarlyWarning> selectCarEarlyWarningInfo(Map<String,Object> map) {
    	return carEarlyWarningMapper.selectCarEarlyWarningInfo(map);
    }
    
    public void changeWarnCarInfor() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Map<String,Object> para_map=new HashMap<String, Object>();
		//para_map.put("listTypeId", 1);
		para_map.put("listStatus", "Y");
		List<SysRuleList> sysRuleList=sysRuleListMapper.selectSysRuleListByMap(para_map);
		para_map.clear();
		List<WarnCarInfo> warnCarInfoData=warnCarInfoMapper.selectWarnCarByParamMap(para_map);
		for(WarnCarInfo warnCarInfo:warnCarInfoData) {
			try {
				//Date date=new Date(new Double(Double.parseDouble(warnCarInfo.getWarnCTime())).longValue());
				//String dateStr = TimeUtils.timeZoneTransfer(sdf.format(date), "yyyy-MM-dd HH:mm:ss", "+8", "+8");
				//warnCarInfo.setWarnCTime(dateStr);
				int hour=sdf.parse(warnCarInfo.getWarnCTime()).getHours();
				int score=0;
		    	String item="";
		    	String type="";
		    	String typeList="";//具体的匹配项
		    	boolean ejFlag=false;//夜间出行标识
		    	boolean wpFlag=false;//外拍赋分标识
		    	for(SysRuleList data:sysRuleList) {
		    		if(1==data.getListTypeId()) {//凌晨出行
		    			if(!StringUtils.isEmpty(data.getListValueFir())&&!StringUtils.isEmpty(data.getListValueSec())) {//存在起止时间
			    			int startHour=Integer.parseInt(data.getListValueFir());
			    			int endHour=Integer.parseInt(data.getListValueSec());
			    			if(startHour<endHour) {//开始时间小于结束时间 同一天
			    				if(startHour<=hour&&hour<endHour) {
				    				score+=data.getListValueScore();
				    				typeList+=data.getListId()+",";
				    				ejFlag=true;
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
		    				if(!StringUtils.isEmpty(warnCarInfo.getWarnCNum())
		    				&&warnCarInfo.getWarnCNum().contains(data.getListValueFir())) {//牌照不为空且包含系统设置的车牌区域
		    					score+=data.getListValueScore();
		    					wpFlag=true;
		    					typeList+=data.getListId()+",";
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
		    	warnCarInfo.setWarnItem(item);
		    	warnCarInfo.setWarnCUdf2(type);
		    	warnCarInfo.setWarnCUdf3(typeList);
		    	warnCarInfo.setWarnCTotal(score);
				warnCarInfoMapper.updateByPrimaryKeySelective(warnCarInfo);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
    }
}
