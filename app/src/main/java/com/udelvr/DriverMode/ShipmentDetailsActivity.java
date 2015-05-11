package com.udelvr.DriverMode;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
