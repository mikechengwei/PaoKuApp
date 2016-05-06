package com.example.chengwei.notebook.net;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chengwei on 4/3/16.
 */
public interface AllFragmentService {

        @GET("/")
        Observable<ArrayList<String>> getMockDataById(@Query("id") final String id);

}
