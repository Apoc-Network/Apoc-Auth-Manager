package com.zhida.car.utils;

import android.util.Log;

import com.zhida.car.constant.Constants;


/**
 * Created by skylan on 11/11/2017.
 */

public class LogUtils {
    public static void e(final String tag, final String msg) {
        if (Constants.DEBUG_MODE) {
            Log.e(tag, msg);
        } else {
            // DO NOTHING
        }
    }
}
