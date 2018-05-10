package com.xoptimal.interaction.observer;

/**
 * Created by Freddie on 2018/2/13.
 */

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;

import com.xoptimal.interaction.ApiException;
import com.xoptimal.interaction.OnRefreshListener;
import com.xoptimal.interaction.helper.ExSwitchViewHelper;
import com.xoptimal.interaction.view.ExToast;
import com.xoptimal.interaction.view.INetViewGroup;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public abstract class ViewObserver<T> extends ExObserver<T> {

    protected SwipeRefreshLayout mRefreshLayout;
    protected ExSwitchViewHelper mSwitchViewHelper;
    protected boolean            hasLoadDataSuccess;
    protected LoadingWay mLoadWay = LoadingWay.WINDOW;

    public ViewObserver(Activity activity) {
        this(activity, activity.findViewById(android.R.id.content));
    }

    public ViewObserver(Activity activity, View contentView) {
        traverseView(contentView);
        mSwitchViewHelper = new ExSwitchViewHelper(activity, contentView);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (mLoadWay == LoadingWay.DROP && mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(true);
        } else {
            mSwitchViewHelper.showLoading();
        }
    }

    @Override
    public void onComplete() {
        mSwitchViewHelper.restore();
        resetRefreshStatus();
    }

    @Override
    public void onNext(T t) {
        if (t == null) {
            mSwitchViewHelper.showEmpty();
        } else {
            accept(t);
        }
    }

    public abstract void accept(T t);

    @Override
    public void onError(@NonNull Throwable e) {
        if (e instanceof ApiException) {
            switch (((ApiException) e).getStatus()) {
                case ERROR:
                    if (hasLoadDataSuccess) {
                        ExToast.makeText(e.getMessage());
                    } else {
                        mSwitchViewHelper.showFailure(e);
                    }
                    break;
                case JURISDICTION:
                    mSwitchViewHelper.showLogin(e);
                    break;
            }
        }
        resetRefreshStatus();
    }

    private void resetRefreshStatus() {
        if (mLoadWay == LoadingWay.DROP && mRefreshLayout != null && mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        } else {
            mSwitchViewHelper.dismiss();
        }
        isLoading = false;
    }

    protected void traverseView(View view) {
        if (view instanceof SwipeRefreshLayout) {
            mRefreshLayout = (SwipeRefreshLayout) view;
        }
        if (mRefreshLayout == null && view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0, count = viewGroup.getChildCount(); i < count; i++) {
                if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                    traverseView(viewGroup.getChildAt(i));
                }
            }
        }
    }

    public void setOnRefreshListener(final OnRefreshListener listener) {
        if (mSwitchViewHelper != null) {
            mSwitchViewHelper.setOnRefreshListener(listener);
        }
        if (mRefreshLayout != null) {
            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mRefreshLayout.setRefreshing(true);
                    mLoadWay = LoadingWay.DROP;
                    listener.onRefresh();
                }
            });
        }
    }

    public ViewObserver setNetViewGroup(INetViewGroup netViewGroup) {
        mSwitchViewHelper.setNetViewGroup(netViewGroup);
        return this;
    }

    public void setLoadingWay(LoadingWay loadWay) {
        mLoadWay = loadWay;
    }

    public enum LoadingWay {
        WINDOW, DROP
    }

    public ExSwitchViewHelper getSwitchViewHelper() {
        return mSwitchViewHelper;
    }
}
