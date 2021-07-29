package com.bayee.dao;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayee.model.SysTimeRecord;

public interface SysTimeRecordMapper {
    int deleteByPrimaryKey(Long recId);

    int insert(SysTimeRecord record);

    int insertSelective(SysTimeRecord record);

    SysTimeRecord selectByPrimaryKey(Long recId);

    int updateByPrimaryKeySelective(SysTimeRecord record);

    int updateByPrimaryKey(SysTimeRecord record);
    
    Date selectMaxRecordTime(@Param("map") Map<String,Object> map);
}