package com.ups.demo.dao;

import com.ups.demo.pojo.Battery;
import org.springframework.stereotype.Component;

@Component
public interface BatteryMapper {
    int deleteByPrimaryKey(Integer intBatteryId);

    int insert(Battery record);

    int insertSelective(Battery record);

    Battery selectByPrimaryKey(Integer intBatteryId);

    int updateByPrimaryKeySelective(Battery record);

    int updateByPrimaryKey(Battery record);
}