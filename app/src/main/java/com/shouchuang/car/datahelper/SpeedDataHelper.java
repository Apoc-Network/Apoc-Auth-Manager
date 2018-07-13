package com.shouchuang.car.datahelper;

import android.os.Message;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by skylan on 16/12/25.
 */

public class SpeedDataHelper {
//
//
//    public void getspeed(String str) {
//        String str1 = "cmd=control&d=" + str;
//        byte[] data = str1.getBytes();
//        try {
//            address2 = InetAddress.getByName(str_ip);
//            dataPacket2 = new DatagramPacket(data, data.length, address2, 8089);
//        } catch (UnknownHostException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        new Thread() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                super.run();
//                try {
//                    /* 创建socket实例 */
//                    ms2 = new MulticastSocket();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                int i = 0;
//                while (i < 5) {
//                    try {
//                        sleep(200);
//                    } catch (InterruptedException e1) {
//                        // TODO Auto-generated catch block
//                        e1.printStackTrace();
//                    }
//
//                    try {
//                        ms2.setSoTimeout(4);
//                        ms2.send(dataPacket2);
//                        // 读取Socket中的数据，读到的数据放在inPacket所封装的字节数组中
//                        ms2.receive(inPacket2);
//                        System.out.println(new String(inBuff2, 0, inPacket2
//                                .getLength()));
//
//                        if (inPacket2.getLength() > 0) {
//                            str_udp3 = new String(inBuff2, 0, inPacket2.getLength());
//                            break;
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    i++;
//                }
//
//                ms2.close();
//                if (str_udp3 == "") {
//                    Message msg = new Message();
//                    msg.what = 4;
//                    handler.sendMessage(msg);
//                } else {
//                    str_speed = str_udp3.substring(str_udp3.lastIndexOf("=") + 1);
//                    Message msg = new Message();
//                    msg.what = 5;
//                    handler.sendMessage(msg);
//                }
//            }
//
//        }.start();
//    }


}
