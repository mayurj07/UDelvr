package com.udelvr.RESTClient;


import com.udelvr.RESTClient.User.User;
import com.udelvr.RESTClient.User.UserResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.mime.MultipartTypedOutput;

/**
 * Created by prasadshirsath on 1/29/15.
 */
public interface API {
    @Multipart
    @POST("/users/signup")
    void createUser(@Body MultipartTypedOutput multipartTypedOutput,
                        Callback<UserResponse> callback);

    @GET("/user/{id}/shipments")
    List<User> groupList(@Path("id") int userId,Callback<UserResponse> callback);

    @GET("/driver/shipments")
    List<User> groupList(@Path("id") int userId,Callback<UserResponse> callback);



//    @POST("/user/login")
//    void postLoginUser(@Body LoginData credentials,
//                       Callback<LoginResponse> callback);
//
//    @GET("/user/profile")
//    void getProfile(Callback<ProfileResponse> callback);



}