package com.zhida.car.datahelper.network;

import com.zhida.car.utils.LogUtils;

import java.net.UnknownHostException;

/**
 * Created by skylan on 21/11/2017.
 */

public class Message implements SocketHelper.ScocketResponseListener {

    public static final String TAG = Message.class.getSimpleName();

    public interface IMessageResponseListener {
        void onMessageReceivrSucceed(String data);

        void onMessageReceivrFailed(String data);
    }

    private SocketHelper mSocketHelper;
    private IMessageResponseListener mListener;

    public Message(String ip, int port) {
        try {
            mSocketHelper = new SocketHelper(this);
            mSocketHelper.setHostInfo(ip, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) {
        mSocketHelper.setSendData(msg);
        mSocketHelper.send();
    }

    public void setListener(IMessageResponseListener listener) {
        this.mListener = listener;
    }

    @Override
    public void receiveSucceed(String data) {
        LogUtils.e(TAG, "Receive data: " + data);
        if (mListener != null) {
            mListener.onMessageReceivrSucceed(data);
        }
    }

    @Override
    public void receiveError(String errorMsg) {
        LogUtils.e(TAG, "Receive error: " + errorMsg);
        if (mListener != null) {
            mListener.onMessageReceivrFailed(errorMsg);
        }
    }
}
