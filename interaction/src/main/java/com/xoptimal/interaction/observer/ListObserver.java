package com.xoptimal.interaction.observer;

import android.app.Activity;
import android.view.View;

import com.xoptimal.interaction.entity.ListResult;
import com.xoptimal.rcvhelper.RcvHelper;
import com.xoptimal.rcvhelper.entity.FDLoadMore;
import com.xoptimal.rcvhelper.view.INetViewGroup;


/**
 * Created by Freddie on 2018/2/13.
 */

public class ListObserver<T extends ListResult> extends ViewObserver<T> implements RcvHelper.OnScrollListener {

    private RcvHelper mRcvHelper;
    private INetViewGroup.OnNetListener mOnNetListener;

    public ListObserver(Activity activity) {
        super(activity);
    }

    public ListObserver(Activity activity, View contentView) {
        super(activity, contentView);
    }

    public void setRcvHelper(RcvHelper helper) {
        mRcvHelper = helper;
        mRcvHelper.setOnScrollListener(this);
        mRcvHelper.setOnNetListener(mOnNetListener);
    }

    private boolean isLoadMore;

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    @Override
    public void accept(T t) {
        if (isLoadMore) {
            mRcvHelper.addAll(t.getLists());
        } else {
            mRcvHelper.replaceAll(t.getLists());
        }
    }

    @Override
    public void onError(Throwable e) {
        if (isLoadMore) {
            mRcvHelper.initLoadMoreView(FDLoadMore.Status.ERROR);
        } else {
            super.onError(e);
        }
    }

    @Override
    public void onScrollUp(boolean isScrollUp) {
        if (mRefreshLayout != null) mRefreshLayout.setEnabled(isScrollUp);
    }

    @Override
    public void onScrollBottom() {
    }

    public void setOnNetListener(INetViewGroup.OnNetListener listener) {
        mOnNetListener = listener;
    }

}
