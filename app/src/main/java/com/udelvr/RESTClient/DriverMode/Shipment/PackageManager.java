package com.udelvr.RESTClient.DriverMode.Shipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prasadshirsath on 4/17/15.
 */
public class PackageManager
{

    private static PackageManager mInstance;
    private List<Package> packages;

    public static PackageManager getInstance() {
        if (mInstance == null) {
            mInstance = new PackageManager();
        }
        return mInstance;
    }

    public List<Package> getPackages() {
        if(packages==null)
            packages=new ArrayList<Package>();
        return  packages;
    }
    public void add(Package apackage)
    {
        packages.add(apackage);

    }
}
