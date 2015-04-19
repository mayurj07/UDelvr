package com.udelvr.RESTClient.User;

import java.io.File;

/**
 * Created by prasadshirsath on 4/19/15.
 */
public class User
{
    private String userId;
    private String fullName;
    private String mobileNo;
    private String email;
    private String password;
    private String deviceID;
    private String createdAt;
    private File profilePhoto;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public File getprofilePhoto() {
        return profilePhoto;
    }

    public void setprofilePhoto(File profilePhoto) {

        this.profilePhoto = profilePhoto;
    }



}
