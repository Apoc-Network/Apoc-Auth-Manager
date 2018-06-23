package com.shouchuang.car.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;

/**
 * Created by skylan on 16/12/25.
 */

public class WifiManager {

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager mConnectivityManager =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isAvailable();
        }

        return false;
    }

    public static String getConnectWifiSsid(Context context) {
        android.net.wifi.WifiManager wifiManager =
                (android.net.wifi.WifiManager) context.getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getSSID();
    }
}
