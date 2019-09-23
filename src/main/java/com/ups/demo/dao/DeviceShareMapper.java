package com.ups.demo.dao;

import com.ups.demo.pojo.DeviceShare;
import org.springframework.stereotype.Component;

@Component
public interface DeviceShareMapper {
    int deleteByPrimaryKey(Integer intSharedId);

    int insert(DeviceShare record);

    int insertSelective(DeviceShare record);

    DeviceShare selectByPrimaryKey(Integer intSharedId);

    int updateByPrimaryKeySelective(DeviceShare record);

    int updateByPrimaryKey(DeviceShare record);
}