package com.shouchuang.car.datahelper;

import android.os.Message;
import android.util.Log;

import com.shouchuang.car.datahelper.network.SocketHelper;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class MoveDataHelper implements SocketHelper.ScocketResponseListener {

    public static final String COMMAND_SUB_STR = "cmd=control&d=";
    public static final String CAR_IP = "192.168.4.1";
    public static final int CONNECT_PORT_LEFT = 8089;
    public static final int CONNECT_PORT_RIGHT = 8089;

    private SocketHelper mLeftWheelSocketHelper;
    private SocketHelper mRightWheelSocketHelper;

    Timer mTimer_left;
    Timer mTimer_right;

    public MoveDataHelper() {
        mLeftWheelSocketHelper = new SocketHelper(200);
        mLeftWheelSocketHelper.setResponseListener(this);
        mRightWheelSocketHelper = new SocketHelper(200);
        mRightWheelSocketHelper.setResponseListener(this);
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


    public void move(Direction _direction) {
        if (_direction == Direction.LETF_FORWARD
                || _direction == Direction.LEFT_BACKEARD
                || _direction == Direction.LEFT_STOP) {
            try {
                mLeftWheelSocketHelper.setSendData(COMMAND_SUB_STR + _direction.getValue(),
                        CAR_IP, CONNECT_PORT_LEFT);
                cancelLeftSend();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            mTimer_left = new Timer();
            mTimer_left.schedule(new TimerTask() {
                @Override
                public void run() {
                    mLeftWheelSocketHelper.send();
                }
            }, 500, 500);
        } else if (_direction == Direction.RIGHT_FORWARD
                || _direction == Direction.RIGHT_BACKEARD
                || _direction == Direction.RIGHT_STOP) {
            try {
                mRightWheelSocketHelper.setSendData(COMMAND_SUB_STR + _direction.getValue(),
                        CAR_IP, CONNECT_PORT_RIGHT);
                cancelRightSend();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            mTimer_right = new Timer();
            mTimer_right.schedule(new TimerTask() {
                @Override
                public void run() {
                    mRightWheelSocketHelper.send();
                }
            }, 500, 500);
        }
    }

    public void cancelLeftSend() {
        if (mTimer_left != null) {
            mTimer_left.cancel();
        }
    }

    public void cancelRightSend() {
        if (mTimer_right != null)
            mTimer_right.cancel();
    }
}
