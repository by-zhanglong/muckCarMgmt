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
	 * ��ȡ�������켣��Ϣ
	 * @param map
	 * @return
	 */
	public List<WarnCarInfo> selectWarnCarByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectWarnCarByParamMap(map);
	}
	
	/**
	 * ��ȡ�������켣����
	 * @param map
	 * @return
	 */
	public int selectWarnCarCountByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectWarnCarCountByParamMap(map);
	}
	
	/**
	 * ��ȡ����������ǰ100����
	 * @param map
	 * @return
	 */
	public List<WarnCarInfo> selectEarlyWarnCarByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectEarlyWarnCarByParamMap(map);
	}
	/**
	 * ��ȡ���������ڸ��ֵĳ�������
	 * @param map
	 * @return
	 */
	public int selectEarlyWarnCarCountByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectEarlyWarnCarCountByParamMap(map);
	}
	
	/**
	 * ��ѯ�������ּ�¼
	 * @param map
	 * @return
	 */
	public List<WarnCarInfo> selectWarnCarScoreByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectWarnCarScoreByParamMap(map);
	}
	
	/**
	 * ��ѯ�������ִ���
	 * @param map
	 * @return
	 */
	public int selectWarnCarScoreCountByParamMap(Map<String, Object> map){
		return warnCarInfoMapper.selectWarnCarScoreCountByParamMap(map);
	}
	
	/**
	 * �������������η������ݺͱ��ι켣�������
	 * @param warnCarInfo
	 * @return
	 */
	public int insertWarnCarInfoSelective(WarnCarInfo warnCarInfo){
		return warnCarInfoMapper.insertSelective(warnCarInfo);
	}
	
	/**
	 * ɾ�����������ּ�¼
	 * @param map
	 * @return
	 */
	public int deleteWarnCarInfoByMap(Map<String, Object> map){
		return  warnCarInfoMapper.deleteWarnCarInfoByMap(map);
	}
	
	/**
	 * ��������-�б�
	 * @param map
	 * @return
	 */
	public List<SysCarInfo> selectSysCarListByParamMap(Map<String, Object> map){
		return sysCarInfoMapper.selectSysCarListByParamMap(map);
	}
	
	/**
	 * ��������-�ڡ��ס�����������
	 * @param map
	 * @return
	 */
	public int selectSysCarCountByParamMap(Map<String, Object> map){
		return sysCarInfoMapper.selectSysCarCountByParamMap(map);
	}
	
	/**
	 * ��������-��������
	 * @param sysCarInfo
	 * @return
	 */
	public int insertSysCarSelective(SysCarInfo sysCarInfo){
		return sysCarInfoMapper.insertSelective(sysCarInfo);
	}
	
	/**
	 * ��������-����������Ϣ��ѯ
	 * @param carId
	 * @return
	 */
	public SysCarInfo getSysCarInfo(int carId){
		return sysCarInfoMapper.selectByPrimaryKey(carId);
	}
	
	/**
	 * ��������-�߼�ɾ������ĳ�������
	 * @param sysCarInfo
	 * @return
	 */
	public int updateSysCarByPrimaryKeySelective(SysCarInfo sysCarInfo){
		return sysCarInfoMapper.updateByPrimaryKeySelective(sysCarInfo);
	}
	
	/**
	 * ���ݸ��ֹ����ID��ѯ�Ѹ��ֳ�������
	 * @param ruleTypeId
	 * @return
	 */
	public int selectcountByRuleTypeId(int ruleTypeId) {
		return warnDetailMapper.selectcountByRuleTypeId(ruleTypeId);
	}
	
	/**
	 * ����idɾ�������г�����Ϣ
	 * @param carId
	 * @return
	 */
	public int deleteSysCarByCarId(int carId) {
		return sysCarInfoMapper.deleteByPrimaryKey(carId);
	}
	
	/**
	 * ������ϸ��ѯ���ѷ�����
	 * @param map
	 * @return
	 */
	public List<WarnDetail> selectWarnDetailListByParaMap(Map<String, Object> map){
		return warnDetailMapper.selectWarnDetailListByParaMap(map);
	}
	/**
	 * ������ϸ��ѯ���ѷ�����
	 * @param map
	 * @return
	 */
    public int selectWarnDetailcountByParaMap(Map<String, Object> map) {
    	return warnDetailMapper.selectWarnDetailcountByParaMap(map);
    }
    /**
     * ���ݳ��Ʋ�ѯ�켣����ʱ������
     * @param plateNumA
     * @return
     */
    public List<StorgeEvent> selectStorgEventByplateNum(String plateNumA){
    	return storgeEventMapper.selectStorgEventByplateNum(plateNumA);
    }
    
    /**
     * �����������켣��������ID����ȡ����ץ����Ϣ
     * @param carId
     * @return
     */
    public WarnCarInfo getWarnCarInfo(int carId){
		return warnCarInfoMapper.selectByPrimaryKey(carId);
	}
    
    /**
     * ��ӳ�������
     * @param carControlInfo
     * @return
     */
    public int addCarControlInfo(CarControlInfo carControlInfo) {
    	return carControlInfoMapper.insertSelective(carControlInfo);
    }
    
    /**
     * ��ȡ������Ϣ
     * @param cId
     * @return
     */
    public CarControlInfo getCarControlInfo(Long cId) {
    	return carControlInfoMapper.selectByPrimaryKey(cId);
    }
    /**
     * ���³����Ĳ���״̬����������
     * @param map
     * @return
     */
    public int updateCarControlInfoByMap(Map<String,Object> map) {
    	return carControlInfoMapper.updateCarControlInfoByMap(map);
    }
    /**
     * ��ѯ���س�������
     * @param map
     * @return
     */
    public int selectCarControlCountByParamMap(Map<String,Object> map) {
    	return carControlInfoMapper.selectCarControlCountByParamMap(map);
    }
    
    /**
     * ��ѯ�Ѳ��س�����Ϣ
     * @param map
     * @return
     */
    public List<CarControlInfo> selectCarControlInfoByParamMap(Map<String,Object> map) {
    	return carControlInfoMapper.selectCarControlInfoByParamMap(map);
    }
    /**
     * ��ѯ����Ԥ����Ϣ�ĳ�������
     * @param map
     * @return
     */
    public int selectCarEarlyWarningCountByParamMap(Map<String,Object> map) {
    	return carEarlyWarningMapper.selectCarEarlyWarningCountByParamMap(map);
    }
    
    /**
     * ������ѯ����Ԥ����������Ϣ����
     * @param map
     * @return
     */
    public List<CarEarlyWarning> selectCarEarlyWarningInfoByParamMap(Map<String,Object> map) {
    	return carEarlyWarningMapper.selectCarEarlyWarningInfoByParamMap(map);
    }
    
    /**
     * top5Ԥ����λ
     */
    public List<CarEarlyWarning> getTopFiveChannelInfo(){
    	return carEarlyWarningMapper.getTopFiveChannelInfo();
    }
    
    /**
     * ���ݲ���map ��ѯԤ����Ϣ
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
		    	String typeList="";//�����ƥ����
		    	boolean ejFlag=false;//ҹ����б�ʶ
		    	boolean wpFlag=false;//���ĸ��ֱ�ʶ
		    	for(SysRuleList data:sysRuleList) {
		    		if(1==data.getListTypeId()) {//�賿����
		    			if(!StringUtils.isEmpty(data.getListValueFir())&&!StringUtils.isEmpty(data.getListValueSec())) {//������ֹʱ��
			    			int startHour=Integer.parseInt(data.getListValueFir());
			    			int endHour=Integer.parseInt(data.getListValueSec());
			    			if(startHour<endHour) {//��ʼʱ��С�ڽ���ʱ�� ͬһ��
			    				if(startHour<=hour&&hour<endHour) {
				    				score+=data.getListValueScore();
				    				typeList+=data.getListId()+",";
				    				ejFlag=true;
				    			}
			    			}else if(startHour>endHour) {//��������
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
			    				if(startHour<=hour&&hour<=endHour) {//��ʼʱ��ͽ���ʱ����ͬ
				    				score+=data.getListValueScore();
				    				ejFlag=true;
				    				typeList+=data.getListId()+",";
				    			}
			    			}
			    			
			    		}
		    		}else if(4==data.getListTypeId()) {//����
		    			if(!StringUtils.isEmpty(data.getListValueFir())) {//����
		    				if(!StringUtils.isEmpty(warnCarInfo.getWarnCNum())
		    				&&warnCarInfo.getWarnCNum().contains(data.getListValueFir())) {//���ղ�Ϊ���Ұ���ϵͳ���õĳ�������
		    					score+=data.getListValueScore();
		    					wpFlag=true;
		    					typeList+=data.getListId()+",";
		    				}
		    			}
		    		}	
		    	}
		    	if(ejFlag) {
		    		item+="1,";
	    			type+="�賿����,";
		    	}
		    	if(wpFlag) {
		    		item+="4,";
	    			type+="����,";
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
