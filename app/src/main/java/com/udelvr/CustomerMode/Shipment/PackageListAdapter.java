package com.udelvr.CustomerMode.Shipment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udelvr.CustomerMode.PackageFullImagePopupActivity;
import com.udelvr.R;
import com.udelvr.RESTClient.RestClient;
import com.udelvr.RESTClient.Shipment.ShipmentDO;

import java.util.List;

/**
 * Prasad
 */
public class PackageListAdapter extends RecyclerView.Adapter<PackageListAdapter.ViewHolder>{

    private static List<ShipmentDO> packages;
    private int rowLayout;
    private Context mContext;

    public PackageListAdapter(List<ShipmentDO> packages, int rowLayout, Context context) {
        this.packages = packages;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final ShipmentDO aPackage = packages.get(i);
        viewHolder.packageName.setText(aPackage.getRecipientName());
        viewHolder.deliveryDate.setText("On: "+aPackage.getPickupDate()+" "+aPackage.getPickupTime());
        viewHolder.status.setText("Status: "+aPackage.getStatus());

        Picasso.with(mContext).setIndicatorsEnabled(true);
        String url= RestClient.getRoot()+aPackage.getThumbnailUrl();
        Picasso.with(mContext).load(url).fit().centerCrop().into(viewHolder.packageImage, new Callback() {
            @Override
            public void onSuccess() {
                System.out.println("onSuccess");
            }

            @Override
            public void onError() {
                System.out.println("onSuccessFail");
            }
        });

        viewHolder.packageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PackageFullImagePopupActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ShipmentImageURL", aPackage.getCompressedImageUrl());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return packages == null ? 0 : packages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView packageName,deliveryDate,status;
        public ImageView packageImage;

        public ViewHolder(View itemView) {
            super(itemView);
            packageName = (TextView) itemView.findViewById(R.id.recipientsName);
            deliveryDate =(TextView) itemView.findViewById(R.id.dateTime);
            status =(TextView) itemView.findViewById(R.id.status);
            packageImage = (ImageView)itemView.findViewById(R.id.packageThumbnailImage);
        }

    }

    public void add(ShipmentDO apackage)
    {
        packages.add(apackage);

    }

    public void notifyDataSetChanges()
    {
        this.notifyDataSetChanged();
    }
}
