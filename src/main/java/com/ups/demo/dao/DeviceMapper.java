package com.ups.demo.dao;

import com.alibaba.fastjson.JSONObject;
import com.ups.demo.pojo.Device;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceMapper {

    @Select("select int_device_id,str_device_name,str_status from t_device where int_device_id = #{deviceId}")
    JSONObject selectByDeviceId(int deviceId);

    @Select("select str_user_tel from t_device where int_device_id = #{deviceId}")
    String selectUserTelByDeviceId(int deviceId);

    @Select("select * from t_device where int_device_id = #{deviceId} and str_user_tel =#{userName}")
    Device selectAllByDeviceIdAndUserTel(int deviceId, String userName);

    @Select("select str_user_tel from t_device where str_code_id = #{deviceCode}")
    String selectUserTelByDeviceCode(String deviceCode);

    @Select("select str_device_address from t_device where int_device_id = #{deviceId}")
    String selectDeviceAdressByDeviceId(int deviceId);

    @Select("select int_detail_id from t_device where int_device_id = #{deviceId}")
    int selectDetailIdByDeviceId(int deviceId);

    @Select("select int_group_id from t_device where str_user_tel = #{userName}")
    List<Integer> selectGroupIdByUserId(String userName);

    @Select("select int_device_id from t_device where int_group_id = #{groupId}")
    List<Integer> selectDeviceIdByGroupId(int groupId);

    @Select("select int_device_id from t_device where int_group_id = #{groupId} and str_user_tel = #{userName}")
    List<Integer> selectDeviceIdByGroupIdAndUserName(int groupId, String userName);

    @Select("select int_device_id from t_device where str_user_tel = #{userName}")
    List<Integer> selectDeviceIdByUserName(String userName);

    @Select("select int_group_id from t_device where int_device_id = #{deviceId}")
    Integer selectGroupIdByDeviceId(int deviceId);

    @Select("select str_code_id from t_device where int_device_id = #{deviceId}")
    String selectCodeIdByDeviceId(int deviceId);

    @Update("update t_device set int_group_id = #{groupId} where int_device_id = #{deviceId}")
    int updateGroupIdByDeviceId(int groupId, int deviceId);

    @Update("update t_device set int_group_id = #{groupId} where int_device_id = #{deviceId} and str_user_tel = #{userName}")
    int updateGroupIdByDeviceIdAndUserName(int groupId, int deviceId, String userName);

    int deleteByPrimaryKey(Integer intDeviceId);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer intDeviceId);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);
}