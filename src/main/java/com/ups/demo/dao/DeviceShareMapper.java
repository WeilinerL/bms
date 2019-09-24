package com.ups.demo.dao;

import com.ups.demo.pojo.DeviceShare;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceShareMapper {

    @Select("select int_device_id from t_device_share where str_shared_user_tel = #{userName}")
    List<Integer> selectDeviceIdByUserId(String userName);

    int deleteByPrimaryKey(Integer intSharedId);

    int insert(DeviceShare record);

    int insertSelective(DeviceShare record);

    DeviceShare selectByPrimaryKey(Integer intSharedId);

    int updateByPrimaryKeySelective(DeviceShare record);

    int updateByPrimaryKey(DeviceShare record);
}