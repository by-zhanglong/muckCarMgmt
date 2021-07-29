package com.bayee.dao;

import com.bayee.model.ProcessingEvent;

public interface ProcessingEventMapper {
    int insert(ProcessingEvent record);

    int insertSelective(ProcessingEvent record);
}