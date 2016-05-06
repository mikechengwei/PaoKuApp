package com.example.chengwei.notebook.util;

import android.content.Context;
import android.content.Intent;

import com.example.chengwei.notebook.activity.RenwuInfoActivity;

/**
 * Created by chengwei on 3/11/16.
 */
public class UiHelper {
    public static void showLoginActivity(Context context) {
        Intent intent = new Intent(context, RenwuInfoActivity.class);
        context.startActivity(intent);
    }
}
