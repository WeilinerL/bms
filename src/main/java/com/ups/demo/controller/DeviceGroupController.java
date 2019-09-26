package com.ups.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ups.demo.service.DeviceGroupService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/device_group")
public class DeviceGroupController {

    @Autowired
    private DeviceGroupService deviceGroupService;

    private final static Log log = LogFactory.getLog(DeviceController.class);

//    @GetMapping(value = "get_all_device_group")
//    public List<DeviceGroupShow> getAllDeviceGroup() {
//        if(log.isTraceEnabled()) {
//            log.trace("获取所有的分组信息");
//        }
//        return deviceGroupService.getAllDeviceGroup();
//    }
//
//    @PutMapping(value = "rename_group/{oldName}/{newName}")
//    public Map<String,String> renameGroup(@PathVariable String oldName, @PathVariable String newName) {
//        Map<String,String> result = new HashMap<>();
//        if(!newName.equals("")) {
//            if(deviceGroupService.renameGroup(oldName,newName) != 0) {
//                result.put("result","success");
//                return result;
//            }
//            result.put("result","fail");
//            return result;
//        }else {
//            result.put("result","fail");
//            return result;
//        }
//    }
//
//    @PostMapping(value = "add_group/{groupName}")
//    public Map<String,String> addGroup(@PathVariable String groupName) {
//        Map<String,String> result = new HashMap<>();
//        if(!groupName.equals("")) {
//            if(deviceGroupService.addGroup(groupName) != 0) {
//                result.put("result","success");
//                return result;
//            }
//            result.put("result","fail");
//            return result;
//        }else {
//            result.put("result","fail");
//            return result;
//        }
//    }
//
//    @DeleteMapping(value = "delete_group/{groupName}")
//    public Map<String,String> deleteGroup(@PathVariable String groupName) {
//        Map<String,String> result = new HashMap<>();
//        if(!groupName.equals("")) {
//            if(deviceGroupService.deleteGroup(groupName) != 0) {
//                result.put("result","success");
//                return result;
//            }
//            result.put("result","fail");
//            return result;
//        }else {
//            result.put("result","fail");
//            return result;
//        }
//    }

    /**
     * 获取所有分组信息(只包含分组名及其id)
     * @param JSONCONTENT {userName: String}
     * @return {code: int, data: [{intGroupId: id, strGroupName: String}]}
     */

    @GetMapping(value = "get_all_device_group")
    public ResponseEntity<Map<String, Object>> getAllDeviceGroup(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if(JSONCONTENT.contains("userName")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            String userName = json.getString("userName");
            if(log.isTraceEnabled()) {
                log.trace("获取所有分组(不包含设备) 用户名为: " + userName);
            }
            result.put("code",1);
            result.put("data",deviceGroupService.getAllGroupName(userName));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 往分组里添加设备 添加的是未分组的设备
     * @param JSONCONTENT {groupID: int, deviceList: [{deviceID: int}]}
     * @return {code: int}
     */

    @PostMapping(value = "add_device")
    public ResponseEntity<Map<String, Object>> addDevice(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if(JSONCONTENT.contains("groupID") && JSONCONTENT.contains("deviceList") && JSONCONTENT.contains("userName")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            int groupId = json.getInteger("groupID");
            List<JSONObject> deviceIds = (List<JSONObject>) json.get("deviceList");
            String userName = json.getString("userName");
            if(log.isTraceEnabled()) {
                log.trace("往用户名为: " + userName + " 的分组中添加设备表 分组id: " + groupId + "添加的设备有 " + deviceIds.size() + " 个");
            }
            int insertSuccess = deviceGroupService.addDevice(groupId,deviceIds,userName);
            if(log.isTraceEnabled()) {
                log.trace("添加设备成功了 " + insertSuccess + " 个");
            }
            if(insertSuccess != 0) {
                result.put("code",1);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 删除分组
     * @param JSONCONTENT {groupId: int}
     * @return {code: int}
     */

    @DeleteMapping(value = "delete_group")
    public ResponseEntity<Map<String, Object>> deleteGroup(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if(JSONCONTENT.contains("groupID") && JSONCONTENT.contains("userName")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            int groupId = json.getInteger("groupID");
            String userName = json.getString("userName");
            if(log.isTraceEnabled()) {
                log.trace("删除分组 分组id为: " + groupId + " 分组用户为: " + userName);
            }
            if(deviceGroupService.deleteGroup(groupId,userName) != 0) {
                if(log.isTraceEnabled()) {log.trace("删除分组操作成功!");}
                result.put("code",1);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else {
                if(log.isTraceEnabled()) {log.trace("删除分组操作失败!");}
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 重命名分组
     * @param JSONCONTENT {groupID: int, newGroupName: String}
     * @return {code: int}
     */

    @PutMapping(value = "group_rename")
    public ResponseEntity<Map<String, Object>> groupRename(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if(JSONCONTENT.contains("groupID") && JSONCONTENT.contains("newGroupName") && JSONCONTENT.contains("userName")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            int groupId = json.getInteger("groupID");
            String newGroupName = json.getString("newGroupName");
            String userName = json.getString("userName");
            if(log.isTraceEnabled()) {
                log.trace("重命名分组 新的分组名为: " + newGroupName + " 用户名为: " + userName);
            }
            if(deviceGroupService.groupRename(groupId,newGroupName,userName) != 0) {
                if(log.isTraceEnabled()) {log.trace("重命名分组操作成功!");}
                result.put("code",1);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                if(log.isTraceEnabled()) {log.trace("重命名分组操作失败!");}
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 移动设备
     * @param JSONCONTENT {deviceID:int groupID: int}
     * @return {code: int}
     */

    @PutMapping(value = "move_device")
    public ResponseEntity<Map<String, Object>> moveGroup(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if (JSONCONTENT.contains("deviceID") && JSONCONTENT.contains("groupID") && JSONCONTENT.contains("userName")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            int deviceId = json.getInteger("deviceID");
            int groupId = json.getInteger("groupID");
            String userName = json.getString("userName");
            if(log.isTraceEnabled()) {
                log.trace("移动设备 设备id:  " + deviceId + " 新的分组id: " + groupId);
            }
            if(deviceGroupService.moveDevice(deviceId,groupId,userName) != 0) {
                if(log.isTraceEnabled()) {log.trace("移动分组操作成功!");}
                result.put("code",1);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                if(log.isTraceEnabled()) {log.trace("移动分组操作失败!");}
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 获取所有设备
     * @param JSONCONTENT {userName: String}
     * @return {code: String, data: {[groupId: int, groupName: String, detail:[{intDeviceID: int, strDeviceName: String, strStatus: String, strDeviceModel: String}]]}}
     */

    @GetMapping(value = "get_all_device")
    public ResponseEntity<Map<String, Object>> getAllDevice(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if (JSONCONTENT.contains("userName")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            String userName = json.getString("userName");
            if(log.isTraceEnabled()) {
                log.trace("获取所有分组(包含设备) 用户名为: " + userName);
            }
            result.put("code",1);
            result.put("data",deviceGroupService.getAllDevice(userName));
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @PostMapping(value = "add_group")
    public ResponseEntity<Map<String, Object>> addGroup(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if (JSONCONTENT.contains("userName") && JSONCONTENT.contains("newGroupName")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            String userName = json.getString("userName");
            String newGroupName = json.getString("newGroupName");
            if(log.isTraceEnabled()) {
                log.trace("添加分组 新的分组名为: " + newGroupName + " 用户名为: " + userName);
            }
            if(deviceGroupService.addGroup(userName,newGroupName) != 0) {
                if(log.isTraceEnabled()) {log.trace("添加分组操作成功!");}
                result.put("code",1);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                if(log.isTraceEnabled()) {log.trace("添加分组操作失败!");}
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

}
