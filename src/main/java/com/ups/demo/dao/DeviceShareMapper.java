package com.ups.demo.dao;

import com.ups.demo.pojo.DeviceShare;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceShareMapper {

    @Select("select int_device_id from t_device_share where str_shared_user_tel = #{userName}")
    List<Integer> selectDeviceIdByUserId(String userName);

    @Select("select str_user_tel from t_device_share where int_device_id = #{deviceId}")
    String selectUserTelByDeviceId(int deviceId);

    @Select("select int_device_id from t_device_share where int_group_id = #{groupId}")
    List<Integer> selectDeviceIdByGroupId(int groupId);

    @Select("select int_device_id from t_device_share where int_group_id = #{groupId} and str_shared_user_tel = #{userName}")
    List<Integer> selectDeviceIdByGroupIdAndUserName(int groupId, String userName);

    @Select("select int_device_id from t_device_share where str_shared_user_tel = #{userName}")
    List<Integer> selectDeviceIdByUserName(String userName);

    @Select("select int_group_id from t_device_share where str_shared_user_tel = #{userName}")
    List<Integer> selectGroupIdByUserId(String userName);

    @Update("update t_device_share set int_group_id = #{groupId} where int_device_id = #{deviceId}")
    int updateGroupIdByDeviceId(int groupId, int deviceId);

    @Update("update t_device_share set int_group_id = #{groupId} where int_device_id = #{deviceId} and str_shared_user_tel = #{userName}")
    int updateGroupIdByDeviceIdAndUserName(int groupId, int deviceId, String userName);

    int deleteByPrimaryKey(Integer intSharedId);

    int insert(DeviceShare record);

    int insertSelective(DeviceShare record);

    DeviceShare selectByPrimaryKey(Integer intSharedId);

    int updateByPrimaryKeySelective(DeviceShare record);

    int updateByPrimaryKey(DeviceShare record);
}