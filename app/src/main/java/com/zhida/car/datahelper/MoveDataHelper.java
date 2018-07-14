package com.zhida.car.datahelper;

import com.zhida.car.component.Motor;
import com.zhida.car.constant.Constants;
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
        String commandStr;
        if (Constants.DEBUG_MODE) {
            int command = (leftWheel.getDirection().getValue() << 2) ^ rightWheel.getDirection().getValue();
            // change 10 to 'a'
            commandStr = COMMAND_SUB_STR + (command == 10 ? "a" : String.valueOf(command));
        } else {
            // combine left wheel command and right wheel command into one
            int command = (leftWheel.getAction() << 4) | rightWheel.getAction();
        }
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
