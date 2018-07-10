package com.zhida.car.datahelper;

import com.zhida.car.component.Motor;
import com.zhida.car.datahelper.network.MessageLooper;
import com.zhida.car.datahelper.network.MessageManager;

public class MoveDataHelper extends BaseDataHelper {

    public static final String ACTION_LOOPER_NAME = "action";

    public static final String COMMAND_SUB_STR = "cmd=control&d=";

    private MessageLooper mLooper;

    public MoveDataHelper() {
        MessageManager.getInstance().createMsgLooper(ACTION_LOOPER_NAME, 500);
        mLooper = MessageManager.getInstance().getMsgLooper(ACTION_LOOPER_NAME);
        mLooper.setListener(this);
    }

    public void move(Motor leftWheel, Motor rightWheel) {
        // combine left wheel command and right wheel command into one
        int command = (leftWheel.getAction() << 4) | rightWheel.getAction();
        // change 10 to 'a'
        String commandStr = COMMAND_SUB_STR + (command == 10 ? "a" : String.valueOf(command));
        mLooper.send(commandStr);
    }

    public void stop() {
        mLooper.cancelTask();
    }

    @Override
    public void onMessageReceivrSucceed(String data) {
        super.onMessageReceivrSucceed(data);
    }

    @Override
    public void onMessageReceivrFailed(String data) {
        super.onMessageReceivrFailed(data);
    }
}
