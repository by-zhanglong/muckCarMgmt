package com.bayee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayee.model.CarEarlyWarning;

public interface CarEarlyWarningMapper {
    int deleteByPrimaryKey(Long wId);

    int insert(CarEarlyWarning record);

    int insertSelective(CarEarlyWarning record);

    CarEarlyWarning selectByPrimaryKey(Long wId);

    int updateByPrimaryKeySelective(CarEarlyWarning record);

    int updateByPrimaryKey(CarEarlyWarning record);
    List<CarEarlyWarning> selectCarEarlyWarningInfoByParamMap(@Param("map") Map<String, Object> map);
    int selectCarEarlyWarningCountByParamMap(@Param("map") Map<String, Object> map);
    
    List<CarEarlyWarning> getTopFiveChannelInfo();
    
    List<CarEarlyWarning> selectCarEarlyWarningInfo(@Param("map") Map<String, Object> map);
}