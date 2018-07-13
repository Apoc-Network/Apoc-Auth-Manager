package com.shouchuang.car.datahelper;

import android.os.Message;

import com.shouchuang.car.R;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by skylan on 16/12/25.
 */

public class ConnectDataHelper {

    public void connectcar() {

        byte[] data = "cmd=ping".getBytes();
        try {
            address = InetAddress.getByName("255.255.255.255");
            dataPacket = new DatagramPacket(data, data.length, address,
                    8089);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 发送的数据包，局网内的所有地址都可以收到该数据包
        new Thread() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                super.run();
                int i = 0;
                str_udp1 = null;
                try {
                    /* 创建socket实例 */
                    ms = new MulticastSocket();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                while (i < 6) {
                    try {
                        sleep(300);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    try {
                        // ms.setTimeToLive(1);
                        ms.setSoTimeout(1);
                        ms.send(dataPacket);
                        System.out.println(i);
                        // 读取Socket中的数据，读到的数据放在inPacket所封装的字节数组中
                        ms.receive(inPacket);
                        System.out.println(new String(inBuff, 0, inPacket
                                .getLength()));
                        if (inPacket.getLength() > 0) {
                            str_udp1 = new String(inBuff, 0,
                                    inPacket.getLength());
                            break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("error");
                    }
                    i++;
                }
                System.out.println("end");
                ms.close();
                if (str_udp1 == null) {
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            }

        }.start();

    }

}
