package com.ups.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ups.demo.service.DeviceService;
import com.ups.demo.utils.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    private final static Log log = LogFactory.getLog(DeviceController.class);

//    //注册用户都有资格访问所有设备
//    @GetMapping(value = "device_list")
//    @PreAuthorize("hasAnyRole('customer','admin')")
//    public List<DeviceListHome> getAll() {
//        if (log.isTraceEnabled()) {
//            log.trace("get all devices");
//        }
////        return deviceService.getAllDevice();
//        return null;
//    }

//    @GetMapping(value = "device_group")
//    @PreAuthorize("hasAnyRole('customer','admin')")
//    public String getDeviceGroup() {
//        return "writing...";
//    }

    /**
     * 查询设备详情 所有电池
     * @param deviceId int
     * @param deviceName String
     * @return {code: int, data: [{intBatteryId, int, intDeviceId: int, doubleVoltage: String, doubleTemprature: String, doubleResistan: String, doubleCapacity: String, intBatteryPack: int, dataReadTime: DATETIME}]}
     */

    @GetMapping(value = "get_device_detail")
    public ResponseEntity<Map<String, Object>> getDeviceDetail(@RequestParam(value = "deviceID") Integer deviceId, @RequestParam(value = "deviceName") String deviceName) throws ParseException {
        HashMap<String, Object> result = new HashMap<>();
        if(deviceId != null && deviceName != null) {
            if(log.isTraceEnabled()) {
                log.trace("查询设备详情(所有电池) 设备id: " + deviceId + " 设备名称: " + deviceName);
            }
            result.put("code",1);
            result.put("data",deviceService.getDeviceDetail(deviceId));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @GetMapping(value = "get_device_history_data")
    public ResponseEntity<Map<String, Object>> getDeviceHistoryData(@RequestParam(value = "deviceID") Integer deviceId, @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) throws ParseException {
        HashMap<String, Object> result = new HashMap<>();
        if(deviceId != null && startTime != null && endTime != null) {
            if(log.isTraceEnabled()) {
                log.trace("获取设备历史数据,设备ID: " + deviceId + " 开始时间: " + startTime + " 结束时间: " + endTime);
            }
            result.put("code",1);
            result.put("data",deviceService.getDeviceHistory(deviceId,startTime,endTime));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 查询设备的详情
     * @param deviceId int
     * @param userName String
     * @return {code: int, data: {groupID: int, groupName: String, deviceBrand: String, deviceModel: String, deviceAddress: String}}
     */

    @GetMapping(value = "get_device_main_detail")
    public ResponseEntity<Map<String, Object>> getDeviceMainDetail(@RequestParam(value = "deviceID") Integer deviceId, @RequestParam(value = "userName") String userName) {
        HashMap<String, Object> result = new HashMap<>();
        if (deviceId != null && userName != null) {
            if(log.isTraceEnabled()) {
                log.trace("查询设备详情 设备id: " + deviceId + " 用户名: " + userName);
            }
            result.put("code",1);
            result.put("data",JsonUtils.convert(deviceService.getDeviceMainDetail(deviceId,userName).toString()));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 添加设备
     * @param JSONCONTENT {deviceCode: int, userName: String, deviceBrand: String, devieModel: String, deviceName: String}
     * @return {code: int}
     */

    @PostMapping(value = "add_device")
    public ResponseEntity<Map<String, Object>> addDevice(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if (JSONCONTENT.contains("deviceCode")
                && JSONCONTENT.contains("userName")
                && JSONCONTENT.contains("deviceBrand")
                && JSONCONTENT.contains("deviceModel")
                && JSONCONTENT.contains("deviceName")
                && JSONCONTENT.contains("macAddress")
                && JSONCONTENT.contains("seriesCode")
                && JSONCONTENT.contains("seriesName")
                && JSONCONTENT.contains("version")
                && JSONCONTENT.contains("manufactureTime")
                && JSONCONTENT.contains("serialNumber")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            String deviceCode = json.getString("deviceCode");
            String userName = json.getString("userName");
            String deviceBrand = json.getString("deviceBrand");
            String deviceModel = json.getString("deviceModel");
            String deviceName = json.getString("deviceName");
            String macAddress = json.getString("macAddress");
            String seriesCode = json.getString("seriesCode");
            String seriesName = json.getString("seriesName");
            String version = json.getString("version");
            Date manufactureTime = json.getDate("manufactureTime");
            String serialNumber = json.getString("serialNumber");
            if(log.isTraceEnabled()) {
                log.trace("添加设备 云盒id: " + deviceCode + " 用户名: " + userName + " 设备品牌: " + deviceBrand + " 设备型号：" + deviceModel + " 设备名称: " + deviceName);
            }
            if(deviceService.addDevcice(deviceCode,
                    userName,deviceName,deviceBrand,deviceModel,
                    macAddress,seriesCode,seriesName,version,manufactureTime,serialNumber) != 0) {
                if(log.isTraceEnabled()) {log.trace("添加设备成功!");}
                result.put("code",1);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else {
                if(log.isTraceEnabled()) {log.trace("添加设备失败!");}
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 删除设备
     * @param userName, deviceID
     * @return {code: int}
     */

    @DeleteMapping(value = "delete_device")
    public ResponseEntity<Map<String, Object>> deleteDevice(@RequestParam(value = "userName") String  userName, @RequestParam(value = "deviceID") Integer deviceID) {
        HashMap<String, Object> result = new HashMap<>();
        if (userName != null && deviceID != null) {
            Map<String,Integer> rs = deviceService.deleteDevice(userName,deviceID);
            if(rs.get("owner") == 1) {
                if(log.isTraceEnabled()) {log.trace("删除设备成功!");}
                JSONObject data = new JSONObject();
                data.put("deleteType",1);
                result.put("code",1);
                result.put("data",data);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else if (rs.get("share") == 1) {
                if(log.isTraceEnabled()) {log.trace("删除分享设备成功!");}
                JSONObject data = new JSONObject();
                data.put("deleteType",2);
                result.put("code",1);
                result.put("data",data);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else{
                if(log.isTraceEnabled()) {log.trace("删除设备失败!");}
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 修改设备
     * @param JSONCONTENT {deviceCode: int, userName: String, deviceBrand: String, devieModel: String, deviceName: String, deviceAddrss: String}
     * @return {code: int}
     */

    @PutMapping(value = "modify_device")
    public ResponseEntity<Map<String, Object>> modifyDevice(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if (JSONCONTENT.contains("deviceID") && JSONCONTENT.contains("userName") && JSONCONTENT.contains("deviceBrand") && JSONCONTENT.contains("deviceModel") && JSONCONTENT.contains("deviceName") && JSONCONTENT.contains("deviceAddress")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            int deviceID = json.getInteger("deviceID");
            String userName = json.getString("userName");
            String deviceBrand = json.getString("deviceBrand");
            String deviceModel = json.getString("deviceModel");
            String deviceName = json.getString("deviceName");
            String deviceAddress = json.getString("deviceAddress");
            if (log.isTraceEnabled()) {
                log.trace("修改设备 设备id" + deviceID + " 用户名: " + userName + " 设备品牌: " + deviceBrand + " 设备型号：" + deviceModel + " 设备名称: " + deviceName + " 设备地址: " + deviceAddress);
            }
            String rs = deviceService.modifyDevice(deviceID,userName,deviceName,deviceBrand,deviceModel,deviceAddress);
            if(rs.equals("success")) {
                if(log.isTraceEnabled()) {log.trace("修改设备成功!");}
                JSONObject data = new JSONObject();
                data.put("message","success");
                result.put("code",1);
                result.put("data",data);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else if(rs.equals("wrong")) {
                if(log.isTraceEnabled()) {log.trace("修改设备失败!不是主用户");}
                JSONObject data = new JSONObject();
                data.put("message","fail");
                result.put("code",1);
                result.put("data",data);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else{
                if(log.isTraceEnabled()) {log.trace("修改设备失败!");}
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @PostMapping(value = "share_device")
    public ResponseEntity<Map<String, Object>> shareDevice(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if (JSONCONTENT.contains("deviceID") && JSONCONTENT.contains("sharedUserName")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            int deviceId = json.getInteger("deviceID");
            List<JSONObject> userNameList = (List<JSONObject>) json.get("sharedUserName");
            if(log.isTraceEnabled()) {
                log.trace("分享设备 设备ID: " + deviceId + " 被分享者: " + userNameList);
            }
            JSONObject resultCount = deviceService.shareDevice(deviceId,userNameList);
            if(resultCount.getInteger("successCount") != 0) {
                if(log.isTraceEnabled()) {log.trace("分享设备成功了 " + resultCount.getInteger("successCount") + "个");}
                result.put("code",1);
                result.put("data",resultCount.get("shareSuccessList"));
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                if(log.isTraceEnabled()) {log.trace("分享设备失败!");}
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }
}
