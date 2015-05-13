package com.udelvr.RESTClient.Driver;

import android.app.ProgressDialog;

import com.udelvr.ApplicationContextProvider;
import com.udelvr.AuthStore;
import com.udelvr.CustomerMode.ApplyToBeDriver;
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


    public static void addDriverDetails(final ApplyToBeDriver applyToBeDriver,String userId,DriverDetails driverDetails) {
        final ProgressDialog mProgressDialog = new ProgressDialog(applyToBeDriver);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();

        final AuthStore authStore= new AuthStore(ApplicationContextProvider.getContext());
        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
        multipartTypedOutput.addPart("driverLicenseNo", new TypedString(driverDetails.getdriverLicenseNo()));
        multipartTypedOutput.addPart("licenseExpiry", new TypedString(driverDetails.getlicenseExpiry()));
        multipartTypedOutput.addPart("driverLicencePhoto", new TypedFile("driverLicencePhoto", driverDetails.getLicencePhoto()));

        RestClient.get().addDriverDetails(userId, multipartTypedOutput, new Callback<DriverDO>() {
            @Override
            public void success(DriverDO driverDO, Response response) {
            authStore.setDriverLicenceNo(driverDO.getDriverLicenseNo());
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                AuthStore authStore = new AuthStore(ApplicationContextProvider.getContext());
                applyToBeDriver.startDriverHomeActivity();
            }

            @Override
            public void failure(RetrofitError error) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                applyToBeDriver.OnFailedResponse(error);;
            }
        });
    }
}
