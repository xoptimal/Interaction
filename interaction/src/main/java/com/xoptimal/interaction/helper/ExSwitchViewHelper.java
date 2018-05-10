package com.xoptimal.interaction.helper;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.xoptimal.interaction.OnRefreshListener;
import com.xoptimal.interaction.view.INetViewGroup;
import com.xoptimal.interaction.view.impl.NetViewGroup;


/**
 * Created by Freddie on 2017/10/16 .
 * Description:
 */
public class ExSwitchViewHelper extends SwitchViewHelper {

    private INetViewGroup     mNetViewGroup;
    private OnRefreshListener mListener;

    public ExSwitchViewHelper(Activity activity, View contentView) {
        super(activity, contentView);
        mNetViewGroup = new NetViewGroup();
    }

    public ExSwitchViewHelper(Activity activity, View contentView, INetViewGroup viewGroup) {
        super(activity, contentView);
        mNetViewGroup = viewGroup;
    }

    public void setNetViewGroup(INetViewGroup netViewGroup) {
        mNetViewGroup = netViewGroup;
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    private AlertDialog mLoadingDialog;

    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = mNetViewGroup.onCreateLoadingDialog(mContext);
        }
        mLoadingDialog.show();
    }

    public boolean isLoading() {
        return mLoadingDialog != null && mLoadingDialog.isShowing();
    }

    public void showEmpty() {
        dismiss();
        setView(mNetViewGroup.onCreateEmptyView(mContext, mListener));
    }

    public void showLogin(Throwable e) {
        dismiss();
        setView(mNetViewGroup.onCreateLoginView(mContext, e));
    }

    public void showFailure(Throwable e) {
        dismiss();
        setView(mNetViewGroup.onCreateErrorView(mContext, mListener, e));
    }

    @Override
    public void restore() {
        super.restore();
        dismiss();
    }

    public void dismiss() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
