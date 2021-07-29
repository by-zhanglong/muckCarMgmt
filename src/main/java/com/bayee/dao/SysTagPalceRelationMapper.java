package com.bayee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayee.model.SysTagPalceRelation;

public interface SysTagPalceRelationMapper {
    int deleteByPrimaryKey(Integer relId);

    int insert(SysTagPalceRelation record);

    int insertSelective(SysTagPalceRelation record);

    SysTagPalceRelation selectByPrimaryKey(Integer relId);

    int updateByPrimaryKeySelective(SysTagPalceRelation record);

    int updateByPrimaryKey(SysTagPalceRelation record);
    
    int deleteSysTagPalceRelationByMap(@Param("map") Map<String,Object> map);
    
    List<SysTagPalceRelation> seleteSysTagPalceRelationByMap(@Param("map") Map<String,Object> map);
}