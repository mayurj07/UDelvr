package com.udelvr.DriverMode;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udelvr.AuthStore;
import com.udelvr.R;

// google place api

/**
 * Authored by sophiango and prasadshirsath
 */
public class ShipmentDetailsActivity extends Activity{
    private ImageView timePicker, datePicker, packageDestImageView, sourceAddressPick, destAddressPick;

    private TextView recipientsName, packageDesc, packageWeight, pickupTime, pickupDate,sourceAddress, destAddress ;

    private Button accept_shipment;
    private AuthStore authStore;

    private Bundle shipment_bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

         shipment_bundle = getIntent().getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_shipment_details);
        recipientsName=(TextView)findViewById(R.id.recipient);
        packageDesc=(TextView)findViewById(R.id.item_desc);
        packageWeight=(TextView)findViewById(R.id.item_weight);
        pickupTime=(TextView)findViewById(R.id.pickup_time);
        pickupDate=(TextView)findViewById(R.id.pickup_date);
        sourceAddress=(TextView)findViewById(R.id.pickup_address);
        destAddress=(TextView)findViewById(R.id.drop_address);


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





    }
    @Override
    protected void onResume() {
        super.onResume();

    }


}
