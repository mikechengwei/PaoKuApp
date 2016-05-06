package com.example.chengwei.notebook.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.TextView;

import com.example.chengwei.notebook.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chengwei on 4/4/16.
 */
public class SwipeRefreshLayoutActivity extends Activity {
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeView;
    @Bind(R.id.rndNum)
    TextView rndNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swiperefreshlayout_demo);
        ButterKnife.bind(this);
        swipeView.setColorSchemeResources(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_green_light);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);
                        double f = Math.random();
                        rndNum.setText(String.valueOf(f));
                    }
                }, 3000);
            }
        });
    }
}
