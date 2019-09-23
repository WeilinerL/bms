package com.ups.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ups.demo.service.TokenService;
import com.ups.demo.utils.WXAppletUserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class LoginController {

    private final static Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    private TokenService tokenService;

    /**
     * PC 登录模块
     * @param userInfo 前端传递的用户信息
     * @param userAgent 登录客户端
     * @return {code: int,data: Object}
     */

    @PostMapping(value = "login/pc")
    public ResponseEntity<Map<String, Object>> loginPC(@RequestBody Map<String, String> userInfo,
                                                     @RequestHeader(value="User-Agent") String userAgent){
        String userName = userInfo.get("userName");
        String password = userInfo.get("password");
        String userType = userInfo.get("userType");

        HashMap<String, Object> result = new HashMap<>();
        String token = tokenService.loginCheck(userName, password,userAgent, userType);
        if(token == null) {
            if(log.isTraceEnabled()) {
                log.trace("PC端登录 token令牌为空");
            }
            result.put("code", 0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else {
            if(log.isTraceEnabled()) {
                log.trace("PC端登录 用户名为"+ tokenService.getUserFromToken(token).getUsername() + "的用户成功登录");
            }
            JSONObject data = new JSONObject();
            data.put("userName",tokenService.getUserFromToken(token).getUsername());
            data.put("userType",userType);
            data.put("token",token);
            result.put("code",1);
            result.put("data",data);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 微信小程序端登录
     * @param JSONCONTENT json 字符串 包含{code: String} //小程序wx.login获取的
     * @return {code: int, data: {userType: String}}
     */

    @PostMapping(value = "login/weixin")
    public ResponseEntity<Map<String, Object>> loginWeiXin(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if(JSONCONTENT.contains("code")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            String code = json.getString("code");
            if(code != null) {
                if(log.isTraceEnabled()) {
                    log.trace("微信小程序端登录 登录码: " + code);
                }
                JSONObject wxResult = WXAppletUserInfo.getSessionKeyAndOpenId(code);
                JSONObject data = new JSONObject();
                data.put("userType",tokenService.getUserTypeByOpenId(wxResult.getString("openId")));
                result.put("code",1);
                result.put("data",data);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }else {
                if(log.isTraceEnabled()) {
                    log.trace("微信小程序端登录 登录码为空");
                }
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        }else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    /**
     * 小程序绑定账户
     * @param JSONCONTENT json 字符串 包含 {user_id: string, //用户账号，手机号，邮箱 password: string, //md5加密 code: string} //小程序wx.login获取的
     * @return {code: int, data: {userType: String}}
     */
    @PostMapping(value = "auth/bind")
    public ResponseEntity<Map<String, Object>> wxBind(@RequestBody String JSONCONTENT) {
        HashMap<String, Object> result = new HashMap<>();
        if(JSONCONTENT.contains("user_id") && JSONCONTENT.contains("password") && JSONCONTENT.contains("code")) {
            JSONObject json = JSON.parseObject(JSONCONTENT);
            String code = json.getString("code");
            String userName = json.getString("user_id");
            String passWord = json.getString("password");
            if(code != null && userName != null && passWord != null) {
                if(log.isTraceEnabled()) {
                    log.trace("微信小程序端登录 用户名: " + userName + " code: " + code);
                }
                JSONObject wxResult = WXAppletUserInfo.getSessionKeyAndOpenId(code);
                String userType = tokenService.wxBind(userName,passWord,wxResult.getString("openId"));
                if(userType != null) {
                    JSONObject data = new JSONObject();
                    data.put("userType",userType);
                    result.put("code",1);
                    result.put("data",data);
                    return ResponseEntity.status(HttpStatus.OK).body(result);
                }else {
                    if(log.isTraceEnabled()) {
                        log.trace("微信小程序端登录 密码错误");
                    }
                    result.put("code",0);
                    return ResponseEntity.status(HttpStatus.OK).body(result);
                }
            }else {
                if(log.isTraceEnabled()) {
                    log.trace("微信小程序端登录 有参数为空");
                }
                result.put("code",0);
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }

        }else {
            result.put("code",0);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @DeleteMapping
    public Map<String, Object> logout(HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        String token = null;
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            if(log.isTraceEnabled() && tokenService.getUserFromToken(token) != null) {
                log.trace("will delete token : " + token);
                log.trace("注销用户: " + tokenService.getUserFromToken(token).getUsername());
                result.put("注销用户",tokenService.getUserFromToken(token).getUsername());
                tokenService.logout(token);
                return result;
            } else {
                result.put("status","fail");
                result.put("注销失败","此用户登录信息已失效!");
                return result;
            }
        } else{
            result.put("status","fail");
            result.put("注销失败","无此用户登录信息!");
            return result;
        }
    }

    @PostMapping(value = "/token_check")
    public Map<String, String> tokenCheck(@RequestBody Map<String, String> userInfo) {
        Map<String,String> result = new HashMap<>();

        String userName = userInfo.get("userName");
        String token = userInfo.get("token");

        if(userName != null && token != null) {
            if(log.isTraceEnabled()) {
                log.trace("检查用户的token: " + userInfo);
            }
            if(tokenService.tokenCheck(userName,token)) {
                result.put("result","success");
                return result;
            }
            result.put("result", "fail");
            return result;
        }
        result.put("result", "fail");
        return result;
    }

    @PostMapping(value = "test")
    public Map<Object,Object> testApi(@RequestBody String JSONCONTENT) {
        Map<Object,Object> result = new HashMap<>();
        JSONObject json = JSON.parseObject(JSONCONTENT);
        if(json != null) {
            if(log.isTraceEnabled()) {
                log.trace("接口测试 列表大小: " + json.get("deviceList"));
            }
            List<Object> list= (List<Object>) json.get("deviceList");
            Iterator<Object> objectIterator = list.iterator();
            while(objectIterator.hasNext()) {
                System.out.println(objectIterator.next());
            }
            result.put("code",1);
        }else {
            result.put("code",0);
        }

        return result;
    }


}
