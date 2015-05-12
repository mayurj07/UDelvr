package com.udelvr.RESTClient.Shipment;

import android.app.ProgressDialog;

import com.udelvr.CustomerMode.CustomerHomeFragment;
import com.udelvr.CustomerMode.Shipment.AddShipment;
import com.udelvr.CustomerMode.Shipment.CustomerPackageManager;
import com.udelvr.DriverMode.HomeFragment;
import com.udelvr.DriverMode.MyOrdersFragment;
import com.udelvr.DriverMode.Shipment.DriverPackageManager;
import com.udelvr.DriverMode.ShipmentDetailsActivity;
import com.udelvr.RESTClient.RestClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by sophiango on 4/19/15.
 */
public class ShipmentController {

   public static boolean addNewShipment(final AddShipment addShipment,String user_id,Shipment shipment) {

       final ProgressDialog mProgressDialog = new ProgressDialog(addShipment);
       mProgressDialog.setIndeterminate(true);
       mProgressDialog.setProgressNumberFormat(null);
       mProgressDialog.setProgressPercentFormat(null);
       mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
       mProgressDialog.show();


       MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
       multipartTypedOutput.addPart("recipientName", new TypedString(shipment.getRecipientName()));
       multipartTypedOutput.addPart("sourceAddress", new TypedString(shipment.getSourceAddress()));
       multipartTypedOutput.addPart("sourceLat", new TypedString(shipment.getSourceLat()));
       multipartTypedOutput.addPart("sourceLong", new TypedString(shipment.getSourceLong()));
       multipartTypedOutput.addPart("destinationAddress", new TypedString(shipment.getDestinationAddress()));
       multipartTypedOutput.addPart("destinationLat", new TypedString(shipment.getDestinationLat()));
       multipartTypedOutput.addPart("destinationLong", new TypedString(shipment.getDestinationLong()));
       multipartTypedOutput.addPart("packageDescription", new TypedString(shipment.getPackageDescription()));
       multipartTypedOutput.addPart("packageWeight", new TypedString(shipment.getPackageWeight()));
       multipartTypedOutput.addPart("pickupTime", new TypedString(shipment.getPickupTime()));
       multipartTypedOutput.addPart("pickupDate", new TypedString(shipment.getPickupDate()));
       multipartTypedOutput.addPart("shipmentPhoto", new TypedFile("shipmentPhoto", shipment.getShipmentImage()));

       RestClient.get().addNewShipment(user_id,multipartTypedOutput, new Callback<ShipmentResponse>() {
           @Override
           public void success(ShipmentResponse shipmentResponse, Response response) {
               if (mProgressDialog.isShowing())
                   mProgressDialog.dismiss();
               addShipment.addedShipmentSuccess();
           }
           @Override
           public void failure(RetrofitError error) {
               if (mProgressDialog.isShowing())
                   mProgressDialog.dismiss();
               addShipment.OnFailedResponse();

           }
       });
            return true;
    }
    public static void getUserShipments(String user_id,final CustomerHomeFragment homeFragment) {


        RestClient.get().getUserShipments(user_id,new Callback<List<ShipmentDO>>() {
            @Override
            public void success(List<ShipmentDO> shipmentResponse, Response response) {
                CustomerPackageManager.getInstance().addAll(shipmentResponse);
                homeFragment.onLoadComplete();
            }
            @Override
            public void failure(RetrofitError error) {
                homeFragment.onLoadComplete();
            }
        });
    }
    public static void getAllShipments(final HomeFragment homeFragment) {


        RestClient.get().getAllShipments( new Callback<List<ShipmentDO>>() {
            @Override
            public void success(List<ShipmentDO> shipmentResponse, Response response) {
                DriverPackageManager.getInstance().addAll(shipmentResponse);
                homeFragment.onLoadComplete();
            }
            @Override
            public void failure(RetrofitError error) {
                homeFragment.onLoadComplete();
            }
        });
    }
    public static void getDriverShipments(final MyOrdersFragment myOrdersFragment) {


        RestClient.get().getDriverShipments(new Callback<List<ShipmentDO>>() {
            @Override
            public void success(List<ShipmentDO> shipmentResponse, Response response) {
                DriverPackageManager.getInstance().addAll(shipmentResponse);
                myOrdersFragment.onLoadComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                myOrdersFragment.onLoadComplete();
            }
        });
    }
    public static void acceptShipmentforDelivery(String shipmentId,String driverId,final ShipmentDetailsActivity shipmentDetailsActivity) {

        final ProgressDialog mProgressDialog = new ProgressDialog(shipmentDetailsActivity);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
        RestClient.get().acceptShipmentforDelivery(shipmentId,driverId, new Callback<ShipmentResponse>() {
            @Override
            public void success(ShipmentResponse shipmentResponse, Response response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
               shipmentDetailsActivity.acceptShipmentSuccess();
            }
            @Override
            public void failure(RetrofitError error) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                shipmentDetailsActivity.OnFailedResponse();

            }
        });


    }

}
