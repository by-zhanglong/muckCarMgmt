package com.bayee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayee.model.SysTagTypeInfo;

public interface SysTagTypeInfoMapper {
    int deleteByPrimaryKey(Integer typeId);

    int insert(SysTagTypeInfo record);

    int insertSelective(SysTagTypeInfo record);

    SysTagTypeInfo selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(SysTagTypeInfo record);

    int updateByPrimaryKey(SysTagTypeInfo record);
    
    List<SysTagTypeInfo> selectSysTagTypeInfoByMap(@Param("map") Map<String,Object> map);
}