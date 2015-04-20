package com.udelvr.RESTClient;


import com.udelvr.RESTClient.User.UserResponse;
import com.udelvr.RESTClient.Shipment.ShipmentResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.MultipartTypedOutput;

/**
 * Created by prasadshirsath on 1/29/15.
 */
public interface API {
    @POST("/users/signup")
    void createUser(@Body MultipartTypedOutput multipartTypedOutput,
                        Callback<UserResponse> callback);
    @POST("/shipment")
    void addNewShipment(@Body MultipartTypedOutput multipartTypedOutput,
                    Callback<ShipmentResponse> callback);
//    @POST("/user/login")
//    void postLoginUser(@Body LoginData credentials,
//                       Callback<LoginResponse> callback);
//
//    @GET("/user/profile")
//    void getProfile(Callback<ProfileResponse> callback);



}