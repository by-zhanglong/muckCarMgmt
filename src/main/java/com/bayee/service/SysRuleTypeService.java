package com.bayee.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bayee.dao.SysRuleListMapper;
import com.bayee.dao.SysRuleTypeMapper;
import com.bayee.model.SysRuleList;
import com.bayee.model.SysRuleType;

@Service
public class SysRuleTypeService {
	@Autowired
	private SysRuleTypeMapper sysRuleTypeMapper;
	@Autowired
	private SysRuleListMapper sysRuleListMapper;
	
	public List<SysRuleType> selectAllData(){
		return sysRuleTypeMapper.selectAllData();
	}
	
	public int insertSysRuleListSelective(SysRuleList sysRuleList) {
		return sysRuleListMapper.insertSelective(sysRuleList);
	}
	
	public int deleteAllSysRuleList() {
		return sysRuleListMapper.deleteAllSysRuleList();
	}
	
	public List<SysRuleList> selectSysRuleListByMap( Map<String, Object> map){
		return sysRuleListMapper.selectSysRuleListByMap(map);
	}
}
