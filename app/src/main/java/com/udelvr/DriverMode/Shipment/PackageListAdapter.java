package com.udelvr.DriverMode.Shipment;

import android.content.Context;
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
import com.udelvr.R;
import com.udelvr.RESTClient.Shipment.ShipmentDO;

import java.util.List;

/**
 * Created by Trey Robinson on 8/3/14.
 * Copyright 2014 MindMine LLC.
 */
public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder>{

    private static List<ShipmentDO> driverPackages;
    private int rowLayout;
    private Context mContext;

    public PackageListAdapter(List<ShipmentDO> driverPackages, int rowLayout, Context context) {
        this.driverPackages = driverPackages;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }



    @Override
    public PackageListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        PackageListAdapter.ViewHolder vh = new ViewHolder(v, new PackageListAdapter.ViewHolder.IMyViewHolderClicks(){
            @Override
            public void onItem(View caller) {
                Toast.makeText(ApplicationContextProvider.getContext(),"",Toast.LENGTH_LONG).show();
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ShipmentDO aDriverPackage = driverPackages.get(i);
        viewHolder.destination.setText(aDriverPackage.getDestinationAddress() );
        viewHolder.amount.setText("$"+aDriverPackage.getCustomerID());
        viewHolder.dateTime.setText(aDriverPackage.getPickupDate());
        Picasso.with(mContext).setIndicatorsEnabled(true);
      // String url = "http://maps.google.com/maps/api/staticmap?center="+aDriverPackage.getDestinationLocationLatitude()+ "," +aDriverPackage.getDestinationLocationLongitude()+ "&markers="+aDriverPackage.getDestinationLocationLatitude()+ "," +aDriverPackage.getDestinationLocationLongitude()+"&zoom=15&size=500x300&sensor=false";
       // String url = "http://maps.googleapis.com/maps/api/staticmap?size=400x300&sensor=false&markers="+aDriverPackage.getDestinationLocationLatitude()+ "," +aDriverPackage.getDestinationLocationLongitude();
        String url = "http://maps.googleapis.com/maps/api/staticmap?"+"&markers="+aDriverPackage.getDestinationLocationLatitude()+ "," +aDriverPackage.getDestinationLocationLongitude()+"&zoom=15&size=400x300&sensor=false";
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
      //  viewHolder.packageMap.setImageDrawable(new BitmapDrawable(mContext.getResources(), aDriverPackage.image));
    }

    @Override
    public int getItemCount() {
        return driverPackages == null ? 0 : driverPackages.size();
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView destination;
        public TextView amount;
        public TextView dateTime;
        public ImageView packageMap;
        public IMyViewHolderClicks mListener;


        public ViewHolder(View itemView,IMyViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            destination = (TextView) itemView.findViewById(R.id.destinationText);
            packageMap = (ImageView)itemView.findViewById(R.id.packageDestinationStaticMap);
            amount=(TextView)itemView.findViewById(R.id.amountText);
            dateTime=(TextView)itemView.findViewById(R.id.dateTimeText);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
//            if (v instanceof Imageview){
//                mListener.onTomato((ImageView)v)
//            } else {
//                mListener.onPotato(v);
//            }
            mListener.onItem(v);
        }
        public static interface IMyViewHolderClicks {
            public void onItem(View caller);
            //public void onTomato(ImageView callerImage);
        }
    }



}
