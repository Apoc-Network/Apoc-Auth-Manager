package com.shouchuang.car.datahelper;

import android.util.Log;
import android.util.StateSet;

import com.shouchuang.car.datahelper.network.SocketHelper;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by skylan on 16/12/25.
 */

public class ConnectDataHelper implements SocketHelper.ScocketResponseListener {

    public static final String COMMAND_STR = "cmd=ping";
    public static final String MASK_IP = "255.255.255.255";
    public static final int CONNECT_PORT = 8089;

    private SocketHelper mSocketHelper;
    private boolean mHasConnected = false;

    public ConnectDataHelper() {
        mSocketHelper = new SocketHelper(1000);
        mSocketHelper.setResponseListener(this);
        try {
            mSocketHelper.setSendData(COMMAND_STR, MASK_IP, CONNECT_PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void connectCar() {
        // 发送的数据包，局网内的所有地址都可以收到该数据包
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 6 && !mHasConnected; i++) {
                    mSocketHelper.send();
                }
            }
        }.start();
    }

    @Override
    public void receiveSucceed(String data) {
        Log.e("SkyTest", "Connect Car Result:" + data);
        mHasConnected = true;
    }

    @Override
    public void receiveError() {
        Log.e("SkyTest", "Connect Car ERROR!!!");
    }

}
