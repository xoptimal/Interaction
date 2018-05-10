package com.xoptimal.interaction;


import com.xoptimal.interaction.configuration.IAConfiguration;
import com.xoptimal.interaction.configuration.IAListConfiguration;
import com.xoptimal.interaction.configuration.INetAConfiguration;
import com.xoptimal.interaction.observer.ExObserver;
import com.xoptimal.interaction.observer.ListObserver;
import com.xoptimal.interaction.observer.ViewObserver;
import com.xoptimal.rcvhelper.view.INetViewGroup;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Freddie on 2018/2/8 0008 .
 * Description:
 */
public class Interaction implements OnRefreshListener, INetViewGroup.OnNetListener {

    private IAConfiguration mConfiguration;
    private ExObserver      mObserver;

    public Interaction(IAConfiguration configuration) {
        mConfiguration = configuration;
        initObserver();
    }

    private void initObserver() {
        mObserver = mConfiguration.getObserver();

        if (mObserver instanceof ViewObserver) {
            ((ViewObserver) mObserver).setOnRefreshListener(this);
        }

        if (mObserver instanceof ListObserver) {
            ((ListObserver) mObserver).setOnNetListener(this);

            if (mConfiguration instanceof IAListConfiguration) {
                ((ListObserver) mObserver).setRcvHelper(((IAListConfiguration) mConfiguration).getRcvHelper());
            }
        }
    }

    @Override
    public void onRefresh() {

        if (mObserver.isLoading()) return;

        if (mConfiguration instanceof IAListConfiguration) {

            ((ListObserver)mObserver).setLoadMore(false);

            ((IAListConfiguration) mConfiguration).getModel().onRefresh()
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);

        } else if (mConfiguration instanceof INetAConfiguration) {
            ((IAListConfiguration) mConfiguration).getModel().onRefresh()
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);

        } else {
            throw new RuntimeException("error");
        }
    }


    @Override
    public void onLoadMore() {
        if (mObserver.isLoading()) return;
        if (mConfiguration instanceof IAListConfiguration) {

            ((ListObserver)mObserver).setLoadMore(true);

            ((IAListConfiguration) mConfiguration).getModel().onLoadMore()
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mObserver);
        }
    }
}
