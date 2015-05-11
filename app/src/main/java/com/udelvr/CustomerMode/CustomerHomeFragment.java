package com.udelvr.CustomerMode;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.melnykov.fab.FloatingActionButton;
import com.udelvr.ApplicationContextProvider;
import com.udelvr.AuthStore;
import com.udelvr.CustomerMode.Shipment.AddShipment;
import com.udelvr.CustomerMode.Shipment.CustomerPackageManager;
import com.udelvr.CustomerMode.Shipment.PackageListAdapter;
import com.udelvr.DriverMode.DriverMainActivity;
import com.udelvr.R;
import com.udelvr.RESTClient.Shipment.ShipmentController;

public class CustomerHomeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    Button add_shipment_btn;
    private PackageListAdapter mAdapter;

    FloatingActionButton fab_customer;
    AuthStore authStore;
    private ShipmentController shipmentController;
    private SwipeRefreshLayout swipeRefreshLayout;

    private CustomerHomeFragment customerHomeFragment;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customerHomeFragment=this;
        authStore=new AuthStore(ApplicationContextProvider.getContext());

        fab_customer = (FloatingActionButton)getActivity().findViewById(R.id.fab_customer);
        shipmentController=new ShipmentController();
        swipeRefreshLayout = (SwipeRefreshLayout)getActivity().findViewById(R.id.swipeRefreshLayout);

        mRecyclerView = (RecyclerView)getActivity().findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PackageListAdapter(CustomerPackageManager.getInstance().getCustomerPackages(), R.layout.row_package, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        fab_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(authStore.getDriverLicenceNo()==null) {
                    Log.d("Prasad","driverDetails");

                    Intent intent = new Intent(getActivity(), ApplyToBeDriver.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                else
                {
                    Log.d("Prasad","driverMain");
                    Intent i = new Intent(getActivity(), DriverMainActivity.class);
                    startActivity(i);
                }

            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.orange,R.color.green,R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shipmentController.getUserShipments(authStore.getUserId(),customerHomeFragment);

            }
        });


    }
    public void onLoadComplete() {
        mAdapter.notifyDataSetChanged();
        if(swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }



	public CustomerHomeFragment(){}


    public static Fragment newInstance(Context context) {
        CustomerHomeFragment f = new CustomerHomeFragment();
        return f;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container,false);
        add_shipment_btn = (Button) root.findViewById(R.id.button_add_shipment);
        add_shipment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddShipment.class);
                startActivity(i);
            }
        });




        return root;
    }
    @Override
    public void onResume() {
        super.onResume();

        shipmentController.getUserShipments(authStore.getUserId(),customerHomeFragment);
        mAdapter.notifyDataSetChanged();
    }
    public void onSignInFailed() {

    }

}

