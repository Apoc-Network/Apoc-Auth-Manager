package com.shouchuang.car.datahelper;

import android.os.Message;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by skylan on 16/12/25.
 */

public class DirectionDataHelper {

    public void send(String str) {
        String str1 = "cmd=control&d=" + str;
        byte[] data = str1.getBytes();
        try {
            address1 = InetAddress.getByName(str_ip);
            dataPacket1 = new DatagramPacket(data, data.length, address1, 8089);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
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
