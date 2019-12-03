package com.ups.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ups.demo.pojo.User;
import com.ups.demo.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/user")
public class UserContoller {

    @Autowired
    private UserService userService;

    private final static Log log = LogFactory.getLog(UserContoller.class);

    @GetMapping(value = "/get_user_info/{telNumber}")
    public User getUserInfo(@PathVariable String telNumber) {
        if(telNumber != null) {
            if(log.isTraceEnabled()) {
                log.trace("查询电话号码为: " + telNumber + "的用户的个人详细信息" );
            }
            return userService.getUserInfo(telNumber);
        }
        return null;
    }

    @PostMapping(value = "/user_info_modify")
    public Map<String,String> getUserInfo(@RequestBody User user) {
        Map<String,String> result = new HashMap<>();
        if(user != null) {
            if(log.isTraceEnabled()) {
                log.trace("修改电话号码为" + user.getStrTelNumber() + "的个人信息");
            }
            if(userService.userInfoModify(user) != 0) {
                result.put("result","success");
                return result;
            }
            result.put("result","fail");
            return result;
        }
        result.put("result","fail");
        return result;
    }

    @GetMapping(value = "/user_password_modify")
    public Map<String,String> getUserInfo(@RequestParam(value = "telNumber") String telNumber, @RequestParam(value = "newPassword") String newPassword) {
        Map<String,String> result = new HashMap<>();
        if(telNumber != null && newPassword != null) {
            if(log.isTraceEnabled()) {
                log.trace("修改电话号码为" + telNumber + "的密码");
            }
            if(userService.userPasswordModify(newPassword,telNumber) != 0) {
                result.put("result","success");
                return result;
            }
            result.put("result","fail");
            return result;
        }
        result.put("result","fail");
        return result;
    }

    /**
     * 获取用户的基本信息
     * @param userName 用户名
     * @return {code: int, data: User}用户的个人信息
     */

    @GetMapping(value = "get_user_info")
    public ResponseEntity<Map<String, Object>> shareDevice(@RequestParam String userName) {
        HashMap<String, Object> result = new HashMap<>();
        if (userName != null) {
            if(log.isTraceEnabled()) {
                log.trace("获取用户名为: " + userName + " 的个人信息");
            }
            User user = userService.getUserInfo(userName);
            if(user != null) {
                if (log.isTraceEnabled()) {log.trace("获取用户名为: " + userName + " 的个人信息成功");}
                result.put("code",1);
                result.put("data",user);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else {
                if (log.isTraceEnabled()) {log.trace("获取用户名为: " + userName + " 的个人信息失败!");}
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 更改用户个人信息
     * @param JSONCONTENT {
     *     "originalUserName": String,
     *     "newUserName": String,
     *     "password": String,
     *     "nickName": String,
     *     "profession": String,
     *     "realName": String,
     *     "address": String,
     *     "email": String
     * }
     * @return {code: int}
     */

    @PutMapping(value = "user_info_modify")
    public ResponseEntity<Map<String, Object>> userInfoModify(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if (JSONCONTENT.contains("originalUserName")
                && JSONCONTENT.contains("newUserName")
                && JSONCONTENT.contains("password")
                && JSONCONTENT.contains("nickName")
                && JSONCONTENT.contains("profession")
                && JSONCONTENT.contains("realName")
                && JSONCONTENT.contains("address")
                && JSONCONTENT.contains("email")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            String originalUserName = json.getString("originalUserName");
            String newUserName = json.getString("newUserName");
            String password = json.getString("password");
            String nickName = json.getString("nickName");
            String profession = json.getString("profession");
            String realName = json.getString("realName");
            String address = json.getString("address");
            String email = json.getString("email");
            if(log.isTraceEnabled()) {
                log.trace("更改个人信息 原用户名: " + originalUserName + " 新用户名: " + newUserName);
            }
            if(userService.modifyUserTable(originalUserName,newUserName,password,nickName,profession,realName,address,email) != 0) {
                if(log.isTraceEnabled()) {
                    log.trace("修改个人信息成功!");
                }
                result.put("code",1);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            } else {
                if(log.isTraceEnabled()) {
                    log.trace("修改个人信息失败!");
                }
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

    }

}
