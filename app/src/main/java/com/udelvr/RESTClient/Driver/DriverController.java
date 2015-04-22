package com.udelvr.RESTClient.Driver;

import android.app.ProgressDialog;
import android.content.Context;

import com.udelvr.ApplicationContextProvider;
import com.udelvr.AuthStore;
import com.udelvr.RESTClient.RestClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by prasadshirsath on 4/20/15.
 */
public class DriverController {


    public static boolean addDriverDetails(Context mContext,String userId,DriverDetails driverDetails) {
        final ProgressDialog mProgressDialog = new ProgressDialog(mContext);
        final AuthStore authStore= new AuthStore(ApplicationContextProvider.getContext());
        final Boolean[] success = new Boolean[1];
        success[0] = true;
        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
        multipartTypedOutput.addPart("driverLicenseNo", new TypedString(driverDetails.getdriverLicenseNo()));
        multipartTypedOutput.addPart("licenseExpiry", new TypedString(driverDetails.getlicenseExpiry()));
        multipartTypedOutput.addPart("driverLicencePhoto", new TypedFile("driverLicencePhoto", driverDetails.getLicencePhoto()));

        RestClient.get().addDriverDetails(userId, multipartTypedOutput, new Callback<DriverDO>() {
            @Override
            public void success(DriverDO driverDO, Response response) {

            authStore.setDriverLicenceNo(driverDO.getDriverLicenseNo());
            }

            @Override
            public void failure(RetrofitError error) {
                success[0] = false;

            }
        });
        return success[0];
    }
}
