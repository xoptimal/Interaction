package com.xoptimal.interaction.view.impl;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xoptimal.interaction.OnRefreshListener;
import com.xoptimal.interaction.view.INetViewGroup;


/**
 * Created by Freddie on 2017/8/29.
 */

public class NetViewGroup implements INetViewGroup {

    @Override
    public AlertDialog onCreateLoadingDialog(Context context) {
        return null;
    }

    @Override
    public View onCreateEmptyView(Context context, OnRefreshListener listener) {
        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        layout.setGravity(Gravity.CENTER);
        layout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        TextView textView = new TextView(context);
        textView.setText("Data is Null");
        layout.addView(textView);
        return layout;
    }

    @Override
    public View onCreateErrorView(Context context, final OnRefreshListener listener, Throwable e) {
        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));

        Button button = new Button(context);
        button.setText("OnClick Retry");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRefresh();
            }
        });
        layout.addView(button, -2, -2);

        TextView textView = new TextView(context);
        textView.setText(e.getMessage());
        layout.addView(textView, -2, -2);

        return layout;
    }

    @Override
    public View onCreateLoginView(Context context, Throwable e) {
        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        layout.setGravity(Gravity.CENTER);
        layout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        TextView textView = new TextView(context);
        textView.setText("You Need Login");
        layout.addView(textView);
        return layout;
    }
}
