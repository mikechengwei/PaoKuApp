package com.example.chengwei.notebook.net;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.chengwei.notebook.BuildConfig;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;

import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by chengwei on 3/27/16.
 */
public class FakeInterceptor implements Interceptor {
    // FAKE RESPONSES.
    private final static String TEACHER_ID_1 = "{\"id\":1,\"age\":28,\"name\":\"Victor Apoyan\"}";
    private final static String TEACHER_ID_2 = "{\"id\":1,\"age\":16,\"name\":\"Tovmas Apoyan\"}";

    //在这里我们做设定
    public String mockData(int page) {
        List<String> list = new ArrayList<String>();
        int beginLine=(page-1)*5;
        for(int i=0;i<5;i++){
            int line=beginLine+i;
            list.add("测试数据"+line);
        }
        return JSON.toJSONString(list);
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = null;
        if (BuildConfig.DEBUG) {
            String responseString = "";
            // Get Request URI.
            final HttpUrl uri = chain.request().url();
            // Get Query String.
            final String query = uri.query();

            // Parse the Query String.
            final String[] parsedQuery = query.split("=");
            if (parsedQuery[0].equalsIgnoreCase("id")) {
                responseString = mockData(Integer.parseInt(parsedQuery[1]));
            }

            response = new Response.Builder()
                    .code(200)
                    .message(responseString)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();
        } else {
            response = chain.proceed(chain.request());
        }

        return response;

    }
}

