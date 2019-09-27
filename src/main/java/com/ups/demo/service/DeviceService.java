package com.ups.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.ups.demo.dao.*;
import com.ups.demo.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;
    //使用事物可以保证数据的一致性和完整性(避免异常和错误等导致的数据信息异常)

    @Autowired
    private DeviceShareMapper deviceShareMapper;

    @Autowired
    private DeviceGroupMapper deviceGroupMapper;

    @Autowired
    private DeviceDetailMapper deviceDetailMapper;

    @Autowired
    private BatteryMapper batteryMapper;

    @Autowired
    private CloudBoxMapper cloudBoxMapper;

    //查询所有设备
    //返回值为一组用于展示的List集合数据 格式为jason
//    @Scheduled(cron = "* 0/5 * * * ?") //每五分钟执行一次(数据五分钟采集一次)
//    @Transactional(readOnly = true)
//    public List<DeviceListHome> getAllDevice() {
//        try {
//            Thread.sleep(10);
//        }catch(Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        List<DeviceListHome> deviceListHomes = new ArrayList<>();
//        List<Device> deviceList = deviceMapper.getAllDevice();
//        Iterator<Device>iterator = deviceList.iterator();
//        while (iterator.hasNext()) {
//            //根据型号找出对应的类型
//            device = iterator.next();
//            String modelId = device.getStrModelId();
//            String seriesId = null;
//            String brandId = null;
//            String typeId = null;
//            //以下三个if皆为避免空指针异常(相关表并没有数据)
//            if(modelSeriesMapper.selectModelSeries(modelId) != null) {
//                //根据型号查对应的系列
//                seriesId = modelSeriesMapper.selectModelSeries(modelId).getStrSeriesId();
//
//            }
//            if(seriesBrandMapper.selectSeriesBrand(seriesId) != null) {
//                //根据系列查对应的品牌
//                brandId = seriesBrandMapper.selectSeriesBrand(seriesId).getStrBrandId();
//            }
//            if(brandTypeMapper.selectBrandType(brandId) != null) {
//                //根据品牌查对应的类型
//                typeId = brandTypeMapper.selectBrandType(brandId).getStrTyprId();
//            }
//            deviceListHome.setDevice_name(device.getStrDeviceName());
//            deviceListHome.setDevice_id(device.getStrId());
//            deviceListHome.setDevice_modle(modelId);
//            deviceListHome.setDevice_type(typeId);
//            deviceListHome.setDevice_status(alarmCheck());
//            deviceListHomes.add(deviceListHome);
//        }
//        return deviceListHomes;
//    }

    /**
     * 采集在系统当前时间左右五分钟内的所匹配的数据
     * @return
     */

//    public String alarmCheck() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date now = new Date();
//        //当前时间向前加五分钟或者向后减五分钟
//        //用于捕获采集存储到相应表里的数据
//        String date = sdf.format(new Date(now.getTime()));
//        String date1 = sdf.format(new Date(now.getTime() + 60000));
//        String date2 = sdf.format(new Date(now.getTime() + 120000));
//        String date3 = sdf.format(new Date(now.getTime() + 180000));
//        String date4 = sdf.format(new Date(now.getTime() + 240000));
//        String date5 = sdf.format(new Date(now.getTime() + 300000));
//        String date6 = sdf.format(new Date(now.getTime() - 60000));
//        String date7 = sdf.format(new Date(now.getTime() - 120000));
//        String date8 = sdf.format(new Date(now.getTime() - 180000));
//        String date9 = sdf.format(new Date(now.getTime() - 240000));
//        String date10 = sdf.format(new Date(now.getTime() - 300000));
//        data = dataMapper.selectByTime("%" + date + "%", "%" + date1 + "%", "%" + date2 + "%", "%" + date3 + "%", "%" + date4 + "%", "%" + date5 + "%",
//                "%" + date6 + "%", "%" + date7 + "%", "%" + date8 + "%", "%" + date9 + "%", "%" + date10 + "%");
//        if(data != null) {
//            if(data.getDoubleParsedValues().equals("0") || data.getStrDataType().equals("状态量")) {
//                return "正常";
//            }else {
//                return "异常";
//            }
//        }
//        return "暂无数据";
//    }


    //查询单个设备
//    @Transactional(readOnly = true)
//    public Device getDevice(int deviceId) {
//        try {
//            Thread.sleep(10);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return deviceMapper.selectByPrimaryKey(deviceId);
//    }

    @Transactional(readOnly = true)
    public List<Battery> getDeviceDetail(int deviceId) {
        List<Battery> jsons = batteryMapper.selectAllByDeviceId(deviceId);
        if(jsons.size() == 0) {
            return null;
        }
        return jsons;
    }

    @Transactional(readOnly = true)
    public JSONObject getDeviceMainDetail(int deviceId, String userName) {
        int deviceDetailId = deviceMapper.selectDetailIdByDeviceId(deviceId);
        Integer deviceGroupId = deviceMapper.selectGroupIdByDeviceId(deviceId);
        JSONObject jsonObject = deviceDetailMapper.selectDeviceBrandAndDeviceModelById(deviceDetailId);
        Device device = deviceMapper.selectAllByDeviceIdAndUserTel(deviceId,userName);
        if(device != null) {
            String groupName = deviceGroupMapper.selectGroupNameByGroupId(deviceGroupId);
            JSONObject json = new JSONObject();
            json.put("groupID",deviceGroupId);
            json.put("groupName",groupName);
            json.put("deviceCode",device.getStrCodeId());
            json.put("deviceBrand",jsonObject.getString("str_device_brand"));
            json.put("deviceModel",jsonObject.getString("str_device_model"));
            json.put("deviceAddress",device.getStrDeviceAddress() == null ? "暂无" : device.getStrDeviceAddress());
            return json;
        } else {
            device = deviceMapper.selectByPrimaryKey(deviceId);
            deviceGroupId = deviceShareMapper.selectGroupIdByDeviceIdAndUserName(deviceId,userName);
            if(deviceGroupId != null) {
                String groupName = deviceGroupMapper.selectGroupNameByGroupId(deviceGroupId);
                JSONObject json = new JSONObject();
                json.put("groupID",deviceGroupId);
                json.put("groupName",groupName);
                json.put("deviceCode",device.getStrCodeId());
                json.put("deviceBrand",jsonObject.getString("str_device_brand"));
                json.put("deviceModel",jsonObject.getString("str_device_model"));
                json.put("deviceAddress",device.getStrDeviceAddress() == null ? "暂无" : device.getStrDeviceAddress());
                return json;
            } else {
                return new JSONObject();
            }
        }
    }

    //添加设备

    @Transactional(propagation = Propagation.REQUIRED)
    public int addDeviceCloudBoxInfo(
            String codeId,
            String macAddress,
            String seriesCode,
            String seriesName,
            String version,
            Date manufactureTime,
            String serialNumber) {
        CloudBox cloudBox = new CloudBox();
        cloudBox.setDateOfManufacture(manufactureTime);
        cloudBox.setStrCodeId(codeId);
        cloudBox.setStrMacAddress(macAddress);
        cloudBox.setStrSerialNumber(serialNumber);
        cloudBox.setStrSeriesCode(seriesCode);
        cloudBox.setStrSeriesName(seriesName);
        cloudBox.setStrVersion(version);
        return cloudBoxMapper.insert(cloudBox);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addDeviceDetail(String deviceBrand, String deviceModel) {
        DeviceDetail deviceDetail = new DeviceDetail();
        deviceDetail.setStrDeviceBrand(deviceBrand);
        deviceDetail.setStrDeviceModel(deviceModel);
        return deviceDetailMapper.insert(deviceDetail);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int addDevcice(String deviceCode,
                          String userName,
                          String deviceName,
                          String deviceBrand,
                          String deviceModel,
                          String macAddress,
                          String seriesCode,
                          String seriesName,
                          String version,
                          Date manufactureTime,
                          String serialNumber) {
        addDeviceCloudBoxInfo(deviceCode,macAddress,seriesCode,seriesName,version,manufactureTime,serialNumber);
        Integer detailId = deviceDetailMapper.selectIdByDeviceBrandAndDeviceModel(deviceBrand,deviceModel);
        if( detailId!= null) {
            Device device = new Device();
            device.setStrCodeId(deviceCode);
            device.setStrUserTel(userName);
            device.setIntDetailId(detailId);
            device.setStrDeviceName(deviceName);
            device.setIntGroupId(0);
            device.setStrStatus("正常");
            return deviceMapper.insert(device);
        } else {
            addDeviceDetail(deviceBrand,deviceModel);
            detailId = deviceDetailMapper.selectIdByDeviceBrandAndDeviceModel(deviceBrand,deviceModel);
            Device device = new Device();
            device.setStrCodeId(deviceCode);
            device.setStrUserTel(userName);
            device.setIntDetailId(detailId);
            device.setStrDeviceName(deviceName);
            device.setIntGroupId(0);
            device.setStrStatus("正常");
            return deviceMapper.insert(device);
        }

    }

    //删除设备

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteDeviceOfCloudBox(String codeId) {
        return cloudBoxMapper.deleteByCodeId(codeId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteDevice(String userName, int deviceId) {
        if(deviceMapper.selectUserTelByDeviceId(deviceId).equals(userName)) {
            deleteDeviceOfCloudBox(deviceMapper.selectByPrimaryKey(deviceId).getStrCodeId());
            return deviceMapper.deleteByPrimaryKey(deviceId);
        }else {
            return deviceShareMapper.deleteByDeviceIdAndUserName(deviceId,userName);
        }
    }

    // 更改设备

    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyCloudBoxCodeId(String deviceCode) {
        return cloudBoxMapper.updateByCodeId(deviceCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyDeviceDetail(String deviceBrand, String deviceModel) {
        // 如果改的详情仍然在详情表中存在 就不改
        Integer detailId = deviceDetailMapper.selectIdByDeviceBrandAndDeviceModel(deviceBrand,deviceModel);
        if( detailId!= null) {
            return 1;
        }else {
            // 反之 则重新创建一个详情
            return addDeviceDetail(deviceBrand,deviceModel);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyDevice(int deviceId, String deviceCode, String userName, String deviceName, String deviceBrand, String deviceModel, String deviceAddress) {
        // 只有主用户可以更改设备
        if(deviceMapper.selectUserTelByDeviceId(deviceId).equals(userName)) {
            int detailId = deviceDetailMapper.selectIdByDeviceBrandAndDeviceModel(deviceBrand,deviceModel);
            Device device = deviceMapper.selectAllByDeviceIdAndUserTel(deviceId,userName);
            if(device != null) {
                modifyDeviceDetail(deviceBrand,deviceModel);
                modifyCloudBoxCodeId(deviceCode);
                device.setStrCodeId(deviceCode);
                device.setStrDeviceName(deviceName);
                device.setStrDeviceAddress(deviceAddress);
                device.setIntDetailId(detailId);
                return deviceMapper.updateByPrimaryKey(device);
            }
            return 0;
        }else {
            return 0;
        }
    }

}
