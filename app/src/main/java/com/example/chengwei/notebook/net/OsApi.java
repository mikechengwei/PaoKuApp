package com.example.chengwei.notebook.net;

import android.util.Log;

import com.example.chengwei.notebook.bean.Teacher;
import com.example.chengwei.notebook.bean.netItem;


import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by chengwei on 3/26/16.
 */

public class OsApi {
    //rxjava中
    public interface APIService {
        @POST("index")
        Call<List<netItem>> loadRepo();
    }
    public interface allFragmentService{
        @GET("/")
        Observable<List<String>> getTeacherById(@Query("id") final String id);
    }

}
