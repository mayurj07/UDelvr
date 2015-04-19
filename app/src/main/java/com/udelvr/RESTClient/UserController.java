package com.udelvr.RESTClient;

import android.util.Log;

import java.io.File;

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

    registerNewUser(User user)
    {
        /**Dummy REST call**/
        File myFile = new File(tempPath);

        MultipartTypedOutput multipartTypedOutput = new MultipartTypedOutput();
        multipartTypedOutput.addPart("fullName",new TypedString("Prasad Shirsath"));
        multipartTypedOutput.addPart("email",new TypedString("prasadss.in@gmail.com"));
        multipartTypedOutput.addPart("mobileNo",new TypedString("9989899898"));
        multipartTypedOutput.addPart("password",new TypedString("!@#!@#!@#"));
        multipartTypedOutput.addPart("deviceID",new TypedString("!@#!@#!@#"));
        multipartTypedOutput.addPart("file", new TypedFile("sa",myFile));


        RestClient.get().postCreateUser(multipartTypedOutput, new Callback<String>() {
            @Override
            public void success(String createUserResponse, Response response) {
                System.out.print("SENT REQUEST!!!!");
                // Toast.makeText(ApplicationContextProvider.getContext(), "Success" + createUserResponse, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Test!!!", "CanNot Create.." + error);
                //Toast.makeText(ApplicationContextProvider.getContext(), "failed!", Toast.LENGTH_SHORT).show();

            }
        });
        /******/
    }
}
