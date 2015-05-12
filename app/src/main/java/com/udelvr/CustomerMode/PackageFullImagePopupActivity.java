package com.udelvr.CustomerMode;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udelvr.ApplicationContextProvider;
import com.udelvr.AuthStore;
import com.udelvr.R;
import com.udelvr.RESTClient.RestClient;

/**
 * Created by sophiango on 4/14/15.
 */
public class PackageFullImagePopupActivity extends Activity {


    Context mContext;
    ImageView packageImage;
    PackageFullImagePopupActivity applyToBeDriver;
    private ImageButton close;
    private AuthStore auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = this;
        applyToBeDriver = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_image_popup);

        packageImage = (ImageView)findViewById(R.id.imageView_package);

        auth = new AuthStore(ApplicationContextProvider.getContext());

        close = (ImageButton) findViewById(R.id.popoup_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String imageURL = bundle.getString("ShipmentImageURL");

        Picasso.with(this).setIndicatorsEnabled(true);
        String url= RestClient.getRoot()+imageURL;


        Picasso.with(this).load(url).fit().centerInside().into(packageImage, new Callback() {
            @Override
            public void onSuccess() {
                System.out.println("onSuccess");
            }

            @Override
            public void onError() {
                System.out.println("onSuccessFail");
            }
        });
    }
}
