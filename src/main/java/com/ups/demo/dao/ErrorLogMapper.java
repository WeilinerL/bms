package com.ups.demo.dao;

import com.ups.demo.pojo.ErrorLog;
import org.springframework.stereotype.Component;

@Component
public interface ErrorLogMapper {
    int deleteByPrimaryKey(Integer intId);

    int insert(ErrorLog record);

    int insertSelective(ErrorLog record);

    ErrorLog selectByPrimaryKey(Integer intId);

    int updateByPrimaryKeySelective(ErrorLog record);

    int updateByPrimaryKey(ErrorLog record);
}