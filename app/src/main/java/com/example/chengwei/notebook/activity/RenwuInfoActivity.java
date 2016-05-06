package com.example.chengwei.notebook.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;

import com.example.chengwei.notebook.R;

import butterknife.ButterKnife;

/**
 * Created by chengwei on 3/11/16.
 */
public class RenwuInfoActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建一个视图
        setContentView(R.layout.renwu_info_detail_activity);
        initActionBar();
    }
    public  void initActionBar(){

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);//显示home区域
        actionBar.setDisplayHomeAsUpEnabled(true);//显示返回图片
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>  资源详情</font>"));
        actionBar.setElevation(0);
    }
}
