package com.shouchuang.car.datahelper;

import android.util.Log;

import com.shouchuang.car.datahelper.network.SocketHelper;

import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class MoveDataHelper implements SocketHelper.ScocketResponseListener {

    public static final String COMMAND_SUB_STR = "cmd=control&d=";
    public static final String CAR_IP = "192.168.4.1";
    public static final int CONNECT_PORT_LEFT = 8089;

    private SocketHelper mSocketHelper;
    Timer mTimer;

    public MoveDataHelper() {
        mSocketHelper = new SocketHelper(200);
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
        Log.e("SkyTest", "Move Command receive TIMEOUT");
    }


    public void move(Direction leftWheel, Direction rightWheel) {
        int command = (rightWheel.getValue() << 2) & leftWheel.getValue();
        try {
            stop();
            mSocketHelper.setSendData(COMMAND_SUB_STR + command, CAR_IP, CONNECT_PORT_LEFT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mSocketHelper.send();
            }
        }, 500, 500);
    }

    public void stop() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}
