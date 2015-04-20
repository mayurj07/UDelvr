package com.udelvr.RESTClient.Shipment;

import com.udelvr.ApplicationContextProvider;
import com.udelvr.AuthStore;
import com.udelvr.RESTClient.RestClient;

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

   public static boolean addNewShipment(Shipment shipment) {

       final Boolean[] success = new Boolean[1];

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
//       multipartTypedOutput.addPart("shipmentPhoto", new TypedFile("shipmentPhoto", shipment.getShipmentImage()));

       RestClient.get().addNewShipment(multipartTypedOutput, new Callback<ShipmentResponse>() {
           @Override
           public void success(ShipmentResponse shipmentResponse, Response response) {

           }

           @Override
           public void failure(RetrofitError error) {

           }
       });
            return true;
    }
}
