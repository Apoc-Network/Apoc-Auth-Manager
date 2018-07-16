package com.zhida.car.datahelper;

import com.zhida.car.datahelper.network.Message;
import com.zhida.car.datahelper.network.MessageManager;

/**
 * Created by skylan on 16/12/25.
 */

public class ConnectDataHelper extends BaseDataHelper {

    public static final String COMMAND_STR = "cmd=ping";

    private boolean mHasConnected = false;
    private Message mMsg;

    public ConnectDataHelper() {
        mMsg = MessageManager.getInstance().getDefaultMessage(ConnectDataHelper.this);
    }

    public void connectCar() {
        // 发送的数据包，局网内的所有地址都可以收到该数据包
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 6 && !mHasConnected; i++) {
                    mMsg.send(COMMAND_STR);
                }
            }
        }.start();
    }

    @Override
    public void onMessageReceivrFailed(String data) {
        super.onMessageReceivrFailed(data);
    }

    @Override
    public void onMessageReceivrSucceed(String data) {
        super.onMessageReceivrSucceed(data);
    }
}
