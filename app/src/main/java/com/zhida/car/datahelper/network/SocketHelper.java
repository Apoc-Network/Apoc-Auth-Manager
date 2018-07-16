package com.zhida.car.datahelper.network;

import android.text.TextUtils;

import com.zhida.car.utils.LogUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by skylan on 16/12/25.
 */

public class SocketHelper {

    private static final String TAG = SocketHelper.class.getSimpleName();

    private static final int TIME_OUT = 200;
    private static final int DATA_LEN = 1024;

    public interface ScocketResponseListener {
        void receiveSucceed(String data);
        void receiveError(String errorMsg);
    }

    public ScocketResponseListener mResponseListener = null;

    private MulticastSocket mSocket;

    public SocketHelper(ScocketResponseListener responseListener) {
        this.mResponseListener = responseListener;
        try {
            mSocket = new MulticastSocket();
            mSocket.setSoTimeout(TIME_OUT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] mInBuff = new byte[DATA_LEN];
    private byte[] mOutBuff;
    private String mResult;

    private DatagramPacket mInPacket = new DatagramPacket(mInBuff, mInBuff.length);
    private DatagramPacket mOutPacket = null;

    private InetAddress mAddress;
    private int mPort;

    public void setHostInfo(String address, int port) throws UnknownHostException {
        mAddress = InetAddress.getByName(address);
        mPort = port;
    }

    public void setSendData(String commandStr) {
        mOutBuff = commandStr.getBytes();
        mOutPacket = new DatagramPacket(mOutBuff, mOutBuff.length, mAddress, mPort);
    }

    public void send() {
        if (mOutPacket != null && mSocket != null) {
            try {
                LogUtils.e(TAG, "Command :" + new String(mOutPacket.getData(), 0, mOutPacket.getLength()));
                mSocket.send(mOutPacket);
                mSocket.receive(mInPacket);
                int dataLegth = mInPacket.getLength();
                if (dataLegth > 0) {
                    mResult = new String(mInBuff, 0, dataLegth);
                    if (!TextUtils.isEmpty(mResult)) {
                        mResponseListener.receiveSucceed(mResult);
                    } else {
                        mResponseListener.receiveSucceed("");
                    }
                } else {
                    mResponseListener.receiveError("Data size less than 0!");
                }
            } catch (IOException e) {
                mResponseListener.receiveError(e.getMessage());
            }
        } else {
            mResponseListener.receiveError("Please init packet and socket");
        }
    }

    public void release() {
        mSocket.close();
        mInPacket = null;
        mOutPacket = null;
        mInBuff = null;
        mOutBuff = null;
    }

}
