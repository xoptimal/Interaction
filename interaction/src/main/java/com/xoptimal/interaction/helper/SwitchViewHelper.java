package com.xoptimal.interaction.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Freddie on 2017/7/11 0011 .
 * Description: 界面切换帮助类
 */
public class SwitchViewHelper {

    protected Activity               mContext;
    protected ViewGroup              mParentView;
    protected View                   mContentView;
    protected int                    mContentViewIndex;
    protected ViewGroup.LayoutParams mContentViewParams;

    public SwitchViewHelper(Activity activity) {
        mContext = activity;
        ViewGroup parentView = activity.findViewById(android.R.id.content);
        mContentView = parentView.getChildAt(parentView.getChildCount() - 1);
        init();
    }

    public SwitchViewHelper(Fragment fragment) {
        mContext = fragment.getActivity();
        mContentView = fragment.getView();
        init();
    }

    public SwitchViewHelper(Activity activity, View contentView) {
        mContext = activity;
        mContentView = contentView;
        init();
    }

    private void init() {

        mContentViewParams = mContentView.getLayoutParams();
        if (mContentViewParams == null) {
            mContentViewParams = new ViewGroup.LayoutParams(-1, -1);
        }

        if (mContentView.getParent() == null) {
            throw new RuntimeException("ContentView obtain ParentView failure, check ContentView. ");
        } else {
            mParentView = (ViewGroup) mContentView.getParent();
        }

        for (int index = 0, count = mParentView.getChildCount(); index < count; index++) {
            if (mContentView == mParentView.getChildAt(index)) {
                mContentViewIndex = index;
                break;
            }
        }
    }

    public void setView(View view) {
        if (mParentView.getChildAt(mContentViewIndex) != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            mParentView.removeViewAt(mContentViewIndex);
            mParentView.addView(view, mContentViewIndex, mContentViewParams);
        }
    }

    public void restore() {
        if (mParentView.getChildAt(mContentViewIndex) != mContentView) {
            setView(mContentView);
        }

    }

    public View getContentView() {
        return mContentView;
    }

    public Context getContext() {
        return mContext;
    }
}
