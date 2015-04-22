package com.udelvr.DriverMode;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.udelvr.CustomerMode.CustomerMainActivity;
import com.udelvr.DriverMode.Shipment.DriverPackageManager;
import com.udelvr.DriverMode.Shipment.PackageListAdapter;
import com.udelvr.R;
import com.udelvr.RESTClient.Shipment.ShipmentController;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private PackageListAdapter mAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private FloatingActionButton fab_driver;
    private ShipmentController shipmentController;
    private HomeFragment homeFragment;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        shipmentController=new ShipmentController();
        swipeRefreshLayout = (SwipeRefreshLayout)getActivity().findViewById(R.id.swipeRefreshLayout);
        fab_driver=(FloatingActionButton)getActivity().findViewById(R.id.fab_driver);
        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PackageListAdapter(DriverPackageManager.getInstance().getDriverPackages(), R.layout.row_driver_package, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        homeFragment=this;
        fab_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CustomerMainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.orange,R.color.green,R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                shipmentController.getAllShipments(homeFragment);

            }
        });

    }

    public void onLoadComplete() {
                mAdapter.notifyDataSetChanged();
        if(swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
    }


    public HomeFragment(){}


    public static Fragment newInstance(Context context) {
        HomeFragment f = new HomeFragment();
        return f;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_driver_home, container,false);

        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        shipmentController.getAllShipments(homeFragment);

    }

}
