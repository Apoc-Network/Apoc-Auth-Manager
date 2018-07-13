package com.shouchuang.car.datahelper.network;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by skylan on 16/12/25.
 */

public class SocketHelper {

    // 定义每个数据报的最大大小为1KB
    private static final int DATA_LEN = 1024;

    // 发送广播端的socket
    private MulticastSocket ms;

    // 定义接收网络数据的字节数组
    byte[] inBuff = new byte[DATA_LEN];

    // 以指定的字节数组创建准备接收数据的DatagramPacket对象
    private DatagramPacket packetIn = new DatagramPacket(inBuff, inBuff.length);

    private DatagramPacket packetOut = null;

    InetAddress address;
}
