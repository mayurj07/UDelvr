package com.udelvr.RESTClient;


import com.udelvr.RESTClient.Driver.DriverDO;
import com.udelvr.RESTClient.Shipment.ShipmentResponse;
import com.udelvr.RESTClient.User.UserResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.mime.MultipartTypedOutput;

/**
 * Created by prasadshirsath on 1/29/15.
 */
public interface API {
    @POST("/user/signup")
    void createUser(@Body MultipartTypedOutput multipartTypedOutput,
                        Callback<UserResponse> callback);

    @POST("/user/signin")
    void signinUser(@Body MultipartTypedOutput multipartTypedOutput,
                    Callback<UserResponse> callback);


    @POST("/user/{user_id}/driver")
    void addDriverDetails(@Path("user_id")String userId,@Body MultipartTypedOutput multipartTypedOutput,
                          Callback<DriverDO> callback);

    @GET("/driverShipment")
    void groupList(Callback<ShipmentResponse> callback);

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