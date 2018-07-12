package com.zhida.car.datahelper;

/**
 * Created by skylan on 16/12/25.
 */

public enum Direction {
    STOP(0),
    FORWARD(1),
    BACKWARD(2);

    private int mValue;

    Direction(int value) {
        this.mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
