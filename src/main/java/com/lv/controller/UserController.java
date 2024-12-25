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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


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

        //把token存储到redis中
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set(token,token,1, TimeUnit.HOURS);

        return Result.success(token);
    }

    /**
     * 查询用户信息
     * @param token
     * @return
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader("Authorization") String token) {

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
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
        return Result.error("用户信息修改失败");
    }

    /**
     * 修改用户头像
     * @param avatarUrl
     * @param token
     * @return
     */
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@NotNull(message = "头像不能为空") String avatarUrl, @RequestHeader("Authorization") String token) {

        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
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

    /**
     * 修改用户密码
     * @param params
     * @return com.lv.pojo.Result
     * @author gxjh2
     * @date 2024/12/23 16:14:23
    */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {

        String oldPwd = params.get("oldPwd");//原密码
        String newPwd = params.get("newPwd");//新密码
        String confirmPwd = params.get("rePwd");//确认新密码

        //校验必传参数
        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd) || StringUtils.isEmpty(confirmPwd)) {
            return Result.error("存在必传参数为空");
        }

        //校验老密码是否正确
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);
        boolean b = Md5Util.checkPassword(oldPwd, user.getPassword());
        if (!b) {
            return Result.error("原密码错误");
        }

        //校验新密码是否一致
        if (!newPwd.equals(confirmPwd)) {
            return Result.error("两次输入密码不一致");
        }

        userService.updatePwd(user.getId(), newPwd);
        //删除redis中对应的token
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);
        return Result.success();
    }
}