package com.udelvr.RESTClient.User;

/**
 * Created by prasadshirsath on 4/19/15.
 */
public class UserResponse {
    private String userId;
    private String fullName;
    private String mobileNo;
    private String email;
    private String password;
    private String deviceId;
    private String driverLicenseNo;
    private String licenseExpiry;
    private Integer driverRating;
    private String profileURL;
    private String licensePhotoURL;

    public UserResponse() {
    }

    public UserResponse(String userId, String fullName, String mobileNo, String email, String password, String deviceId, String driverLicenseNo, String licenseExpiry, Integer driverRating, String profileURL) {
        this.userId = userId;
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.email = email;
        this.password = password;
        this.deviceId = deviceId;
        this.driverLicenseNo = driverLicenseNo;
        this.licenseExpiry = licenseExpiry;
        this.driverRating = driverRating;
        this.profileURL = profileURL;
    }

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDriverLicenseNo() {
        return driverLicenseNo;
    }

    public void setDriverLicenseNo(String driverLicenseNo) {
        this.driverLicenseNo = driverLicenseNo;
    }

    public String getLicenseExpiry() {
        return licenseExpiry;
    }

    public void setLicenseExpiry(String licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }

    public Integer getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(Integer driverRating) {
        this.driverRating = driverRating;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getLicensePhotoURL() {
        return licensePhotoURL;
    }

    public void setLicensePhotoURL(String licensePhotoURL) {
        this.licensePhotoURL = licensePhotoURL;
    }





}
