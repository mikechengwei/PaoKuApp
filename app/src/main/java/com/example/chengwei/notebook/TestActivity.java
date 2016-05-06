package com.example.chengwei.notebook;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.chengwei.notebook.bean.Teacher;
import com.example.chengwei.notebook.bean.netItem;
import com.example.chengwei.notebook.cache.CacheManager;
import com.example.chengwei.notebook.net.OsApi;
import com.example.chengwei.notebook.net.OsData;
import com.example.chengwei.notebook.net.RestClient;
import com.example.chengwei.notebook.recylerview.RecyclerViewAdapter;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by chengwei on 3/26/16.
 */
public class TestActivity extends Activity{
    TextView v;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        v=new TextView(this);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OsData.loadTestData(new Callback<List<netItem>>() {
//                    @Override
//                    public void onResponse(Response<List<netItem>> response, Retrofit retrofit) {
//                        // Get result Repo from response.body()
//                        v.setText(response.body().get(0).toString());
//                        Log.i("Tag", response.body().get(0).toString());
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        Log.i("Tag", "fail");
//                    }
//                });
//            }
//        }).start();
//      new Thread(new Runnable() {
//          @Override
//          public void run() {
//              Call<Teacher> teacherCall = RestClient.getClient().getTeacherById("1");
//              teacherCall.enqueue(new Callback<Teacher>() {
//                  @Override
//                  public void onResponse(Call<Teacher> call, Response<Teacher> response) {
//                      Teacher teacher = response.body();
//                      Log.d("aa", teacher.getName());
//                  }
//
//                  @Override
//                  public void onFailure(Call<Teacher> call, Throwable t) {
//                      Log.d("aaa", "Failure " + t.getMessage());
//                  }
//
//
//              });
//          }
//      }).start();

//        CacheManager.saveObject(this, setList(), "test");
//        Serializable seri = CacheManager.readObject(this,
//                "test");
//        v.setText(((ArrayList<String>) seri).get(0).toString());
//        Observable<Teacher> london = RestClient.getIRestServiceClient().getTeacherById("1");
//
//        london.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Teacher>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.i("observer", "complete");
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i("observer", e.toString());
//                    }
//                    @Override
//                    public void onNext(Teacher teacher) {
//                        v.setText(teacher.getName());
//                    }
//                });
        Observable<ArrayList<String>> london = RestClient.getAllFragmentServiceClient().getMockDataById(String.valueOf(0));

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<String>>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("observer", e.toString());
                    }
                    @Override
                    public void onNext(ArrayList<String> list) {
                        v.setText(list.get(0).toString());
                        //saveCacheData(list);
                    }
                });
        setContentView(v);
    }
    private int mCount=1;
    private ArrayList<String> setList(){
        ArrayList<String> dataList = new ArrayList<>();
        int start = 20 * (mCount - 1);
        for (int i = start; i < 20 * mCount; i++) {
            dataList.add("测试数据" + i);
        }
        return dataList;

    }
    private void sendRequestData(){
        Observable<ArrayList<String>> london = RestClient.getAllFragmentServiceClient().getMockDataById(String.valueOf(0));

        london.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<String>>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("observer", e.toString());
                    }
                    @Override
                    public void onNext(ArrayList<String> list) {

                        //saveCacheData(list);
                    }
                });

    }
}
