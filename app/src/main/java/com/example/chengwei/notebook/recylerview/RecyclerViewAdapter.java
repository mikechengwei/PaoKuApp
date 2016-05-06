package com.example.chengwei.notebook.recylerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chengwei.notebook.R;
import com.example.chengwei.notebook.listener.MyItemClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;

/**
 * Created by WuXiaolong on 2015/7/2.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NewsViewHolder> {

    private Context mContext;
    private ArrayList<String> dataList;

   private  MyItemClickListener mItemClickListener;
    public ArrayList<String> getDataList() {
        return dataList;
    }

    public RecyclerViewAdapter(Context context,  ArrayList<String> dataList) {
        this.dataList = dataList;
        mContext = context;

    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_info_all_item, parent, false);

        return new NewsViewHolder(v,mItemClickListener);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {
         holder.v.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public int getViewHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }
    public static  class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private  MyItemClickListener itemClickListener;
        private TextView v;
        public NewsViewHolder(View itemView,MyItemClickListener myItemClickListener) {
            super(itemView);
            this.itemClickListener=myItemClickListener;
            itemView.setOnClickListener(this);
             v=(TextView)itemView.findViewById(R.id.renwuinfo);
            //v=itemView.findViewById(R.id.renwuinfo);
        }
        @Override
        public void onClick(View view) {
           if(itemClickListener!=null){
               itemClickListener.onItemClick(view,getPosition());
           }
        }
    }

    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }



}