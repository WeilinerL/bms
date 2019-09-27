package com.ups.demo.dao;

import com.alibaba.fastjson.JSONObject;
import com.ups.demo.pojo.Battery;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BatteryMapper {

    @Select("select double_voltage,double_temprature,double_resistan,double_capacity,int_battery_pack from t_battery where int_device_id = #{deviceID}")
    List<JSONObject> selectVTRCNByDeviceIdAndDeviceName(int deviceId);

    @Select("select * from t_battery where int_device_id = #{deviceId}")
    List<Battery> selectAllByDeviceId(int deviceId);

    int deleteByPrimaryKey(Integer intBatteryId);

    int insert(Battery record);

    int insertSelective(Battery record);

    Battery selectByPrimaryKey(Integer intBatteryId);

    int updateByPrimaryKeySelective(Battery record);

    int updateByPrimaryKey(Battery record);
}