package com.oliveoa.dao;

import com.oliveoa.pojo.UserInfo;

import java.util.ArrayList;

public interface UserDAO {

    /**
     * 更新用户密码
     */
    public void updatePassword(String username, String password);

    /**
     * 更新用户信息
     */
    public void updateUser(UserInfo user);


    /**
     * 查询用户信息
     */
    public UserInfo getUser();


}
