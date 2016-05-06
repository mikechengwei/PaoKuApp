package com.example.chengwei.notebook.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.chengwei.notebook.R;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chengwei on 3/12/16.
 */
public class DianpuFragment extends Fragment {
    private View self;
    @Bind(R.id.mylinear)
    LinearLayout linearLayout;
    private boolean tag = true;
    private View item;
    private List<View> listItem;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.self == null) {


            // View view =LayoutInflater.from(this).inflate(R.layout.view_item, null);//也可以从XML中加载布局
            self = inflater.inflate(R.layout.test_dianpu, null);
            ButterKnife.bind(this, self);



        }
        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }
        return this.self;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //避免重复加载
        if (tag) {
            // ButterKnife.bind(this, view);
            initView();
            tag = false;
        }
    }

    private void initView() {
        LayoutInflater mInflater = LayoutInflater.from(getContext());//
        for(int i=0;i<3;i++){
            View view=mInflater.inflate(R.layout.fragment_dianpu_main, null);
            GridView grid = ButterKnife.findById(view, R.id.main_GridView);
            grid.setAdapter(new GridViewAdapter(getContext()));
            //listItem.add(view);
            linearLayout.addView(view);
        }

       // self.addView(listItem);
    }

    private class GridViewAdapter extends BaseAdapter {

        private Context context;

        public GridViewAdapter(Context context) {
            this.context = context;
        }

        int count = 10;

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            TextView result = new TextView(context);
//            result.setText("Item " + position);
//            result.setTextColor(Color.BLACK);
//            result.setTextSize(24);
//            result.setLayoutParams(new AbsListView.LayoutParams(new AbsListView.LayoutParams(GridLayout.LayoutParams.FILL_PARENT, GridLayout.LayoutParams.WRAP_CONTENT)));
//            result.setGravity(Gravity.CENTER);
//            result.setBackgroundColor(Color.WHITE); //设置背景颜色
//            return result;
            LayoutInflater mInflater = LayoutInflater.from(getContext());//
            item=mInflater.inflate(R.layout.fragment_dianpu_main_griditem, null);
            return item;
        }
    }
}
