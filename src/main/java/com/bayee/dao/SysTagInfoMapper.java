package com.bayee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayee.model.SysTagChirdObject;
import com.bayee.model.SysTagInfo;

public interface SysTagInfoMapper {
    int deleteByPrimaryKey(Integer tagId);

    int insert(SysTagInfo record);

    int insertSelective(SysTagInfo record);

    SysTagInfo selectByPrimaryKey(Integer tagId);

    int updateByPrimaryKeySelective(SysTagInfo record);

    int updateByPrimaryKey(SysTagInfo record);
    
    List<SysTagInfo> selectSysTagInfoByMap(@Param("map") Map<String,Object> map);
    
    List<SysTagChirdObject> selectChirdDataByMap(@Param("map") Map<String,Object> map);
    
    int selectChirdDataCountByMap(@Param("map") Map<String,Object> map);
    List<SysTagChirdObject> selectDataByTypeMap(@Param("map") Map<String,Object> map);
}