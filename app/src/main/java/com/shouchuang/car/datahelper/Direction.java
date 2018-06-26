package com.shouchuang.car.datahelper;

/**
 * Created by skylan on 16/12/25.
 */

public enum Direction {
    LEFT_STOP(0),
    LETF_FORWARD(1),
    LEFT_BACKEARD(2),
    RIGHT_STOP(3),
    RIGHT_FORWARD(4),
    RIGHT_BACKEARD(5);

    private int mValue;

    Direction(int value) {
        this.mValue = value;
    }

    public int getValue() {
        return mValue;
    }
}
