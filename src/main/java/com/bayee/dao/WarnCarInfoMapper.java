package com.bayee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayee.model.WarnCarInfo;

public interface WarnCarInfoMapper {
    int deleteByPrimaryKey(Integer warnCId);

    int insert(WarnCarInfo record);

    int insertSelective(WarnCarInfo record);

    WarnCarInfo selectByPrimaryKey(Integer warnCId);

    int updateByPrimaryKeySelective(WarnCarInfo record);

    int updateByPrimaryKey(WarnCarInfo record);
    
    int deleteWarnCarInfoByMap(@Param("map") Map<String, Object> map); 
    List<WarnCarInfo> selectWarnCarByParamMap(@Param("map") Map<String, Object> map);
    List<WarnCarInfo> selectWarnCarScoreByParamMap(@Param("map") Map<String, Object> map);
    
    int selectWarnCarCountByParamMap(@Param("map") Map<String, Object> map);
    int selectWarnCarScoreCountByParamMap(@Param("map") Map<String, Object> map);
    List<WarnCarInfo> selectEarlyWarnCarByParamMap(@Param("map") Map<String, Object> map);
    int selectEarlyWarnCarCountByParamMap(@Param("map") Map<String, Object> map);
    List<WarnCarInfo> selectWarnCarInfoByMap(@Param("map") Map<String, Object> map);
}