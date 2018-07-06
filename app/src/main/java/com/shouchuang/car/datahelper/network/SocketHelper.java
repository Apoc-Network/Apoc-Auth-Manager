package com.shouchuang.car.datahelper.network;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by skylan on 16/12/25.
 */

public class SocketHelper {

    public SocketHelper(int _timeOut) {
        try {
            mSocket = new MulticastSocket();
            mSocket.setSoTimeout(_timeOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface ScocketResponseListener {
        void receiveSucceed(String data);

        void receiveError();
    }

    private static final int DATA_LEN = 1024;

    public ScocketResponseListener mResponseListener = null;

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

    public void setResponseListener(ScocketResponseListener responseListener) {
        this.mResponseListener = responseListener;
    }

    public void send() {
        if (mOutPacket != null && mSocket != null) {
            try {
                Log.e("SkyTest", "Command :" + new String(mOutPacket.getData(), 0, mOutPacket.getLength()));
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
                    mResponseListener.receiveError();
                }
            } catch (IOException e) {
                mResponseListener.receiveError();
            }
        } else {
            Log.e("SkyTest", "Please init packet and socket");
            mResponseListener.receiveError();
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
