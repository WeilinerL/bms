package com.ups.demo.dao;

import com.ups.demo.pojo.CloudBox;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface CloudBoxMapper {

    @Update("update t_cloud_box set str_code_id = #{codeId}")
    int updateByCodeId(String codeId);

    @Delete("delete from t_cloud_box where str_code_id = #{codeId}")
    int deleteByCodeId(String codeId);

    int deleteByPrimaryKey(Integer intCloudId);

    int insert(CloudBox record);

    int insertSelective(CloudBox record);

    CloudBox selectByPrimaryKey(Integer intCloudId);

    int updateByPrimaryKeySelective(CloudBox record);

    int updateByPrimaryKey(CloudBox record);
}