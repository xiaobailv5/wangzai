package com.lv.services.impl;

import com.lv.mapper.UserMapper;
import com.lv.pojo.User;
import com.lv.pojo.dto.UserDto;
import com.lv.services.UserService;
import com.lv.utils.Md5Util;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: big-event
 * @ClassName Us
 * @description:
 * @author: gxjh
 * @create: 2024-12-14 14:34
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        User user = userMapper.findByUsername(username);
        return user;
    }

    /**
     * 注册
     * @param username
     * @param password
     */
    @Override
    public void register(String username, String password) {

        //加密
        String md5String = Md5Util.getMD5String(password);

        userMapper.register(username,md5String);
    }

    /**
     * 根据用户id查找用户
     * @param id
     * @return
     */
    @Override
    public User findByUserId(int id) {
        User user = userMapper.findByUserId(id);
        return user;
    }

    @Override
    public int updateUserInfo(UserDto userDto) {

        int i = userMapper.updateUserInfo(userDto);
        return i;
    }

    @Override
    public void updatePwd(Integer id, String newPwd) {
        newPwd = Md5Util.getMD5String(newPwd);
        userMapper.updatePwd(id, newPwd);
    }
}