package com.udelvr.RESTClient;


import com.udelvr.RESTClient.Driver.DriverDO;
import com.udelvr.RESTClient.Shipment.ShipmentDO;
import com.udelvr.RESTClient.Shipment.ShipmentResponse;
import com.udelvr.RESTClient.User.UserResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
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

    @GET("/user/{user_id}/shipments")
    void getUserShipments(@Path("user_id")String userId,Callback<List<ShipmentDO>> callback);

    @GET("/driver/shipments")
    void getAllShipments(Callback<List<ShipmentDO>> callback);

    @POST("/user/{user_id}/shipment")
    void addNewShipment(@Path("user_id")String userId,@Body MultipartTypedOutput multipartTypedOutput,
                    Callback<ShipmentResponse> callback);

    @PUT("/shipment/accept/{shipment_id}/{driver_id}")
    void acceptShipmentforDelivery(@Path("shipment_id")String shipmentId,@Path("driver_id")String driverId,
                        Callback<ShipmentResponse> callback);
//    @POST("/user/login")
//    void postLoginUser(@Body LoginData credentials,
//                       Callback<LoginResponse> callback);
//
//    @GET("/user/profile")
//    void getProfile(Callback<ProfileResponse> callback);



}