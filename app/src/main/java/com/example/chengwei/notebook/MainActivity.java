package com.example.chengwei.notebook;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chengwei.notebook.R;
import com.example.chengwei.notebook.bean.MainTab;
import com.example.chengwei.notebook.fragment.AllFragment;
import com.example.chengwei.notebook.widget.MyFragmentTabHost;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chengwei on 3/5/16.
 */
public class MainActivity extends ActionBarActivity implements
        View.OnTouchListener ,View.OnClickListener ,AllFragment.BackHandlerInterface,TabHost.OnTabChangeListener {
    @Bind(R.id.menu_query_all)
    View menu_query_all;
    @Bind(R.id.menu_query_post)
    View menu_query_post;
    @Bind(R.id.menu_query_kuaidi)
    View menu_query_kuaidi;
    @Bind(R.id.menu_query_zawu)
    View menu_query_zawu;
    @Bind(R.id.menu_query_special)
    View menu_query_special;
    @Bind(R.id.tabhost)
    MyFragmentTabHost mTabHost;

    private SlidingMenu menu;
    private AllFragment allFragment;
    private FragmentTransaction ft;//fragment管理的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建一个视图
        setContentView(R.layout.activity_main);
        //绑定视图

        // setSupportActionBar(toolbar);
        initActionBar();
        initSlidingMenu();
        ButterKnife.bind(this);
        initView();
        initTabs();
    }
    public void initSlidingMenu(){
        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */

        //为侧滑菜单设置布局
        menu.setMenu(R.layout.fragment_navigation_drawer_items);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
    }
    public  void initActionBar(){

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);//显示home区域
        actionBar.setDisplayHomeAsUpEnabled(true);//显示返回图片
        actionBar.setHomeAsUpIndicator(R.drawable.icon_sikuai_16);//设置返回图标
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>跑镖 </font>"));
        actionBar.setElevation(0);
    }

    public void initView(){
        mTabHost.setOnTabChangedListener(this);

        menu_query_all.setOnClickListener(this);
        menu_query_special.setOnClickListener(this);
        menu_query_zawu.setOnClickListener(this);
        menu_query_post.setOnClickListener(this);
        menu_query_kuaidi.setOnClickListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home://左侧home区域
                menu.toggle();//交替显示或隐藏侧边栏

                break;

        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        super.onTouchEvent(event);
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {

        }
        return consumed;
    }
    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.menu_query_all:

                break;
            case R.id.menu_query_post:

                break;
            case R.id.menu_query_kuaidi:

                break;
            case R.id.menu_query_zawu:


                break;
            case R.id.menu_query_special:

                break;
            default:
                break;

        }
        menu.postDelayed(new Runnable() {

            @Override
            public void run() {
                menu.toggle();
            }
        }, 800);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //对fragmentTab进行初始化
    private void initTabs() {
        mTabHost.setup(this, getSupportFragmentManager(), R.id.content_main);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        MainTab[] tabs = MainTab.values();//获取tab信息
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {//依次将tab加入到fragmentTabHost
            MainTab mainTab = tabs[i];
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));//创建选项卡 传入tag  作唯一标识
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable drawable = this.getResources().getDrawable(
                    mainTab.getResIcon());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
                    null);

            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {

                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            //这里应该是给加fragment的
            mTabHost.addTab(tab, mainTab.getClz(), null);
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
    }
    @Override
    public void setSelectedFragment(AllFragment backHandledFragment) {
        this.allFragment = backHandledFragment;
    }


    long exitTime=0;// 退出时间
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 按两次返回键退出应用程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 判断间隔时间 大于2秒就退出应用
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                // 应用名
                String applicationName = getResources().getString(
                        R.string.app_name);
                String msg = "再按一次返回键退出" + applicationName;
                //String msg1 = "再按一次返回键回到桌面";
                Toast.makeText(MainActivity.this, msg, 0).show();
                // 计算两次返回键按下的时间差
                exitTime = System.currentTimeMillis();
            } else {
                allFragment.onBackPressed();
                // 关闭应用程序
                finish();
                // 返回桌面操作
                // Intent home = new Intent(Intent.ACTION_MAIN);
                // home.addCategory(Intent.CATEGORY_HOME);
                // startActivity(home);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onTabChanged(String tabId) {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }

        supportInvalidateOptionsMenu();
    }
}
