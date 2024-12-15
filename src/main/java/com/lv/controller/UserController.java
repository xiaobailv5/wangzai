package com.lv.controller;


import com.lv.pojo.Result;
import com.lv.pojo.User;
import com.lv.pojo.dto.UserDto;
import com.lv.services.UserService;
import com.lv.utils.JwtUtil;
import com.lv.utils.Md5Util;
import com.lv.utils.ThreadLocalUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: big-event
 * @ClassName UserController
 * @description: 用户控制层
 * @author: gxjh
 * @create: 2024-12-14 14:33
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$", message = "用户名格式不正确") String username,
                           @Pattern(regexp = "^\\S{5,16}$", message = "密码格式不正确") String password) {

        //查询用户是否存在
        User user = userService.findByUsername(username);
        if (user != null) {
            return Result.error("用户已存在");
        } else {
            //注册
            userService.register(username, password);
            return Result.success();
        }

    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$", message = "用户名格式不正确") String username,
                        @Pattern(regexp = "^\\S{5,16}$", message = "密码格式不正确") String password) {

        //查询用户是否存在
        User user = userService.findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        //密码是否正确
        boolean b = Md5Util.checkPassword(password, user.getPassword());

        if (!b) {
            return Result.error("密码错误");
        }
        //令牌生成
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        String token = JwtUtil.genToken(claims);
        return Result.success(token);
    }

    /**
     * 查询用户信息
     * @param token
     * @return
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader("Authorization") String token) {

        String username = ThreadLocalUtil.get();
        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    /**
     * 修改用户信息
     * @param userDto
     * @return
     */
    @PutMapping("/updateUserInfo")
    public Result updateUserInfo(@Valid @RequestBody UserDto userDto) {

        //校验用户是否存在
        User user = userService.findByUserId(userDto.getId());
        if (user == null) {
            return Result.error("用户不存在");
        }

        int num = userService.updateUserInfo(userDto);
        if (num > 0) {
            return Result.success();
        }
        return Result.error("用户信息修改shibai");
    }

    /**
     * 修改用户头像
     * @param avatarUrl
     * @param token
     * @return
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@NotNull(message = "头像不能为空") String avatarUrl, @RequestHeader("Authorization") String token) {

        String username = ThreadLocalUtil.get();
        User user = userService.findByUsername(username);

        if (user == null) {
            return Result.error("用户不存在");
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserPic(avatarUrl);
        int num = userService.updateUserInfo(userDto);
        if (num > 0) {
            return Result.success();
        }
        return Result.error("头像修改失败");
    }

}