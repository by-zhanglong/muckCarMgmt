package com.bayee.controller;

import java.text.DecimalFormat;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.bayee.model.CarEarlyWarning;
import com.bayee.model.SysTagInfo;
import com.bayee.service.CarService;
import com.bayee.service.SysTagService;

@CrossOrigin()
@Controller
@RequestMapping("/index")
public class IndexContoller {
	@Autowired
	private CarService carService;
	@Autowired
	private SysTagService sysTagService;
	
	@RequestMapping(value="/getCarInfo",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCarInfo(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
		try {
			//���ֳ�������
			para_data.put("isActive", "Y");
			int warnTotal=carService.selectEarlyWarnCarCountByParamMap(para_data);
			//���س�������
			int controlTotal=carService.selectCarControlCountByParamMap(para_data);
			
			para_data.clear();
			para_data.put("carType", 0);
			int whiteCarTotal=carService.selectSysCarCountByParamMap(para_data);
			para_data.put("carType", 1);
			int blackCarTotal=carService.selectSysCarCountByParamMap(para_data);
			para_data.put("carType", 2);
			int redCarTotal=carService.selectSysCarCountByParamMap(para_data);
			String percent="";//���س�����Ԥ�������ͺ����������е�ռ��
			if((warnTotal+redCarTotal)!=0) {
				 DecimalFormat df = new DecimalFormat("#.00");
				 percent=df.format(controlTotal/(warnTotal+redCarTotal));
			}else {
				percent="0";
			}
			res_data.put("warnTotal", warnTotal);//Ԥ������
			res_data.put("controlTotal", controlTotal);//���س���
			res_data.put("percent", percent);//���س���ռ��
			res_data.put("whiteCarTotal", whiteCarTotal);//����������
			res_data.put("blackCarTotal", blackCarTotal);//����������
			res_data.put("redCarTotal", redCarTotal);//����������
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "�����쳣");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	@RequestMapping(value="/getEasyToToppleInfo",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getEasyToToppleInfo(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
		try {
			para_data.clear();
			para_data.put("tagType", 2);//���㵹����
    		List<SysTagInfo> sysTagInfoList=sysTagService.selectSysTagInfoByMap(para_data);
    		Calendar calendar = Calendar.getInstance(); //����Calendar ��ʵ��
    		SimpleDateFormat fmtDay = new SimpleDateFormat("yyyy-MM-dd");//����ת��
    		SimpleDateFormat fmtMonth = new SimpleDateFormat("yyyy-MM");//����ת��
    		String nowDay=fmtDay.format(calendar.getTime());
    		String monthStr=fmtMonth.format(calendar.getTime());
    		List<String> dayStrList=new ArrayList<String>();
    		for(int i=6;i>0;i--) {//��ǰʱ����ǰ����6��
    			calendar = Calendar.getInstance();
    			calendar.add(Calendar.DAY_OF_MONTH, -i);
    			dayStrList.add(fmtDay.format(calendar.getTime()));
    		}
    		dayStrList.add(nowDay);//��ȡ�������
    		int total=0;
    		int monthTotal=0;
    		int dayTotal=0;
    		List<Map<String,Object>> data_list=new ArrayList<Map<String,Object>>();
    		if(null!=sysTagInfoList&&sysTagInfoList.size()>0) {
    			Map<String,Integer> data_count_map=new HashMap<String, Integer>();
    			for(SysTagInfo data:sysTagInfoList) {
    				Date createTime=null;
    				if(null!=data.getCreateTime()) {
    					createTime=data.getCreateTime();
    				}else {
    					createTime=new Date();
    				}
    				if(fmtMonth.format(createTime).equals(monthStr)) {//�·���ͬ
    					monthTotal++;
    				}
    				
    				if(fmtMonth.format(createTime).equals(nowDay)) {//����
    					dayTotal++;
    				}
    				for(String time:dayStrList) {//��ȡ���7������
    					if(fmtMonth.format(createTime).equals(time)) {
    						if(data_count_map.containsKey(time)) {
    							int count=data_count_map.get(time);
    							data_count_map.put(time, count+1);
    						}else {
    							data_count_map.put(time, 0);
    						}
    					}
    				}
    				
    				total++;//������
    			}
    			
    			for(String time:dayStrList) {//��ȡ���7������
    				Map<String,Object> data_map=new HashMap<String, Object>();
    				data_map.put("key", time);
    				data_map.put("value", data_count_map.get(time));
    				data_list.add(data_map);
    			}
    		}else {
    			for(String time:dayStrList) {//��ȡ���7������
    				Map<String,Object> data_map=new HashMap<String, Object>();
    				data_map.put("key", time);
    				data_map.put("value", 0);
    				data_list.add(data_map);
    			}
    		}
    		res_data.put("total", total);
    		res_data.put("monthTotal", monthTotal);
    		res_data.put("dayTotal", dayTotal);
    		res_data.put("data_list", data_list);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "�����쳣");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * ��ȡtop5Ԥ����λ��Ϣ
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getTopFiveChannelInfo",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getTopFiveChannelInfo(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
		try {
			List<CarEarlyWarning> carEarlyWarningList=carService.getTopFiveChannelInfo();
			List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
			if(null!=carEarlyWarningList&&carEarlyWarningList.size()>0) {
				int total=carService.selectCarEarlyWarningCountByParamMap(para_data);
				DecimalFormat df = new DecimalFormat("#.00");
				for(CarEarlyWarning carEarlyWarning:carEarlyWarningList) {
					String percent=df.format(carEarlyWarning.getWarnCount()/total);
				    Map<String,Object> data_map=new HashMap<String, Object>();
				    data_map.put("key", carEarlyWarning.getwChannelName());
				    data_map.put("value", percent);
				    dataList.add(data_map);
				}
			}
    		res_data.put("data_list", dataList);
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "�����쳣");
		}
		return JSONObject.toJSONString(res_data);
	}
	
	/**
	 * ��ȡ����Ԥ����Ϣ
	 * @param httpReq
	 * @return
	 */
	@RequestMapping(value="/getControlAndWarnInfo",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getControlAndWarnInfo(HttpServletRequest httpReq) {
		Map<String,Object> res_data=new HashMap<String, Object>();
		Map<String,Object> para_data=new HashMap<String, Object>();
		try {
			  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		      String dayStr=format.format(new Date());
		      para_data.put("warnDate", dayStr);
		      List<CarEarlyWarning> carEarlyWarningList=carService.selectCarEarlyWarningInfo(para_data);
			  Calendar calendar = Calendar.getInstance();
				 //����ͳ��
	            int hour = calendar.get(Calendar.HOUR_OF_DAY); //��ȡ��ǰСʱ
	            List<Integer> hour_list = new ArrayList<Integer>();
	            if (hour == 0) {//����0ʱ
	                hour_list.add(0);
	                hour_list.add(1);
	            } else if (hour < 7) {//��ǰʱ��С��
	                for (int i = 0; i <= hour; i++) {
	                    hour_list.add(i);
	                }
	            } else {
	                int hour_num = (int) Math.floor(hour / 6);
	                hour_list.add(0);
	                int hourNo = 1;
	                for (int i = 1; i <= 5; i++) {
	                    hourNo += hour_num;
	                    hour_list.add(hourNo);
	                }
	                hour_list.add(hour);
	            }
	            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	            if(null!=carEarlyWarningList&&carEarlyWarningList.size()>0) {
	            	if (hour == 0) {//����0ʱ
	                    for (int hourNum : hour_list) {//����Сʱ���ϣ���ȡÿ��ʱ��ε���������
	                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hourNum, calendar.get(Calendar.MINUTE));
	                        String dateStr = dateFormat.format(calendar.getTime());
	                        int dataCount = 0;
	                        Map<String, Object> data_map = new HashMap<String, Object>();
	                        for (int i = 0; i < carEarlyWarningList.size(); i++) {
	                            String capTimeStr = carEarlyWarningList.get(i).getwCapDateStr();
	                            if (null != capTimeStr && capTimeStr.contains(dateStr)) {
	                                dataCount++;
	                            }
	                        }
	                        data_map.put("key",hourNum);
	                        data_map.put("value",dataCount);
	                        data_map.put("isShow", true);
	                        dataList.add(data_map);
	                    }
	                }else{
	                    for(int h=0;h<=hour;h++){
	                        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), h, calendar.get(Calendar.MINUTE));
	                        String dateStr = dateFormat.format(calendar.getTime());
	                        int dataCount = 0;
	                        Map<String, Object> data_map = new HashMap<String, Object>();
	                        for (int i = 0; i < carEarlyWarningList.size(); i++) {
	                            String capTimeStr = carEarlyWarningList.get(i).getwCapDateStr();
	                            if (null != capTimeStr && capTimeStr.contains(dateStr)) {
	                                dataCount++;
	                            }
	                        }
	                        data_map.put("key",h);
	                        data_map.put("value",dataCount);
	                        if(hour_list.contains(h)){
	                            data_map.put("isShow", true);
	                        }else{
	                            data_map.put("isShow", false);
	                        }
	                        dataList.add(data_map);
	                    }
	                }
	            }
			int controlTotal=carService.selectCarControlCountByParamMap(para_data);
	        int warnTotal=carService.selectCarEarlyWarningCountByParamMap(para_data);
	        int dayWarnTotal=0;
	        if(null!=carEarlyWarningList&&carEarlyWarningList.size()>0) {
	        	dayWarnTotal=carEarlyWarningList.size();
	        }
	        res_data.put("controlTotal", controlTotal);//��������
	        res_data.put("warnTotal", warnTotal);//Ԥ������
	        res_data.put("dayWarnTotal", dayWarnTotal);//����Ԥ����
    		res_data.put("data_list", dataList);//Ԥ������ͼ
			res_data.put("res_code", 0);
		} catch (Exception e) {
			e.printStackTrace();
			res_data.put("res_code", 1);
			res_data.put("errmsg", "�����쳣");
		}
		return JSONObject.toJSONString(res_data);
	}
}
