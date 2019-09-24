package com.ups.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.ups.demo.dao.DeviceGroupMapper;
import com.ups.demo.dao.DeviceMapper;
import com.ups.demo.dao.DeviceShareMapper;
import com.ups.demo.pojo.DeviceGroup;
import com.ups.demo.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DeviceGroupService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceShareMapper deviceShareMapper;

    @Autowired
    private DeviceGroupMapper deviceGroupMapper;

//    public List<DeviceGroupShow> getAllDeviceGroup() {
//        DeviceGroupShow deviceGroupShow = new DeviceGroupShow();
//        return deviceGroupShow.findChildren(deviceGroupMapper.selectAll());
//    }
//
//    public int renameGroup(String oldName, String newName) {
//        DeviceGroup deviceGroup = deviceGroupMapper.selectByLabel(oldName);
//        deviceGroup.setStrLabel(newName);
//        return deviceGroupMapper.updateByPrimaryKey(deviceGroup);
//    }
//
//    public int addGroup(String groupName) {
//        DeviceGroup deviceGroup = new DeviceGroup();
//        deviceGroup.setStrLabel(groupName);
//        deviceGroup.setStrType("parent");
//        deviceGroup.setStrShow("false");
//        return deviceGroupMapper.insert(deviceGroup);
//    }
//
//    public int deleteGroup(String groupName) {
//        int resultCount = 0;
//        DeviceGroup deviceGroup = deviceGroupMapper.selectByLabel(groupName);
//        if(deviceGroup.getStrChildren() != null) {
//            resultCount += deviceGroupMapper.deleteByLabel(groupName);
//            String[] childrenId = deviceGroup.getStrChildren().split(";");
//            for(String id : childrenId) {
//                int childId = Integer.parseInt(id);
//                resultCount += deviceGroupMapper.deleteByPrimaryKey(childId);
//            }
//            if(resultCount == childrenId.length + 1) {
//                return 1;
//            }else {
//                return 0;
//            }
//        }else {
//            return deviceGroupMapper.deleteByLabel(groupName);
//        }
//    }

    public List<DeviceGroup> getAllGroupName(String userName) {
        List<DeviceGroup> groupList = new ArrayList<>();
        List<Integer> groupIdList = deviceMapper.selectGroupIdByUserId(userName);
        List<Integer> deviceIdList = deviceShareMapper.selectDeviceIdByUserId(userName);
        Iterator<Integer> integerIterator = groupIdList.iterator();
        Iterator<Integer> integerIterator2 = deviceIdList.iterator();
        // 自己拥有的设备
        while (integerIterator.hasNext()) {
            int groupId = integerIterator.next();
            DeviceGroup deviceGroup = deviceGroupMapper.selectAllByGroupId(groupId);
            if(groupList.size() >= deviceGroup.getIntGroupId()) {
                if (groupList.get(deviceGroup.getIntGroupId()) != null) {
                    continue;
                } else {
                    groupList.add(deviceGroup);
                }
            } else {
                groupList.add(deviceGroup);
            }
        }
        // 别人分享的设备
        while (integerIterator2.hasNext()) {
            int deviceId = integerIterator2.next();
            int groupId = deviceMapper.selectGroupIdByDeviceId(deviceId);
            DeviceGroup deviceGroup = deviceGroupMapper.selectAllByGroupId(groupId);
            if(groupList.size() >= deviceGroup.getIntGroupId()) {
                if (groupList.get(deviceGroup.getIntGroupId()) != null) {
                    continue;
                } else {
                    groupList.add(deviceGroup);
                }
            } else {
                groupList.add(deviceGroup);
            }
        }
        return groupList;
    }

    public int addDevice(int groupId, List<JSONObject> deviceList) {
        Iterator<JSONObject> jsonObjectIterator = deviceList.iterator();
        int counter = 0;
        while (jsonObjectIterator.hasNext()) {
            int deviceId = jsonObjectIterator.next().getInteger("deviceID");
            if(deviceMapper.updateDeviceByGroupId(groupId,deviceId) != 0) {
                counter ++;
            }
        }
        return counter;
    }

}
