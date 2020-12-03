package com.tezsol.bagex.custom.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.tezsol.bagex.R;

/**
 *
 */

public class CustomTab {

    public static View getTabView(Context context, String tab_title, boolean isBadgeReq) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        view.findViewById(R.id.txt_tab_badge).setVisibility(isBadgeReq ? View.VISIBLE : View.GONE);
        ((TextView) view.findViewById(R.id.txt_tab)).setText(tab_title);
        return view;
    }

    public static void setTabMode(Context context, TabLayout tabLayout) {
        if (context.getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        } else {
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
//
    }
}
