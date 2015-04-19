package com.udelvr.DriverMode.Shipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prasadshirsath on 4/17/15.
 */
public class DriverPackageManager
{

    private static DriverPackageManager mInstance;
    private List<DriverPackage> driverPackages;

    public static DriverPackageManager getInstance() {
        if (mInstance == null) {
            mInstance = new DriverPackageManager();
        }
        return mInstance;
    }

    public List<DriverPackage> getDriverPackages() {
        if(driverPackages ==null)
            driverPackages =new ArrayList<DriverPackage>();
        return driverPackages;
    }
    public void add(DriverPackage apackage)
    {
        driverPackages.add(apackage);

    }
}
