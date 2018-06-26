package com.shouchuang.car.datahelper;

import android.os.Message;
import android.util.Log;

import com.shouchuang.car.datahelper.network.SocketHelper;

import java.io.IOException;
import java.net.UnknownHostException;

public class MoveDataHelper implements SocketHelper.ScocketResponseListener {

    public static final String COMMAND_SUB_STR = "cmd=control&d=";
    public static final String MASK_IP = "192.168.4.1";
    public static final int CONNECT_PORT = 8089;

    private SocketHelper mSocketHelper;

    public MoveDataHelper() {
        mSocketHelper = new SocketHelper();
        mSocketHelper.setResponseListener(this);
    }

    @Override
    public void receiveSucceed(String data) {
        Log.e("SkyTest", "Move Action" + data);
//        if (str_udp2 == "ok") {
//            Message msg = new Message();
//            msg.what = 2;
//            handler.sendMessage(msg);
//        } else {
//            Message msg = new Message();
//            msg.what = 3;
//            handler.sendMessage(msg);
//        }
    }

    @Override
    public void receiveError() {
        Log.e("SkyTest", "Connect Car ERROR!!!");
    }


    public void sendDirection(Direction _direction) {

        try {
            mSocketHelper.setSendData(COMMAND_SUB_STR + _direction.getValue(), MASK_IP, CONNECT_PORT);
            mSocketHelper.creatSocket(4);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    mSocketHelper.send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
