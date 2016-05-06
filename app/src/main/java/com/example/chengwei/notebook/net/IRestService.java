package com.example.chengwei.notebook.net;

import com.example.chengwei.notebook.bean.Teacher;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chengwei on 3/27/16.
 */
public interface IRestService {

    String ENDPOINT = "http://www.vavian.com/";

    @GET("/")
    Observable<Teacher> getTeacherById(@Query("id")  String id);
}
