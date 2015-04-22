package com.udelvr.RESTClient.Driver;

import java.io.File;

/**
 * Created by prasadshirsath on 4/20/15.
 */
public class DriverDetails {

    File licencePhoto;
    String driverLicenseNo;
    String licenseExpiry;

    public DriverDetails() {
    }

    public DriverDetails(File licencePhoto, String driverLicenseNo, String licenseExpiry) {
        this.licencePhoto = licencePhoto;
        this.driverLicenseNo = driverLicenseNo;
        this.licenseExpiry = licenseExpiry;
    }

    public File getLicencePhoto() {
        return licencePhoto;
    }

    public void setLicencePhoto(File licencePhoto) {
        this.licencePhoto = licencePhoto;
    }

    public String getdriverLicenseNo() {
        return driverLicenseNo;
    }

    public void setdriverLicenseNo(String driverLicenseNo) {
        this.driverLicenseNo = driverLicenseNo;
    }

    public String getlicenseExpiry() {
        return licenseExpiry;
    }

    public void setlicenseExpiry(String licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }



}
