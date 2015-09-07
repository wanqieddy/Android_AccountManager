package com.phicomm.account.util;

import android.graphics.Bitmap;

public class Result {
    private String flag;
    private String statusCode;
    private String resultMsg;
    private String keyuserKey;
    private String userId;
    private String userName;
    private String userTrueName;
    private String userSex;
    private String userBirth;
    private String userAdress;
    private String userEmail;
    private String userCreateTime;
    private String nickName;
    private String phone;
    private String image;
    private String age;
    private String passWord;
    private String jssessionid;
    private String syncTime;

    // user true name
    public void setUserAge(String age) {
        this.age = age;
    }

    public String getUserAge() {
        return age;
    }

    // user true name
    public void setUserTrueName(String userTrueName) {
        this.userTrueName = userTrueName;
    }

    public String getUserTrueName() {
        return userTrueName;
    }

    // createtime
    public String getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(String userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    // user address
    public String getUserAddress() {
        return userAdress;
    }

    public void setUserAddress(String userAdress) {
        this.userAdress = userAdress;
    }

    // user phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String string) {
        this.phone = string;
    }

    // user sex
    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    // user email
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // user nickname
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    // user birth
    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    // flag
    public String getFalg() {
        return flag;
    }

    public void setFalg(String falg) {
        this.flag = falg;
    }

    // status code
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    // user msg
    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    // user key
    public String getKeyuserKey() {
        return keyuserKey;
    }

    public void setKeyuserKey(String keyuserKey) {
        this.keyuserKey = keyuserKey;
    }

    // user id
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    // user image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // user name
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String passWord) {
        // TODO Auto-generated method stub
        this.passWord = passWord;
    }

    public String getPassword() {
        // TODO Auto-generated method stub
        return passWord;
    }

    public String getJssessionid() {
        return jssessionid;
    }

    public void setJssessionid(String jssessionid) {
        this.jssessionid = jssessionid;
    }
    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "keyuserKey=" + keyuserKey + "____statuscode=" + statusCode
                + "____________resultMsg=" + resultMsg;
    }

}
