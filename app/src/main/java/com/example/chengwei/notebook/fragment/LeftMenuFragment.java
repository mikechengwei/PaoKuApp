package com.example.chengwei.notebook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chengwei.notebook.R;

/**
 * Created by chengwei on 3/5/16.
 */
public class LeftMenuFragment extends Fragment {

    private View self;

    private static LeftMenuFragment instance;



    public static LeftMenuFragment getInstance() {
        if (instance == null) instance = new LeftMenuFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.self == null) {
            this.self = inflater.inflate(R.layout.fragment_navigation_drawer_items, null);
        }
        if (this.self.getParent() != null) {
            ViewGroup parent = (ViewGroup) this.self.getParent();
            parent.removeView(this.self);
        }
        return this.self;
    }
}

