package com.bayee.dao;

import com.bayee.model.ProcessingEventImagelist;

public interface ProcessingEventImagelistMapper {
    int insert(ProcessingEventImagelist record);

    int insertSelective(ProcessingEventImagelist record);
}