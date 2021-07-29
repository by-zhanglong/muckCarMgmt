package com.bayee.dao;

import java.util.List;

import com.bayee.model.SysRuleType;

public interface SysRuleTypeMapper {
    int deleteByPrimaryKey(Integer typeId);

    int insert(SysRuleType record);

    int insertSelective(SysRuleType record);

    SysRuleType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(SysRuleType record);

    int updateByPrimaryKey(SysRuleType record);
    
    List<SysRuleType> selectAllData();
}