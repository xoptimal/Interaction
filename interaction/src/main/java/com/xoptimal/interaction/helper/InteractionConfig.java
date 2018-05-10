package com.xoptimal.interaction.helper;

import android.content.Context;

import com.xoptimal.interaction.entity.IResult;
import com.xoptimal.interaction.view.ExToast;


/**
 * Created by Freddie on 2018/1/11 0011 .
 * Description:
 */
public class InteractionConfig {

    public static InteractionConfig sConfig;

    private Configuration mConfiguration;

    private InteractionConfig() {}

    public InteractionConfig(Configuration configuration) {
        mConfiguration = configuration;
    }

    public static void init(Context context, Configuration configuration) {
        sConfig = new InteractionConfig(configuration);
        //  初始化 Toast
        ExToast.initToast(context);
    }

    public interface Configuration<T extends IResult> {
        Status formatData(T t);
    }

    public enum Status {
        SUCCESS, NOT_DATA, FAILURE, NOT_LOGIN,
    }

    public Configuration getConfiguration() {
        return mConfiguration;
    }
}
