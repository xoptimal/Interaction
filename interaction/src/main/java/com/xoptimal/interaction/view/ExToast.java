package com.xoptimal.interaction.view;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Freddie on 2017/8/16 0016 .
 * Description:
 */
public class ExToast {

    private static Toast   sToast;
    private static Context sContext;

    public static void initToast(Context context) {
        sContext = context;
    }

    public static void makeText(String content) {
        if (sToast == null) {
            sToast = Toast.makeText(sContext, content, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(content);
        }
        sToast.show();
    }

    public static void makeText(String content, int duration) {
        if (sToast == null) {
            sToast = Toast.makeText(sContext, content, duration);
        } else {
            sToast.setText(content);
        }
        sToast.show();
    }

}
