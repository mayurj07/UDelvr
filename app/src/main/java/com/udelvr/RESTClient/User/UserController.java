package com.udelvr.RESTClient.User;

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
 * Created by prasadshirsath on 4/19/15.
 */
public class UserController {

   public static boolean registerNewUser(User user) {

       final Boolean[] success = new Boolean[1];

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

           }

           @Override
           public void failure(RetrofitError error) {

           }
       });
            return true;
    }
}
