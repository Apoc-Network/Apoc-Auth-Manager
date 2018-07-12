package com.zhida.car.component.hardware;

/**
 * Created by skylan on 17/2/19.
 */

public class Motor {
    public static final int STATE_STOP = 0;
    public static final int STATE_CLOCKWISE = 1;
    public static final int STATE_CONTERCLOCKWISE = 2;

    private int mState;
    private int mSpeed;

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        this.mSpeed = speed;
    }
}
