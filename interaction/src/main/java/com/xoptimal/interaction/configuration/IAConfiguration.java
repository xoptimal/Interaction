package com.xoptimal.interaction.configuration;

import com.xoptimal.interaction.entity.IResult;
import com.xoptimal.interaction.observer.ExObserver;


/**
 * Created by Freddie on 2018/2/7 0007 .
 * Description:
 */
public interface IAConfiguration<T extends IResult> {

    ExObserver<T> getObserver();
}
