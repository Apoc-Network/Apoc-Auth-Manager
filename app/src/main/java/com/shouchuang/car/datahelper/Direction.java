package com.shouchuang.car.datahelper;

/**
 * Created by skylan on 16/12/25.
 */

public enum Direction {
    STOP(0x00),
    FORWARD(0x01),
    BACKWARD(0x10);

    private int mValue;

    Direction(int value) {
        this.mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
