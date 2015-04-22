package com.udelvr.CustomerMode.Shipment;

import com.udelvr.RESTClient.Shipment.ShipmentDO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prasadshirsath on 4/17/15.
 */
public class CustomerPackageManager
{

    private static CustomerPackageManager mInstance;
    private static List<ShipmentDO> driverPackages;

    public static CustomerPackageManager getInstance() {
        if (mInstance == null) {
            mInstance = new CustomerPackageManager();
        }
        return mInstance;
    }

    public List<ShipmentDO> getCustomerPackages() {
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
