package com.udelvr.DriverMode.Shipment;

import com.udelvr.RESTClient.Shipment.ShipmentDO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prasadshirsath on 4/17/15.
 */
public class DriverPackageManager
{

    private static DriverPackageManager mInstance;
    private List<ShipmentDO> driverPackages;

    public static DriverPackageManager getInstance() {
        if (mInstance == null) {
            mInstance = new DriverPackageManager();
        }
        return mInstance;
    }

    public List<ShipmentDO> getDriverPackages() {
        if(driverPackages ==null)
            driverPackages =new ArrayList<ShipmentDO>();
        return driverPackages;
    }
    public void add(ShipmentDO apackage)
    {
        driverPackages.add(apackage);

    }
    public void addAll(List<ShipmentDO> shipmentResponse) {

        driverPackages.clear();
        for(ShipmentDO shipment:shipmentResponse) {
            if(!driverPackages.contains(shipment))
                driverPackages.addAll(shipmentResponse);
        }
    }

}
