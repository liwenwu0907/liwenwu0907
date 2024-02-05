package com.example.demo.entity;

/**
 * @version 1.0, 2018/1/10.
 * @auto <a href="mailto:zhouwanqing@gtmap.cn">zhouwanqing</a>
 * @description 接入api的用户
 */
public class ApiUser {
    private String userid;
    private String userName;
    private String userPwd;
    private String aesKey;
    private String origin;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
