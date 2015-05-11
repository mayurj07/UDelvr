package com.udelvr.DriverMode;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udelvr.ApplicationContextProvider;
import com.udelvr.AuthStore;
import com.udelvr.R;
import com.udelvr.RESTClient.Shipment.ShipmentController;

// google place api

/**
 * Authored by sophiango and prasadshirsath
 */
public class ShipmentDetailsActivity extends Activity{
    private ImageView timePicker, datePicker, packageDestImageView, sourceAddressPick, destAddressPick;

    private TextView recipientsName, packageDesc, packageWeight,amount, pickupTime, pickupDate,sourceAddress, destAddress ;

    private Button accept_shipment;
    private AuthStore authStore;

    private Bundle shipment_bundle;

    private Context mContext;
    ShipmentDetailsActivity shipmentDetailsActivityl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authStore= new AuthStore(this);
        shipment_bundle = getIntent().getExtras();
        mContext=this;
        shipmentDetailsActivityl=this;
        setContentView(R.layout.driver_shipment_details);
        recipientsName=(TextView)findViewById(R.id.recipient);
        packageDesc=(TextView)findViewById(R.id.item_desc);
        packageWeight=(TextView)findViewById(R.id.item_weight);
        amount=(TextView)findViewById(R.id.item_price);
        pickupTime=(TextView)findViewById(R.id.pickup_time);
        pickupDate=(TextView)findViewById(R.id.pickup_date);
        sourceAddress=(TextView)findViewById(R.id.pickup_address);
        destAddress=(TextView)findViewById(R.id.drop_address);


        recipientsName.setText(shipment_bundle.getString("RecipientName"));
        packageDesc.setText(shipment_bundle.getString("PackageDescription"));
        packageWeight.setText(shipment_bundle.getString("PackageWeight"));
        amount.setText("$"+shipment_bundle.getString("Amount"));
        pickupTime.setText(shipment_bundle.getString("PickupTime"));
        pickupDate.setText(shipment_bundle.getString("PickupDate"));
        sourceAddress.setText(shipment_bundle.getString("SourceAddress"));
        destAddress.setText(shipment_bundle.getString("DestinationAddress"));




        packageDestImageView = (ImageView)this.findViewById(R.id.packageDestinationStaticMap);
        packageDestImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Picasso.with(this).setIndicatorsEnabled(true);
        String url = "http://maps.googleapis.com/maps/api/staticmap?" + "&markers=" + shipment_bundle.getString("DestinationLocationLatitude") + "," + shipment_bundle.getString("DestinationLocationLongitude") + "&zoom=15&size=400x300&sensor=false";
        Picasso.with(this).load(url).fit().centerCrop().into(packageDestImageView, new Callback() {
            @Override
            public void onSuccess() {
                System.out.println("onSuccess");
            }

            @Override
            public void onError() {
                System.out.println("onSuccessFail");
            }
        });


        timePicker = (ImageView)this.findViewById(R.id.imageView_timePicker);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        datePicker = (ImageView)this.findViewById(R.id.imageView_datePicker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        sourceAddressPick = (ImageView)this.findViewById(R.id.imageView_pickup_address);
        sourceAddressPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        destAddressPick = (ImageView)this.findViewById(R.id.imageView_dropoff_address);
        destAddressPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        accept_shipment=(Button)findViewById(R.id.button_accept_shipment);

        accept_shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                ShipmentController.acceptShipmentforDelivery(shipment_bundle.getString("ShipmentID"),authStore.getDriverLicenceNo(),shipmentDetailsActivityl);

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Do you want to accept this order?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });




    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    public void acceptShipmentSuccess()
    {
        Toast.makeText(ApplicationContextProvider.getContext(), "Order booked!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void OnFailedResponse() {
        Toast.makeText(ApplicationContextProvider.getContext(), "Problem oocured while accepting shipment. Try again!", Toast.LENGTH_LONG).show();
    }

}
