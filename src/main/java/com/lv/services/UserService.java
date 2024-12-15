package com.lv.services;

import com.lv.pojo.User;
import com.lv.pojo.dto.UserDto;

public interface UserService {

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 用户注册
     * @param username
     * @param password
     */
    void register(String username, String password);

    /**
     * 根据用户id查找用户
     * @param id
     * @return
     */
    User findByUserId(int id);

    /**
     * 修改用户信息
     * @param userDto
     * @return
     */
    int updateUserInfo(UserDto userDto);
}
