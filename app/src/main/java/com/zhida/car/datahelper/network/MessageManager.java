package com.zhida.car.datahelper.network;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by skylan on 11/11/2017.
 * 消息发送管理类
 * 用来协调各个功能之间发送socket的时间和频率
 */

public class MessageManager {

    private static final String TAG = MessageManager.class.getSimpleName();

    public static final String CONNECT_IP = "192.168.4.1";
    public static final int CONNECT_PORT = 8089;

    private Message mDefaultMessage;
    private Map<String, MessageLooper> mLooperMap;

    private volatile static MessageManager mInstance = null;
    public static MessageManager getInstance() {
        if (mInstance == null) {
            synchronized (SocketHelper.class) {
                if (mInstance == null) {
                    mInstance = new MessageManager();
                }
            }
        }
        return mInstance;
    }

    public MessageManager() {
        mLooperMap = new HashMap<>();
    }

    public Message getDefaultMessage(Message.IMessageResponseListener listener) {
        if (mDefaultMessage == null) {
            mDefaultMessage = new Message(CONNECT_IP, CONNECT_PORT);
            mDefaultMessage.setListener(listener);
        }
        return mDefaultMessage;
    }

    public Message getDefaultMessage() {
        return getDefaultMessage(null);
    }

    public void createMsgLooper(String looperName, int period) {
        mLooperMap.put(looperName, new MessageLooper(CONNECT_IP, CONNECT_PORT, period));
    }

    public MessageLooper getMsgLooper(String looperName)    {
        return mLooperMap.get(looperName);
    }

}
