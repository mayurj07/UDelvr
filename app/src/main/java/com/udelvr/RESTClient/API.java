package com.udelvr.RESTClient;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.MultipartTypedOutput;

/**
 * Created by prasadshirsath on 1/29/15.
 */
public interface API {
    @POST("/user")
    void postCreateUser(@Body MultipartTypedOutput multipartTypedOutput,
                        Callback<String> callback);
//
//    @POST("/user/login")
//    void postLoginUser(@Body LoginData credentials,
//                       Callback<LoginResponse> callback);
//
//    @GET("/user/profile")
//    void getProfile(Callback<ProfileResponse> callback);



}