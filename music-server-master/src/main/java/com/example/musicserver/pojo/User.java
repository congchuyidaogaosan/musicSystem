package com.example.musicserver.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("User用户")
public class  User {
    @ApiModelProperty(value = "用户表主键",name = "userId",hidden = true)
    private Integer userId;
    @ApiModelProperty(value = "用户昵称",name = "userName")
    private String userName;
    @ApiModelProperty(value = "用户账号",name = "account")
    private String account;
    @ApiModelProperty(value = "用户密码",name = "password")
    private String password;
    @ApiModelProperty(value = "用户电话",name = "userPhone")
    private String userPhone;
    @ApiModelProperty(value = "用户地址",name = "userArea")
    private String userArea;
    @ApiModelProperty(value = "用户简介",name = "userDetails")
    private String userDetails;
    @ApiModelProperty(value = "用户性别",name = "userGender",example = "男传1，女传0")
    private Integer userGender;
    @ApiModelProperty(value = "用户生日",name = "userBirth")
    private Date userBirth;
    @ApiModelProperty(value = "用户头像",name = "userPhoto")
    private String userPhoto;
    @ApiModelProperty(value = "用户收藏的歌单",name = "list",hidden = true)
    private List<Lists> list;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public Integer getUserGender() {
        return userGender;
    }

    public void setUserGender(Integer userGender) {
        this.userGender = userGender;
    }

    public List<Lists> getList() {
        return list;
    }

    public void setList(List<Lists> list) {
        this.list = list;
    }

    public Date getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(Date userBirth) {
        this.userBirth = userBirth;
    }

    public String getUserArea() {
        return userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userArea='" + userArea + '\'' +
                ", userDetails='" + userDetails + '\'' +
                ", userGender=" + userGender +
                ", userBirth='" + userBirth + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                '}';
    }
}
