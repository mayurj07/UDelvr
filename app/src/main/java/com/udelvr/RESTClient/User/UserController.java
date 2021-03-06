package com.udelvr.RESTClient.User;

import android.app.ProgressDialog;

import com.udelvr.ApplicationContextProvider;
import com.udelvr.AuthStore;
import com.udelvr.RESTClient.RestClient;
import com.udelvr.SplashScreen.SplashScreenFragmentRegisterNewUser;
import com.udelvr.SplashScreen.SplashScreenFragmentSignIn;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by prasadshirsath on 4/19/15.
 */
public class UserController {
    public static void sendSMSVerificationCode(String mobileNo,final SplashScreenFragmentRegisterNewUser splashScreenFragmentRegisterNewUser) {

        final ProgressDialog mProgressDialog = new ProgressDialog(splashScreenFragmentRegisterNewUser);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();

        RestClient.get().sendSMSVerificationCode(mobileNo,new Callback<String>() {
            @Override
            public void success(String verifiacationCode, Response response) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
               splashScreenFragmentRegisterNewUser.verifyMobileNo(verifiacationCode);
            }

            @Override
            public void failure(RetrofitError error) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
               splashScreenFragmentRegisterNewUser.onSendSMSVerifiactionCodeFailure();
            }
        });
    }
   public static void registerNewUser(User user,final SplashScreenFragmentRegisterNewUser splashScreenFragmentRegisterNewUser) {
       final ProgressDialog mProgressDialog = new ProgressDialog(splashScreenFragmentRegisterNewUser);
       mProgressDialog.setIndeterminate(true);
       mProgressDialog.setProgressNumberFormat(null);
       mProgressDialog.setProgressPercentFormat(null);
       mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
       mProgressDialog.show();



       MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
       multipartTypedOutput.addPart("fullName", new TypedString(user.getFullName()));
       multipartTypedOutput.addPart("email", new TypedString(user.getEmail()));
       multipartTypedOutput.addPart("mobileNo", new TypedString(user.getMobileNo()));
       multipartTypedOutput.addPart("password", new TypedString(user.getPassword()));
       multipartTypedOutput.addPart("deviceId", new TypedString(new AuthStore(ApplicationContextProvider.getContext()).getDeviceid()));
       multipartTypedOutput.addPart("profilePhoto", new TypedFile("profilephoto", user.getprofilePhoto()));

       RestClient.get().createUser(multipartTypedOutput, new Callback<UserResponse>() {
           @Override
           public void success(UserResponse userResponse, Response response) {

               AuthStore auth = new AuthStore(ApplicationContextProvider.getContext());
               auth.setEmail(userResponse.getEmail());
               auth.setProfilePicUrl(userResponse.getProfileURL());
               auth.setUserid(userResponse.getUserId());
               auth.setDeviceid(userResponse.getDeviceId());
               auth.setPassword(userResponse.getPassword());
               auth.setDriverLicenceNo(userResponse.getDriverLicenseNo());
               if (mProgressDialog.isShowing())
                   mProgressDialog.dismiss();
               splashScreenFragmentRegisterNewUser.startCustomerMainActivity();

           }

           @Override
           public void failure(RetrofitError error) {
               if (mProgressDialog.isShowing())
                   mProgressDialog.dismiss();
               splashScreenFragmentRegisterNewUser.onRegistrationfailed(error.getMessage());
           }
       });
   }

    public static void signinUser(User user,final SplashScreenFragmentSignIn splashScreenFragmentSignIn) {

        final Boolean[] success = new Boolean[1];
        final ProgressDialog mProgressDialog = new ProgressDialog(splashScreenFragmentSignIn.getActivity());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressNumberFormat(null);
        mProgressDialog.setProgressPercentFormat(null);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();


        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
        multipartTypedOutput.addPart("email", new TypedString(user.getEmail()));
        multipartTypedOutput.addPart("password", new TypedString(user.getPassword()));

        RestClient.get().signinUser(multipartTypedOutput, new Callback<UserResponse>() {
            @Override
            public void success(UserResponse userResponse, Response response) {

                AuthStore auth = new AuthStore(ApplicationContextProvider.getContext());
                auth.setEmail(userResponse.getEmail());
                auth.setProfilePicUrl(userResponse.getProfileURL());
                auth.setUserid(userResponse.getUserId());
                auth.setDeviceid(userResponse.getDeviceId());
                auth.setPassword(userResponse.getPassword());
                auth.setDriverLicenceNo(userResponse.getDriverLicenseNo());
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                splashScreenFragmentSignIn.startCustomerMainActivity();

            }

            @Override
            public void failure(RetrofitError error) {
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                splashScreenFragmentSignIn.OnsignInfailed();
            }
        });
    }
}
