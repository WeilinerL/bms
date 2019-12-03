package com.ups.demo.dao;

import com.ups.demo.pojo.Data;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DataMapper {

    @Select("select * from t_data where str_mac_address = #{MACAddress} and date_read_time between #{startTime} and #{endTime}")
    List<Data> selectAllByMACAddress(String MACAddress, String startTime, String endTime);

    int deleteByPrimaryKey(Integer intDataId);

    int insert(Data record);

    int insertSelective(Data record);

    Data selectByPrimaryKey(Integer intDataId);

    int updateByPrimaryKeySelective(Data record);

    int updateByPrimaryKey(Data record);
}