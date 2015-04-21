package com.udelvr.RESTClient;

import com.squareup.okhttp.OkHttpClient;
import com.udelvr.ApplicationContextProvider;
import com.udelvr.AuthStore;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

import static retrofit.RestAdapter.Builder;

/**
 * Created by prasadshirsath on 1/29/15.
 */
public class RestClient {

    private static API REST_CLIENT;
    private static String ROOT = "http://192.168.43.98:8080";//52.8.19.255

    static {
        setupRestClient();
    }

    private RestClient() {

    }

    public static API get() {
        return REST_CLIENT;
    }

    private static void setupRestClient()
    {
        final AuthStore authStore = new AuthStore(ApplicationContextProvider.getContext());
        Builder builder = new Builder()
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()));
                builder.setLogLevel(RestAdapter.LogLevel.FULL);

        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                 //request.addHeader("Content-type", "multipart/form-data");
//                if (authStore.getAuthTocken()!=null) {//isUserLoggedIn()
//                    //request.addHeader("Authorization",authStore.getAuthTocken());
////                    request.addHeader("email",authStore.getEmail());
////                    request.addHeader("password",authStore.getPassword());
////                    request.addHeader("deviceid",authStore.getDeviceid());
////                }
            }
        });

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(API.class);
    }
}