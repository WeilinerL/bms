package cn.crecode.bms.dao;

import cn.crecode.bms.pojo.DeviceDetail;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface DeviceDetailMapper {

    @Select("select str_device_model from t_device_detail where int_detail_id = #{intDetailId}")
    String selectDeviceModelById(int intDetailId);

    @Select("select int_detail_id from t_device_detail where str_device_brand = #{deviceBrand} and str_device_model = #{deviceModel}")
    Integer selectIdByDeviceBrandAndDeviceModel(String deviceBrand, String deviceModel);

    @Select("select str_device_brand,str_device_model from t_device_detail where int_detail_id = #{intDetailId}")
    JSONObject selectDeviceBrandAndDeviceModelById(int intDetailId);

    int deleteByPrimaryKey(Integer intDetailId);

    int insert(DeviceDetail record);

    int insertSelective(DeviceDetail record);

    DeviceDetail selectByPrimaryKey(Integer intDetailId);

    int updateByPrimaryKeySelective(DeviceDetail record);

    int updateByPrimaryKey(DeviceDetail record);
}