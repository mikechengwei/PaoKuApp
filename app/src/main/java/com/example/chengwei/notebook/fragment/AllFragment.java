package com.example.chengwei.notebook.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chengwei.notebook.R;
import com.example.chengwei.notebook.adapter.ConstellationAdapter;
import com.example.chengwei.notebook.adapter.GirdDropDownAdapter;
import com.example.chengwei.notebook.adapter.ItemAdapter;
import com.example.chengwei.notebook.adapter.ListDropDownAdapter;
import com.example.chengwei.notebook.bean.Item;
import com.example.chengwei.notebook.bean.Teacher;
import com.example.chengwei.notebook.cache.CacheManager;
import com.example.chengwei.notebook.listener.MyItemClickListener;
import com.example.chengwei.notebook.manager.FullyLinearLayoutManager;
import com.example.chengwei.notebook.net.RestClient;
import com.example.chengwei.notebook.recylerview.PullLoadMoreRecyclerView;
import com.example.chengwei.notebook.recylerview.RecyclerViewAdapter;
import com.example.chengwei.notebook.ui.EmptyLayout;
import com.example.chengwei.notebook.ui.RefreshLayout;
import com.example.chengwei.notebook.util.TDevice;
import com.example.chengwei.notebook.util.UiHelper;
import com.yyydjk.library.DropDownMenu;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by chengwei on 3/6/16.
 */
public class AllFragment extends Fragment implements MyItemClickListener {

    @Bind(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;
    private EmptyLayout errorLayout;
    private String headers[] = {"类别", "年龄", "性别", "时间"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private ConstellationAdapter constellationAdapter;

    private String citys[] = {"不限", "发单", "快递", "外卖", "特殊", "其它"};
    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
    private String sexs[] = {"不限", "男", "女"};
    private String constellations[] = {"不限", "白天", "晚上", "上午", "下午"};

    private int constellationPosition = 0;
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private int mCurrentPage = 1;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    private static AllFragment instance;
    private View self;
    private View content_main;

    private boolean tag = true;

    public static AllFragment getInstance() {
        if (instance == null) instance = new AllFragment();
        return instance;
    }

    protected BackHandlerInterface backHandlerInterface;

    public interface BackHandlerInterface {
        public void setSelectedFragment(AllFragment backHandledFragment);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //回调函数赋值
        if (!(getActivity() instanceof BackHandlerInterface)) {
            throw new ClassCastException("Hosting activity must implement BackHandlerInterface");
        } else {
            backHandlerInterface = (BackHandlerInterface) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.self == null) {
            this.self = inflater.inflate(R.layout.fragment_info_all, null);
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
            ButterKnife.bind(this, view);
            initView();
            tag = false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //将自己的实例传出去
        backHandlerInterface.setSelectedFragment(this);
    }

    private void initView() {
        LayoutInflater mInflater = LayoutInflater.from(getContext());//
        final ListView cityView = new ListView(getContext());
        cityAdapter = new GirdDropDownAdapter(getContext(), Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(getContext());
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(getContext(), Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(getContext());
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(getContext(), Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

        //init constellation
        final View constellationView = mInflater.inflate(R.layout.custom_layout, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        constellationAdapter = new ConstellationAdapter(getContext(), Arrays.asList(constellations));
        constellation.setAdapter(constellationAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
                mDropDownMenu.closeMenu();
            }
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        popupViews.add(constellationView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
                mDropDownMenu.closeMenu();
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constellationAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });

        //用代码进行动态绑定

        content_main = mInflater.inflate(R.layout.fragment_all_info_contentmain, null);

        // mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) content_main.findViewById(R.id.pullLoadMoreRecyclerView);
        mPullLoadMoreRecyclerView = ButterKnife.findById(content_main, R.id.pullLoadMoreRecyclerView);
        errorLayout=ButterKnife.findById(content_main,R.id.error_layout);
        errorLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentPage = 0;
               // mState = STATE_REFRESH;
                errorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                requestData(true);
            }
        });
       //mPullLoadMoreRecyclerView.setRefreshing(true);
        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setPullLoadMoreListener(new PullLoadMoreListener());
        Log.i("AllFragment", headers.length + "|" + popupViews.size());
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, content_main);

        mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), new ArrayList<String>());
        mRecyclerViewAdapter.setOnItemClickListener(this);
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);

       // new DataAsyncTask().execute();
//sendRequestData();
        requestData(true);

    }

    public boolean onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
            Log.i("allFragment", "guanbicaidan");
            return true;
        }
        return false;
    }

    private ArrayList<String> setList() {
        ArrayList<String> dataList = new ArrayList<>();
        int start = 20 * (mCurrentPage - 1);
        for (int i = start; i < 20 * mCurrentPage; i++) {
            dataList.add("测试数据" + i);
        }
        return dataList;

    }


    /* 判断是否需要读取缓存的数据
    * @param refresh
    * @return
            */
    protected boolean isReadCacheData(boolean refresh) {
        String key = getCacheKey()+mCurrentPage ;
        // 第一页若不是主动刷新，缓存存在，优先取缓存的
        if (CacheManager.isExistDataCache(getActivity(), key) && !refresh
                && mCurrentPage == 1) {
            Log.i("isReadCacheData","1exist");
            return true;
        }
        // 其他页数的，缓存存在以及还没有失效，优先取缓存的
        if (CacheManager.isExistDataCache(getActivity(), key)
                && !CacheManager.isCacheDataFailure(getActivity(), key)
                && mCurrentPage != 1 && !refresh) {
            Log.i("isReadCacheData", "2exist");
            return true;
        }
        Log.i("isReadCacheData", "not exist");
        return false;
    }

    /**
     * false代表去缓存里取 true代表直接获取
     *
     * @param tag
     */
    private void requestData(Boolean tag) {
        String key = getCacheKey()+mCurrentPage;
        if (isReadCacheData(tag)) {
            Log.i("requestData","read");
            readCacheData(key);
        } else {
            Log.i("requestData", "send");
            sendRequestData();
        }



        //sendRequestData();
    }

    private void sendRequestData() {
         Observable<ArrayList<String>> london = RestClient.getAllFragmentServiceClient().getMockDataById(String.valueOf(mCurrentPage));

        london.subscribeOn(Schedulers.newThread()).delay(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<String>>() {
                    @Override
                    public void onCompleted() {
                        if (mCurrentPage == 1) {
                            Log.i("sendRequestData", "page1");
                            errorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                        } else {
                            Log.i("sendRequestDataLoadMore", "complete");
                            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                        }
                        Log.i("sendRequestData", "finish");
                       // mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("sendRequestData", e.toString());
                    }

                    @Override
                    public void onNext(ArrayList<String> list) {

                        Log.i("sendRequestData", "onNExt");
                        if (mRecyclerViewAdapter == null) {

                            mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), list);

                            mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);

                        } else {
                            mRecyclerViewAdapter.getDataList().addAll(list);
                            mRecyclerViewAdapter.notifyDataSetChanged();
                        }


                         saveCacheData(list);
                    }
                });


    }

    private void saveCacheData(ArrayList<String> list) {

        Observable<Boolean> observable = Observable.just(list).map(new Func1<ArrayList<String>, Boolean>() {
                                                                       @Override
                                                                       public Boolean call(ArrayList<String> strings) {
                                                                           Boolean tag = CacheManager.saveObject(getContext(), strings, "allFragment" + mCurrentPage);
                                                                           Log.i("saveCacheData", "finish");
                                                                           return tag;
                                                                       }
                                                                   }
        );
        observable.subscribeOn(Schedulers.newThread()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                               @Override
                               public void call(Boolean taskName) {
                                   if(taskName==true){
                                       Log.i("saveObject", "success");
                                   }else{
                                       Log.i("saveObject", "fail");
                                   }

                               }
                           }

                );
    }

    private void readCacheData(String cacheKey) {
        Serializable seri = CacheManager.readObject(getContext(),
                "allFragment" + mCurrentPage);
        Observable<ArrayList<String>> observable=Observable.just(cacheKey).map(new Func1<String, ArrayList<String>>() {

            @Override
            public ArrayList<String> call(String s) {
                Serializable seri = CacheManager.readObject(getContext(),
                        "allFragment" + mCurrentPage);

                return (ArrayList<String>) seri;

            }
        });
        observable.observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<String>>() {
                               @Override
                               public void onCompleted() {
//                                   if (mCurrentPage == 1) {
//                                       Log.i("mPullLoadMore", "page1");
//                                       mPullLoadMoreRecyclerView.setRefreshing(false);
//                                   } else {
//                                       Log.i("mPullLoadMore", "complete");
//
//                                   }
                                   Log.i("readCacheData", "finish");
                                   mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.i("readCacheData", e.toString());
                               }

                               @Override
                               public void onNext(ArrayList<String> list) {

                                   Log.i("readCacheData", "onNExt");
                                   if (mRecyclerViewAdapter == null) {

                                       mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), list);

                                       mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);

                                   } else {
                                       mRecyclerViewAdapter.getDataList().addAll(list);
                                       mRecyclerViewAdapter.notifyDataSetChanged();
                                   }
                               }
                           }
                );

    }

    private AsyncTask<String, Void, List<String>> mCacheTask;

    private String getCacheKey() {
        return "allFragment";
    }


    class DataAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            try{
                Thread.sleep(3000);
            }catch (Exception e){

            }
            return setList();
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            if (mRecyclerViewAdapter == null) {

                mRecyclerViewAdapter = new RecyclerViewAdapter(getContext(), strings);

                mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);

            } else {
                mRecyclerViewAdapter.getDataList().addAll(strings);
                mRecyclerViewAdapter.notifyDataSetChanged();
            }
            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
           //mPullLoadMoreRecyclerView.setRefreshing(false);
        }

    }

    class PullLoadMoreListener implements PullLoadMoreRecyclerView.PullLoadMoreListener {
        @Override
        public void onRefresh() {
            setRefresh();
            //mPullLoadMoreRecyclerView.setSwipeRefreshLoadingState();
            requestData(false);
            //new DataAsyncTask().execute();
            //sendRequestData();
        }

        @Override
        public void onLoadMore() {

            if (!mPullLoadMoreRecyclerView.isLoadMore()) {
                Log.i("swipeRefreshLayout", "isLoadMore please wait");
            } else {
                mCurrentPage = mCurrentPage + 1;
                Log.i("onloadmore", String.valueOf(mCurrentPage) + "page");
                requestData(false);
            }
            //new DataAsyncTask().execute();
        }
    }

    private void setRefresh() {
        mRecyclerViewAdapter.getDataList().clear();
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        mCurrentPage = 1;

    }

    @Override
    public void onItemClick(View view, int postion) {
        UiHelper.showLoginActivity(getContext());
    }
}
