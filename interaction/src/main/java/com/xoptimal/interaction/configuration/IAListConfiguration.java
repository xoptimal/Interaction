package com.xoptimal.interaction.configuration;


import com.xoptimal.interaction.model.NetListModel;
import com.xoptimal.interaction.observer.ListObserver;
import com.xoptimal.rcvhelper.RcvHelper;

/**
 * Created by Freddie on 2018/2/8 0008 .
 * Description:
 */
public interface IAListConfiguration extends IAConfiguration {

    NetListModel getModel();

    ListObserver getObserver();

    RcvHelper getRcvHelper();
}

