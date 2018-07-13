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

    int repeat_command = -1;

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
        int command = (leftWheel.getValue() << 2) ^ rightWheel.getValue();
        if (command == repeat_command) {
            return;
        }
        repeat_command = command;

        try {
            cancelTask();
            // change 10 to 'a'
            mSocketHelper.setSendData(COMMAND_SUB_STR + (command == 10?"a":String.valueOf(command)),
                    CAR_IP, CONNECT_PORT_LEFT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mSocketHelper.send();
            }
        }, 0, 500);
    }

    private void cancelTask() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    public void stop() {
        cancelTask();
    }
}
