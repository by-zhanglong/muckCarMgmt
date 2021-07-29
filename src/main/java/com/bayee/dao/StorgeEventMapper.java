package com.bayee.dao;

import java.util.List;

import com.bayee.model.StorgeEvent;

public interface StorgeEventMapper {
    int insert(StorgeEvent record);

    int insertSelective(StorgeEvent record);
    List<StorgeEvent> selectStorgEventByplateNum(String plateNumA);
}