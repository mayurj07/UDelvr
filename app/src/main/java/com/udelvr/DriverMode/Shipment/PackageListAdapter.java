package com.udelvr.DriverMode.Shipment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udelvr.ApplicationContextProvider;
import com.udelvr.DriverMode.ShipmentDetailsActivity;
import com.udelvr.R;
import com.udelvr.RESTClient.Shipment.ShipmentDO;

import java.util.List;

/**
 * Created by Trey Robinson on 8/3/14.
 * Copyright 2014 MindMine LLC.
 */
public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder> {

    private static List<ShipmentDO> driverPackages;
    private int rowLayout;
    private Context mContext;
    private String TAG = "recycle ";
    //final ViewHolder viewHolder = null;

    public PackageListAdapter(List<ShipmentDO> driverPackages, int rowLayout, Context context) {
        this.driverPackages = driverPackages;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }


    @Override
    public PackageListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        PackageListAdapter.ViewHolder vh = new ViewHolder(v, new PackageListAdapter.ViewHolder.IMyViewHolderClicks(){
            @Override
            public void onItem(View caller) {
                ShipmentDO aDriverPackage = driverPackages.get(i);

                Toast.makeText(ApplicationContextProvider.getContext(), "", Toast.LENGTH_LONG).show();
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final ShipmentDO aDriverPackage = driverPackages.get(i);
        viewHolder.destination.setText(aDriverPackage.getDestinationAddress() );
        viewHolder.amount.setText("$"+aDriverPackage.getCustomerID());

        viewHolder.dateTime.setText(aDriverPackage.getPickupDate());
//        viewHolder.setClickListener(new ViewHolder.ClickListener(){
//            @Override
//            public void onClick(View v, int position, boolean isLongClick) {
//                if (isLongClick){
//                    Log.i(TAG, "Perform long click " + v + " at " + position);
//                    //Log.i(TAG, "Click detail" + viewHolder.destination.getText());
//                }
//                else {
//                    Log.i(TAG, "Perform click " + v + " at " + position);
//                    //Log.i(TAG, "Click detail" + viewHolder.destination.getText());
//                }
//            }
//        });

        Picasso.with(mContext).setIndicatorsEnabled(true);
        // String url = "http://maps.google.com/maps/api/staticmap?center="+aDriverPackage.getDestinationLocationLatitude()+ "," +aDriverPackage.getDestinationLocationLongitude()+ "&markers="+aDriverPackage.getDestinationLocationLatitude()+ "," +aDriverPackage.getDestinationLocationLongitude()+"&zoom=15&size=500x300&sensor=false";
        // String url = "http://maps.googleapis.com/maps/api/staticmap?size=400x300&sensor=false&markers="+aDriverPackage.getDestinationLocationLatitude()+ "," +aDriverPackage.getDestinationLocationLongitude();
        String url = "http://maps.googleapis.com/maps/api/staticmap?" + "&markers=" + aDriverPackage.getDestinationLocationLatitude() + "," + aDriverPackage.getDestinationLocationLongitude() + "&zoom=15&size=400x300&sensor=false";
        Picasso.with(mContext).load(url).fit().centerCrop().into(viewHolder.packageMap, new Callback() {
            @Override
            public void onSuccess() {
                System.out.println("onSuccess");
            }

            @Override
            public void onError() {
                System.out.println("onSuccessFail");
            }
        });
        viewHolder.packageMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ApplicationContextProvider.getContext(),""+aDriverPackage.getAmount() ,Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString("PackageDescription",aDriverPackage.getPackageDescription());
                bundle.putString("PackageWeight",aDriverPackage.getPackageWeight());
                bundle.putString("Amount",aDriverPackage.getAmount());
                bundle.putString("RecipientName",aDriverPackage.getRecipientName());

                bundle.putString("SourceAddress",aDriverPackage.getSourceAddress());
                bundle.putString("SourceLocationLatitude",aDriverPackage.getSourceLocationLatitude());
                bundle.putString("SourceLocationLongitude",aDriverPackage.getSourceLocationLongitude());

                bundle.putString("DestinationAddress",aDriverPackage.getDestinationAddress());
                bundle.putString("DestinationLocationLatitude",aDriverPackage.getDestinationLocationLatitude());
                bundle.putString("DestinationLocationLongitude",aDriverPackage.getDestinationLocationLongitude());

                bundle.putString("PickupTime",aDriverPackage.getPickupTime());
                bundle.putString("PickupDate",aDriverPackage.getPickupDate());

                bundle.putString("ShipmentImage",aDriverPackage.getShipmentImage());
                bundle.putString("ShipmentID",aDriverPackage.getShipmentID());



                Intent intent = new Intent(mContext,ShipmentDetailsActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);



            }
        });
      //  viewHolder.packageMap.setImageDrawable(new BitmapDrawable(mContext.getResources(), aDriverPackage.image));

    }

    @Override
    public int getItemCount() {
        return driverPackages == null ? 0 : driverPackages.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView destination;
        public TextView amount;
        public TextView dateTime;
        public ImageView packageMap;
        //public ClickListener listener;
        public IMyViewHolderClicks mListener;


        public ViewHolder(View itemView,IMyViewHolderClicks listener) {

            super(itemView);
            //listener = clickListener;
            destination = (TextView) itemView.findViewById(R.id.destinationText);
            packageMap = (ImageView)itemView.findViewById(R.id.packageDestinationStaticMap);
            amount=(TextView)itemView.findViewById(R.id.amountText);
            dateTime=(TextView)itemView.findViewById(R.id.dateTimeText);

            itemView.setOnClickListener(this);
            packageMap.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
                mListener.onItem(view);
        }

        public static interface IMyViewHolderClicks {
            public void onItem(View caller);
            //public void onTomato(ImageView callerImage);
        }

//        public interface ClickListener {
//            public void onClick(View v, int position, boolean isLongClick);
//        }
//
//        public void setClickListener(ClickListener clickListener) {
//            this.listener = clickListener;
//        }
//
//        @Override
//        public void onClick(View v) {
//            listener.onClick(v, getPosition(), false);
//        }
//
//        @Override
//        public boolean onLongClick(View v) {
//            listener.onClick(v, getPosition(), true);
//            return true;
//        }

    }

    public void add(ShipmentDO apackage)
    {
        driverPackages.add(apackage);
    }

    public void addAll(List<ShipmentDO> shipmentResponse) {
        driverPackages.addAll(shipmentResponse);
    }

    public void notifyDataSetChanges()
    {
        this.notifyDataSetChanged();
    }
}
