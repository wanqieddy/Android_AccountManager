package com.phicomm.account.util;

public class User {
    private String userName;
    private String passWord;

    public User(String username, String pasString) {
        // TODO Auto-generated constructor stub
        this.userName = username;
        this.passWord = pasString;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassword(String passWord) {
        this.passWord = passWord;
    }

}
