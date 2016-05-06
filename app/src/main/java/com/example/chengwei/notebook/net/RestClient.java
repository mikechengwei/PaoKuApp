package com.example.chengwei.notebook.net;
import java.io.IOException;

import okhttp3.Interceptor;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chengwei on 3/27/16.
 */
public final class RestClient {
    private static  String ENDPOINT = "http://www.vavian.com/";
    private static IRestService mRestService = null;
    private static AllFragmentService allFragmentService=null;
    private static final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new FakeInterceptor()).build();
    private static final Retrofit retrofit = new Retrofit.Builder()
            // Using custom Jackson Converter to parse JSON
            // Add dependencies:
            // com.squareup.retrofit:converter-jackson:2.0.0-beta2
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//这个是为了RxJava
            .addConverterFactory(GsonConverterFactory.create())
                    // Endpoint
            .baseUrl(ENDPOINT)
            .client(client)
            .build();

    public static IRestService getIRestServiceClient() {
        if(mRestService == null) {
            mRestService = retrofit.create(IRestService.class);
        }
        return mRestService;
    }
    public static AllFragmentService getAllFragmentServiceClient() {
        if(allFragmentService == null) {
            allFragmentService = retrofit.create(AllFragmentService.class);
        }
        return allFragmentService;
    }

}
