package com.ups.demo.dao;

import com.ups.demo.pojo.DeviceGroup;
import org.springframework.stereotype.Component;

@Component
public interface DeviceGroupMapper {
    int deleteByPrimaryKey(Integer intGroupId);

    int insert(DeviceGroup record);

    int insertSelective(DeviceGroup record);

    DeviceGroup selectByPrimaryKey(Integer intGroupId);

    int updateByPrimaryKeySelective(DeviceGroup record);

    int updateByPrimaryKey(DeviceGroup record);
}