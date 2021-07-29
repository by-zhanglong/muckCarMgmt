package com.bayee.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayee.dao.SysPalceInfoMapper;
import com.bayee.model.SysPalceInfo;

@Service
public class SysPalceInfoService {
	@Autowired
	private SysPalceInfoMapper sysPalceInfoMapper;
	
	public List<SysPalceInfo> selectSysPalceInfoByParams(SysPalceInfo sysPalceInfo){
		return sysPalceInfoMapper.selectSysPalceInfoByParams(sysPalceInfo);
	}
	
	public List<SysPalceInfo> selectSysPalceInfoByParamMap(Map<String,Object> map){
		return sysPalceInfoMapper.selectSysPalceInfoByParamMap(map);
	}
	
	public int selectSysPalceInfoCountByParamMap(Map<String,Object> map){
		return sysPalceInfoMapper.selectSysPalceInfoCountByParamMap(map);
	}
}
