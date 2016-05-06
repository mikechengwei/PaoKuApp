package com.example.chengwei.notebook.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.chengwei.notebook.R;
import com.example.chengwei.notebook.bean.Item;


import android.content.Context;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by admin on 2016/1/10.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.NewsViewHolder> {
    // 数据集
    private ArrayList<Item> items;
    private final LayoutInflater mLayoutInflater;
    private Context context;
    public ItemAdapter(Context context,ArrayList<Item> items) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
        this.items = items;
        this.context=context;
    }

    @Override
    public ItemAdapter.NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = mLayoutInflater.inflate(R.layout.fragment_info_all_item, viewGroup, false);
        // 创建一个ViewHolder
        ItemAdapter.NewsViewHolder holder = new ItemAdapter.NewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemAdapter.NewsViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return items.size()-1;
    }

    public static  class NewsViewHolder extends RecyclerView.ViewHolder {


        public NewsViewHolder(View itemView) {
            super(itemView);



        }
    }
}
