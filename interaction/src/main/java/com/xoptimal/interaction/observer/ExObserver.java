package com.xoptimal.interaction.observer;

import com.orhanobut.logger.Logger;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Freddie on 2018/2/13.
 */

public abstract class ExObserver<T> implements Observer<T> {

    protected Disposable mDisposable;
    protected boolean    isLoading;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mDisposable = d;
    }

    @Override
    public void onError(@NonNull Throwable e) {
        isLoading = false;
    }

    @Override
    public void onComplete() {
        isLoading = false;
    }

    public boolean isLoading() {
        return isLoading;
    }

}
