package cn.crecode.bms.service;

import cn.crecode.bms.dao.DeviceDetailMapper;
import cn.crecode.bms.dao.DeviceGroupMapper;
import cn.crecode.bms.dao.DeviceMapper;
import cn.crecode.bms.dao.DeviceShareMapper;
import cn.crecode.bms.pojo.DeviceGroup;
import cn.crecode.bms.utils.JsonUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private DeviceDetailMapper deviceDetailMapper;

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

//    public List<DeviceGroup> getAllGroupName(String userName) {
//        List<DeviceGroup> groupList = new ArrayList<>();
//        List<Integer> groupIdList = deviceMapper.selectGroupIdByUserId(userName);
//        List<Integer> sharedGroupIdList = deviceShareMapper.selectGroupIdByUserId(userName);
//        Iterator<Integer> integerIterator = groupIdList.iterator();
//        Iterator<Integer> integerIterator2 = sharedGroupIdList.iterator();
//        Boolean flag = false;
//        // 自己拥有的设备
//        while (integerIterator.hasNext()) {
//            int groupId = integerIterator.next();
//            DeviceGroup deviceGroup = deviceGroupMapper.selectAllByGroupId(groupId);
//            if(groupList.size() >= deviceGroup.getIntGroupId() && groupList.size() != 0) {
//                Iterator<DeviceGroup> deviceGroupIterator = groupList.iterator();
//                //查询groupList里是否已经包含该分组了
//                while (deviceGroupIterator.hasNext()) {
//                    DeviceGroup deviceGroup2 = deviceGroupIterator.next();
//                    if (deviceGroup2.getIntGroupId() == deviceGroup.getIntGroupId()) {
//                        flag = true;
//                        break;
//                    }
//                }
//                if(!flag) {
//                    groupList.add(deviceGroup);
//                }
//                flag = false; //复位
//            } else {
//                groupList.add(deviceGroup);
//            }
//        }
//        // 别人分享的设备
//        while (integerIterator2.hasNext()) {
//            int groupId = integerIterator2.next();
//            DeviceGroup deviceGroup = deviceGroupMapper.selectAllByGroupId(groupId);
//            if(groupList.size() >= deviceGroup.getIntGroupId() && groupList.size() != 0) {
//                Iterator<DeviceGroup> deviceGroupIterator = groupList.iterator();
//                //查询groupList里是否已经包含该分组了
//                while (deviceGroupIterator.hasNext()) {
//                    DeviceGroup deviceGroup2 = deviceGroupIterator.next();
//                    if (deviceGroup2.getIntGroupId() == deviceGroup.getIntGroupId()) {
//                        flag = true;
//                        break;
//                    }
//                }
//                if(!flag) {
//                    groupList.add(deviceGroup);
//                }
//                flag = false; //复位
//            } else {
//                groupList.add(deviceGroup);
//            }
//        }
//        return groupList;
//    }

    @Transactional(readOnly = true)
    public List<JSONObject> getAllGroupName(String userName) {
        List<JSONObject> jsonObjects = deviceGroupMapper.selecGroupIdAndGroupNameByUserName(userName);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("int_group_id",0);
        jsonObject.put("str_group_name","默认分组");
        jsonObjects.add(jsonObject);
        return jsonObjects;
    }

    public int addDevice(int groupId, List<JSONObject> deviceList, String userName) {
        int counter = 0;
        if(deviceGroupMapper.selectUserNameByGroupId(groupId) != null) {
            Iterator<JSONObject> jsonObjectIterator = deviceList.iterator();
            while (jsonObjectIterator.hasNext()) {
                int deviceId = jsonObjectIterator.next().getInteger("deviceID");
                //验证用户是否有该分组
                if (deviceGroupMapper.selectUserNameByGroupId(groupId).equals(userName)) {
                    if (deviceMapper.updateGroupIdByDeviceIdAndUserName(groupId, deviceId, userName) != 0) {
                        counter++;
                    } else if (deviceShareMapper.updateGroupIdByDeviceIdAndUserName(groupId, deviceId, userName) != 0) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    /**
     * 开启事物机制 避免在设备表和分享表以及分组表删除更改相应的信息只成功一部分
     * @param groupId
     * @param userName
     * @return
     */

    // 删除自己拥有的设备
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteGroupOwner(int groupId, String userName) {
        int counter = 0;
        List<Integer> deviceIdList = deviceMapper.selectDeviceIdByGroupIdAndUserName(groupId,userName);
        Iterator<Integer> deviceIdListIr = deviceIdList.iterator();
        while (deviceIdListIr.hasNext()) {
            int deviceId = deviceIdListIr.next();
            counter += deviceMapper.updateGroupIdByDeviceId(0,deviceId); //恢复为默认分组
        }
        return counter;
    }

    // 删除别人分享的设备
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteGroupShared(int groupId, String userName) {
        int counter = 0;
        List<Integer> deviceSharedIdList = deviceShareMapper.selectDeviceIdByGroupIdAndUserName(groupId,userName);
        Iterator<Integer> deviceIdListIr = deviceSharedIdList.iterator();
        while (deviceIdListIr.hasNext()) {
            int deviceId = deviceIdListIr.next();
            deviceShareMapper.updateGroupIdByDeviceId(0,deviceId);
        }
        return counter;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteGroup(int groupId, String userName) {
        // 自己拥有的设备
        deleteGroupOwner(groupId,userName);
        // 别人分享的设备
        deleteGroupShared(groupId,userName);
        return deviceGroupMapper.deleteByGroupIdAndUserName(groupId,userName);
    }

    // 重命名分组
    public int groupRename(int groupId, String newGroupName, String userName) {
        return deviceGroupMapper.updateGroupNameByGroupIdAndUserName(newGroupName,groupId,userName);
    }

    // 移动设备
    public int moveDevice(int deviceId, int groupId, String userName) {
        if(groupId == 0) {
            if(deviceMapper.updateGroupIdByDeviceIdAndUserName(groupId,deviceId,userName) == 0) {
                return deviceShareMapper.updateGroupIdByDeviceIdAndUserName(groupId,deviceId,userName);
            }
            return 1;
        } else {
            if(deviceGroupMapper.selectUserNameByGroupId(groupId) != null && deviceGroupMapper.selectUserNameByGroupId(groupId).equals(userName)) {
                if(deviceMapper.updateGroupIdByDeviceIdAndUserName(groupId,deviceId,userName) == 0) {
                    return deviceShareMapper.updateGroupIdByDeviceIdAndUserName(groupId,deviceId,userName);
                }
                return 1;
            }
            return 0;
        }
    }

    @Transactional(readOnly = true)
    public List<JSONObject> getAllDevice(String userName) {
        List<JSONObject> groupData = new ArrayList<>();
        List<JSONObject> deviceGroupList = getAllGroupName(userName);
        Iterator<JSONObject> deviceGroupIterator = deviceGroupList.iterator();
        while (deviceGroupIterator.hasNext()) {
            Boolean flag = false;
            JSONObject deviceGroup = deviceGroupIterator.next();
            int groupId = deviceGroup.getInteger("int_group_id");
            String groupName = deviceGroup.getString("str_group_name");

            // 检查冗余
            Iterator<JSONObject> groupDataIr = groupData.iterator();
            while (groupDataIr.hasNext()) {
                JSONObject group = groupDataIr.next();
                if (group != null && group.getInteger("groupID") == groupId) {
                    flag = true;
                    break;
                }
            }
            if(flag) {
                continue;
            }
            JSONObject json = new JSONObject();
            json.put("groupID",groupId);
            json.put("groupName",groupName);
            List<Object> details = new ArrayList<>();
            List<Integer> deviceIdList = deviceMapper.selectDeviceIdByGroupIdAndUserName(groupId,userName);
            List<Integer> deviceIdList2 = deviceShareMapper.selectDeviceIdByGroupIdAndUserName(groupId,userName);
            Iterator<Integer> integerIterator1 = deviceIdList.iterator();
            Iterator<Integer> integerIterator2 = deviceIdList2.iterator();
            // 自己拥有的设备
            while (integerIterator1.hasNext()) {
                int deviceId = integerIterator1.next();
                int detailId = deviceMapper.selectDetailIdByDeviceId(deviceId);
                String deviceModel = deviceDetailMapper.selectDeviceModelById(detailId);
                JSONObject jsonObject = deviceMapper.selectByDeviceId(deviceId);
                jsonObject.put("strDeviceModel",deviceModel);
                details.add(JsonUtils.convert(jsonObject.toString()));
            }
            // 别人分享的设备
            while (integerIterator2.hasNext()) {
                int deviceId = integerIterator2.next();
                int detailId = deviceMapper.selectDetailIdByDeviceId(deviceId);
                String deviceModel = deviceDetailMapper.selectDeviceModelById(detailId);
                JSONObject jsonObject = deviceMapper.selectByDeviceId(deviceId);
                jsonObject.put("strDeviceModel",deviceModel);
                details.add(JsonUtils.convert(jsonObject.toString()));
            }
            json.put("detail",details);
            groupData.add(json);
        }
        return groupData;
    }

    public int addGroup(String userName, String newGroupName) {
        DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setStrUserName(userName);
        deviceGroup.setStrGroupName(newGroupName);
        return deviceGroupMapper.insert(deviceGroup);
    }

}
