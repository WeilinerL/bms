package com.ups.demo.dao;

import com.alibaba.fastjson.JSONObject;
import com.ups.demo.pojo.Device;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceMapper {

    @Select("select int_device_id,str_device_name,str_status from t_device where int_group_id = #{groupId}")
    List<JSONObject> selectByGroupId(int groupId);

    @Select("select int_group_id from t_device where str_user_tel = #{userName}")
    List<Integer> selectGroupIdByUserId(String userName);

    @Select("select int_group_id from t_device where int_device_id = #{deviceId}")
    int selectGroupIdByDeviceId(int deviceId);

    @Update("update t_device set int_group_id = #{groupId} where int_device_id = #{deviceId}")
    int updateDeviceByGroupId(int groupId, int deviceId);

    int deleteByPrimaryKey(Integer intDeviceId);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer intDeviceId);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);
}