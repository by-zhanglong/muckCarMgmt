package com.bayee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayee.model.SysPalceInfo;

public interface SysPalceInfoMapper {
    int insert(SysPalceInfo record);

    int insertSelective(SysPalceInfo record);
    
    List<SysPalceInfo> selectSysPalceInfoByParams(SysPalceInfo sysPalceInfo);
    
    List<SysPalceInfo> selectSysPalceInfoByParamMap(@Param("map") Map<String,Object> map);
    
    int selectSysPalceInfoCountByParamMap(@Param("map") Map<String,Object> map);
}