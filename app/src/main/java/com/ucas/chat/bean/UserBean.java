package com.ucas.chat.bean;

import com.ucas.chat.R;

import java.io.Serializable;

/**
 * 用户
 */
public class UserBean implements Serializable {

    public static final int[] imHead = {R.mipmap.g1, R.mipmap.b1,R.mipmap.g2, R.mipmap.b2,R.mipmap.g3, R.mipmap.b3, };
    private String userName;//用户名
    private String password;//密码
    private int imPhoto;//头像


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getImPhoto() {
        return imHead[imPhoto];
    }

    public void setImPhoto(int imPhoto) {
        this.imPhoto = imPhoto;
    }
}
