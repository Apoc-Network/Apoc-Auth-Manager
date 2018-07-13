package com.shouchuang.car.datahelper;

import android.os.Message;
import android.util.Log;

import com.shouchuang.car.datahelper.network.SocketHelper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by skylan on 16/12/25.
 */

public class MoveDataHelper implements SocketHelper.ScocketResponseListener{

    public static final String COMMAND_SUB_STR = "cmd=control&d=";
    public static final String MASK_IP = "192.168.4.1";
    public static final int CONNECT_PORT = 8089;

    private SocketHelper mSocketHelper;

    public MoveDataHelper() {
        mSocketHelper = new SocketHelper();
        mSocketHelper.setResponseListener(this);
    }

    public void connectCar() {
        try {
            mSocketHelper.setSendData(COMMAND_SUB_STR, MASK_IP, CONNECT_PORT);
            mSocketHelper.creatSocket(1000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 发送的数据包，局网内的所有地址都可以收到该数据包
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 6 && !mHasConnected; i++) {
                    try {
                        mSocketHelper.send();
                    } catch (IOException e) {
                        Log.e("SkyTest", "Receive Timeout!!");
                    }
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


    public void sendDirection(String str) {

        try {
            mSocketHelper.setSendData(COMMAND_STR, MASK_IP, CONNECT_PORT);
            mSocketHelper.creatSocket(500);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
                try {
                    /* 创建socket实例 */
                    ms1 = new MulticastSocket();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    ms1.setSoTimeout(4);
                    ms1.send(dataPacket1);
                    // 读取Socket中的数据，读到的数据放在inPacket所封装的字节数组中
                    ms1.receive(inPacket1);
                    Log.e("msg_receive_skt1", " " + new String(inBuff1, 0, inPacket1.getLength()));
                    str_udp2 = new String(inBuff1, 0, inPacket1.getLength());
                    Log.e("msg_receive_skt2", " " + str_udp2);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ms1.close();
                if (str_udp2 == "ok") {
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.what = 3;
                    handler.sendMessage(msg);
                }
            }

        }.start();
    }
}
