package cn.crecode.bms.service;

import cn.crecode.bms.dao.*;
import cn.crecode.bms.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceShareMapper deviceShareMapper;

    @Autowired
    private DeviceGroupMapper deviceGroupMapper;

    @Autowired
    private UserLogInfoMapper userLogInfoMapper;

    // 查询用户信息
    @Transactional(readOnly = true)
    public User getUserInfo(String telNumber) {return userMapper.selectByTelNumber(telNumber);}

    public int userInfoModify(User user) {return userMapper.updateByPrimaryKey(user);}

    public int userPasswordModify(String newPassword, String telNumber) {return userMapper.updataByTelNumber(newPassword,telNumber);}

    // 修改用户信息
    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyUserTable(String originalUserName,
                               String newUserName,
                               String password,
                               String nickName,
                               String profession,
                               String realName,
                               String address,
                               String email) {
        if(newUserName.equals("")){
            return 0;
        } else if(newUserName.equals(originalUserName)) {
            User originalUser = userMapper.selectByTelNumber(originalUserName);
            if(originalUser != null) {
                originalUser.setStrPassword(password);
                originalUser.setStrNickname(nickName);
                originalUser.setStrProfession(profession);
                originalUser.setStrRealName(realName);
                originalUser.setStrAddress(address);
                originalUser.setStrEmail(email);
                return userMapper.updateByPrimaryKey(originalUser);
            }
            return 0;
        } else {
            User originalUser = userMapper.selectByTelNumber(originalUserName);
            if(originalUser != null) {
                int userId = originalUser.getIntUserId();
                User user = new User();
                user.setStrTelNumber(newUserName);
                user.setStrPassword(password);
                user.setStrNickname(nickName);
                user.setStrProfession(profession);
                user.setStrRealName(realName);
                user.setStrAddress(address);
                user.setStrEmail(email);
                user.setStrUserType(originalUser.getStrUserType());
                user.setStrOpenId(originalUser.getStrOpenId());
                // 由于外键约束 先在用户表增加一条记录在修改其他的表
                // 修改成功后在把旧的用户记录删除 然后把新增的的临时用户更新
                if(userMapper.insert(user) != 0) {
                    try {
                        modifyUserInfoOfDeviceTable(originalUserName,newUserName);
                        modifyUserInfoOfDeviceShareTable(originalUserName,newUserName);
                        modifyUserInfoOfDeviceGroupTable(originalUserName,newUserName);
                        modifyUserInfoOfUserLoginLogTable(originalUserName,newUserName);
                        userMapper.deleteByPrimaryKey(originalUser.getIntUserId()); // 删除原来的用户
                        userMapper.updateUserIdByTelNumber(userId,newUserName); // 将新的用户id换回以前的
                        return 1;
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return 0;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyUserInfoOfDeviceTable(String originalUserName, String newUserName) {
        List<Device> deviceList = deviceMapper.selectAllByUserTel(originalUserName);
        int count = 0;
        for(Device device : deviceList) {
            device.setStrUserTel(newUserName);
            count += deviceMapper.updateByPrimaryKey(device);
        }
        if(count == deviceList.size()) {
            return 1;
        }
        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyUserInfoOfDeviceShareTable(String originalUserName, String newUserName) {
        List<DeviceShare> shareList = deviceShareMapper.selectAllByUserTel(originalUserName);
        int count = 0;
        for(DeviceShare deviceShare : shareList) {
            deviceShare.setStrSharedUserTel(newUserName);
            count += deviceShareMapper.updateByPrimaryKey(deviceShare);
        }
        if(count == shareList.size()) {
            return 1;
        }
        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyUserInfoOfDeviceGroupTable(String originalUserName, String newUserName) {
        List<DeviceGroup> deviceGroupList = deviceGroupMapper.selectAllByUserName(originalUserName);
        int count = 0;
        for(DeviceGroup deviceGroup : deviceGroupList) {
            deviceGroup.setStrUserName(newUserName);
            count += deviceGroupMapper.updateByPrimaryKey(deviceGroup);
        }
        if(count == deviceGroupList.size()) {
            return 1;
        }
        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyUserInfoOfUserLoginLogTable(String originalUserName, String newUserName) {
        UserLogInfo userLogInfo = userLogInfoMapper.selectByPrimaryKey(originalUserName);
        if (userLogInfo != null) {
            userLogInfo.setStrUsername(newUserName);
            return userLogInfoMapper.updateByPrimaryKey(userLogInfo);
        }
        return 0;
    }

}
