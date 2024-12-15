package com.lv.pojo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @program: wangzai
 * @ClassName UserDto
 * @description:
 * @author: gxjh
 * @create: 2024-12-14 21:01
 * @Version 1.0
 **/
public class UserDto {

    @NotNull(message = "ID不能为空")
    private Integer id;

    @NotNull(message = "昵称不能为空")
    @Size(min = 2, max = 20, message = "昵称长度必须在2到20个字符之间")
    private String nickname;

    @NotNull(message = "电子邮件不能为空")
    @Email(message = "电子邮件格式不正确")
    private String email;

    private String username;//用户名称

    private String userPic;//用户头像

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String geUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }
}