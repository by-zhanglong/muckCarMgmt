package com.bayee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayee.model.SysCarInfo;

public interface SysCarInfoMapper {
    int deleteByPrimaryKey(Integer carId);

    int insert(SysCarInfo record);

    int insertSelective(SysCarInfo record);

    SysCarInfo selectByPrimaryKey(Integer carId);

    int updateByPrimaryKeySelective(SysCarInfo record);

    int updateByPrimaryKey(SysCarInfo record);
    
    List<SysCarInfo> selectSysCarListByParamMap(@Param("map") Map<String, Object> map);
    
    int selectSysCarCountByParamMap(@Param("map") Map<String, Object> map);
}