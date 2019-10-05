package com.ups.demo.dao;

import com.alibaba.fastjson.JSONObject;
import com.ups.demo.pojo.DeviceGroup;
import com.ups.demo.utils.JsonUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceGroupMapper {

    @Select("select * from t_device_group")
    List<DeviceGroup> selectAll();

    @Select("select * from t_device_group where str_user_name = #{userName}")
    List<DeviceGroup> selectAllByUserName(String userName);

    @Select("select * from t_device_group where int_group_id = #{groupid}")
    DeviceGroup selectAllByGroupId(int groupId);

    @Select("select int_group_id,str_group_name from t_device_group where str_user_name = #{userName}")
    List<JSONObject> selecGroupIdAndGroupNameByUserName(String UserName);

    @Select("select str_group_name from t_device_group where int_group_id = #{groupId}")
    String selectGroupNameByGroupId(int groupId);

    @Select("select str_user_name from t_device_group where int_group_id = #{groupId}")
    String selectUserNameByGroupId(int groupId);

    @Update("update t_device_group set str_group_name = #{newGroupName} where int_group_id = #{groupId} and str_user_name = #{userName}")
    int updateGroupNameByGroupIdAndUserName(String newGroupName, int groupId, String userName);

    @Delete("delete from t_device_group where int_group_id = #{groupId} and str_user_name = #{userName}")
    int deleteByGroupIdAndUserName(int groupId, String userName);

    int deleteByPrimaryKey(Integer intGroupId);

    int insert(DeviceGroup record);

    int insertSelective(DeviceGroup record);

    DeviceGroup selectByPrimaryKey(Integer intGroupId);

    int updateByPrimaryKeySelective(DeviceGroup record);

    int updateByPrimaryKey(DeviceGroup record);
}