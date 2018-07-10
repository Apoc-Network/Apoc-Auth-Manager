package com.zhida.car.datahelper.network;

import android.text.TextUtils;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by skylan on 21/11/2017.
 */

public class MessageLooper {

    private Message mMsg;
    private String mContent = "";

    private Timer mTimer;
    private int mPeriod;

    public MessageLooper(String ip, int port, int period, Message.IMessageResponseListener listener) {
        mMsg = new Message(ip, port);
        mMsg.setListener(listener);
        mPeriod = period;
    }

    public MessageLooper(String ip, int port, int period) {
        this(ip, port, period, null);
    }

    public void send(String content) {
        if (TextUtils.isEmpty(content) || content.equals(mContent)) {
            return;
        } else {
            mContent = content;
            // cancel last TemerTask
            cancelTask();

            mTimer = new Timer();
            // set delay to a non-zero time avoid send message at one time
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mMsg.send(mContent);
                }
            }, 50, mPeriod);

        }
    }

    public void setListener(Message.IMessageResponseListener listener) {
        mMsg.setListener(listener);
    }

    public void cancelTask() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

}
