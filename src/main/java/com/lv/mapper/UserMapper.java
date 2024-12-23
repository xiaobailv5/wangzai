package com.lv.mapper;

import com.lv.pojo.User;
import com.lv.pojo.dto.UserDto;

public interface UserMapper {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 注册用户
     * @param username
     * @param password
     */
    void register(String username, String password);

    /**
     * 根据用户id查询用户
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

    void updatePwd(Integer id, String newPwd);
}
