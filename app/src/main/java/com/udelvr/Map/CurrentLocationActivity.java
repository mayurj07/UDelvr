package com.udelvr.Map;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;
import com.udelvr.R;

public class CurrentLocationActivity extends Activity implements OnMapReadyCallback{
    static double latitude = 37.335499;
    static double longitude = -121.883486;
    private GoogleMap googleMap;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
//        try {
//            // Loading map
//            initilizeMap();
//            drawMarker(latitude,longitude );
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng currentPosition = new LatLng(latitude, longitude);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 12));
        map.addMarker(new MarkerOptions()
                .position(currentPosition)
                .title("ME")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.udelvr))
                .snippet("Lat:" + latitude + "Lng:"+ longitude));
    }

//    private void initilizeMap() {
//        if (googleMap == null) {
//            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
//                    R.id.map)).getMap();
//
//            // check if map is created successfully or not
//            if (googleMap == null) {
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        initilizeMap();
//    }

//    private void drawMarker(double latitude, double longtitude){
//        googleMap.clear();
//        // create marker
//        //MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");
//        // Changing marker icon
////        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.shipment)));
//        LatLng currentPosition = new LatLng(latitude, longitude);
//        map.setMyLocationEnabled(true);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 13));
//        googleMap.addMarker(new MarkerOptions()
//                .position(currentPosition)
//                .title("ME")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.udelvr))
//                .snippet("Lat:" + latitude + "Lng:"+ longitude));
//    }


}