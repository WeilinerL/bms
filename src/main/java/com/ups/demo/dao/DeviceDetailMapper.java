package com.ups.demo.dao;

import com.ups.demo.pojo.DeviceDetail;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface DeviceDetailMapper {

    @Select("select str_device_model from t_device_detail where int_detail_id = #{intDetailId}")
    String selectDeviceModelById(int intDetailId);

    int deleteByPrimaryKey(Integer intDetailId);

    int insert(DeviceDetail record);

    int insertSelective(DeviceDetail record);

    DeviceDetail selectByPrimaryKey(Integer intDetailId);

    int updateByPrimaryKeySelective(DeviceDetail record);

    int updateByPrimaryKey(DeviceDetail record);
}