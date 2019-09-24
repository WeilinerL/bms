package com.ups.demo.dao;

import com.ups.demo.pojo.DeviceGroup;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceGroupMapper {

    @Select("select * from t_device_group")
    List<DeviceGroup> selectAll();

    @Select("select * from t_device_group where int_group_id = #{groupid}")
    DeviceGroup selectAllByGroupId(int groupId);

    @Select("select str_group_name from t_device_group where int_group_id = #{groupid}")
    String selectGroupNameByGroupId(int groupId);

    int deleteByPrimaryKey(Integer intGroupId);

    int insert(DeviceGroup record);

    int insertSelective(DeviceGroup record);

    DeviceGroup selectByPrimaryKey(Integer intGroupId);

    int updateByPrimaryKeySelective(DeviceGroup record);

    int updateByPrimaryKey(DeviceGroup record);
}