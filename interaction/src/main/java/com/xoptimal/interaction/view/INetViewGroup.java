package com.xoptimal.interaction.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.xoptimal.interaction.OnRefreshListener;


/**
 * Created by Freddie on 2017/9/6 0006 .
 * Description:
 */
public interface INetViewGroup {

    AlertDialog onCreateLoadingDialog(Context context);

    View onCreateEmptyView(Context context, OnRefreshListener listener);

    View onCreateErrorView(Context context, OnRefreshListener listener, Throwable e);

    View onCreateLoginView(Context context, Throwable e);

}

