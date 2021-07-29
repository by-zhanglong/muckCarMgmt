package com.bayee.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bayee.model.WarnDetail;

public interface WarnDetailMapper {
    int deleteByPrimaryKey(Integer wdId);

    int insert(WarnDetail record);

    int insertSelective(WarnDetail record);

    WarnDetail selectByPrimaryKey(Integer wdId);

    int updateByPrimaryKeySelective(WarnDetail record);

    int updateByPrimaryKey(WarnDetail record);
    int selectcountByRuleTypeId(Integer ruleTypeId);
    List<WarnDetail> selectWarnDetailListByParaMap(@Param("map") Map<String, Object> map);
    int selectWarnDetailcountByParaMap(@Param("map") Map<String, Object> map);
}