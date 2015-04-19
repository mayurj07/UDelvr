package com.udelvr.DriverMode.Shipment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udelvr.R;

import java.util.List;

/**
 * Created by Trey Robinson on 8/3/14.
 * Copyright 2014 MindMine LLC.
 */
public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder>{

    private static List<DriverPackage> driverPackages;
    private int rowLayout;
    private Context mContext;

    public PackageListAdapter(List<DriverPackage> driverPackages, int rowLayout, Context context) {
        this.driverPackages = driverPackages;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        DriverPackage aDriverPackage = driverPackages.get(i);
        viewHolder.destination.setText(aDriverPackage.destination );
        viewHolder.amount.setText("$"+aDriverPackage.amount);
        viewHolder.dateTime.setText(aDriverPackage.dateTime);
        Picasso.with(mContext).setIndicatorsEnabled(true);
        String url = "http://maps.google.com/maps/api/staticmap?center="+aDriverPackage.destinationLatitude+ "," +aDriverPackage.destinationLongitude+ "&markers="+aDriverPackage.destinationLatitude+ "," +aDriverPackage.destinationLongitude+"&zoom=15&size=500x300&sensor=true";
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView destination;
        public TextView amount;
        public TextView dateTime;
        public ImageView packageMap;


        public ViewHolder(View itemView) {
            super(itemView);
            destination = (TextView) itemView.findViewById(R.id.destinationText);
            packageMap = (ImageView)itemView.findViewById(R.id.packageDestinationStaticMap);
            amount=(TextView)itemView.findViewById(R.id.amountText);
            dateTime=(TextView)itemView.findViewById(R.id.dateTimeText);


        }

    }

    public void add(DriverPackage apackage)
    {
        driverPackages.add(apackage);
    }

    public void notifyDataSetChanges()
    {
        this.notifyDataSetChanged();
    }
}
