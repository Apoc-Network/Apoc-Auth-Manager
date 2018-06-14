package com.shouchuang.car.datahelper.network;

import android.text.TextUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by skylan on 16/12/25.
 */

public class SocketHelper {

    public interface ScocketResponseListener {
        void receiveSucceed(String data);
        void receiveError();
    }

    private static final int DATA_LEN = 1024;

    public ScocketResponseListener mResponseListener = null;

    public void setResponseListener(ScocketResponseListener responseListener) {
        this.mResponseListener = responseListener;
    }

    private MulticastSocket mSocket;

    private byte[] mInBuff = new byte[DATA_LEN];
    private byte[] mOutBuff;
    private String mResult;

    private DatagramPacket mInPacket = new DatagramPacket(mInBuff, mInBuff.length);
    private DatagramPacket mOutPacket = null;

    private InetAddress mAddress;

    public void setSendData(String commandStr, String address, int port) throws UnknownHostException {
        mOutBuff = commandStr.getBytes();
        this.mAddress = InetAddress.getByName(address);
        this.mOutPacket = new DatagramPacket(mOutBuff, mOutBuff.length, mAddress, port);
    }

    public void creatSocket(int timeOut) throws IOException {
        mSocket = new MulticastSocket();
        mSocket.setSoTimeout(timeOut);
    }

    public void send() throws IOException {
        if (mOutPacket != null) {
            mSocket.send(mOutPacket);
        }
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
            mResponseListener.receiveError();
        }
    }

}
