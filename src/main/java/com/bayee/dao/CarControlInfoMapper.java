package com.bayee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayee.model.CarControlInfo;

public interface CarControlInfoMapper {
    int deleteByPrimaryKey(Long cId);

    int insert(CarControlInfo record);

    int insertSelective(CarControlInfo record);

    CarControlInfo selectByPrimaryKey(Long cId);

    int updateByPrimaryKeySelective(CarControlInfo record);

    int updateByPrimaryKey(CarControlInfo record);
    int updateCarControlInfoByMap(@Param("map") Map<String, Object> map);
    
    List<CarControlInfo> selectCarControlInfoByParamMap (@Param("map") Map<String, Object> map);
    int selectCarControlCountByParamMap (@Param("map") Map<String, Object> map);
}