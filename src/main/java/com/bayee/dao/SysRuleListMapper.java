package com.bayee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayee.model.SysRuleList;

public interface SysRuleListMapper {
    int deleteByPrimaryKey(Integer listId);

    int insert(SysRuleList record);

    int insertSelective(SysRuleList record);

    SysRuleList selectByPrimaryKey(Integer listId);

    int updateByPrimaryKeySelective(SysRuleList record);

    int updateByPrimaryKey(SysRuleList record);
    
    int deleteAllSysRuleList();
    
    List<SysRuleList> selectSysRuleListByMap(@Param("map") Map<String, Object> map);
}