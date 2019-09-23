package com.ups.demo.dao;

import com.ups.demo.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    @Select("select * from t_user where str_tel_number = #{telNumber}")
    User selectByTelNumber(String telNumber);

    @Select("select str_user_type from t_user where str_open_id = #{openID}")
    String selectByOpenId(String openId);

    @Select("select str_password from t_user where str_tel_number = #{telNumber}")
    String selectPassWordByUserName(String telNumber);

    @Update("update t_user set str_open_id = #{openId}")
    int updateOpenId(String openId);

    @Update("update t_user set str_password = #{newPassword} where str_tel_number = #{telNumber}")
    int updataByTelNumber(String newPassword, String telNumber);

    int deleteByPrimaryKey(Integer intUserId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer intUserId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}