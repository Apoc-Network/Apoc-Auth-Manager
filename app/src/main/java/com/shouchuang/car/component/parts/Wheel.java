package com.shouchuang.car.component.parts;


import com.shouchuang.car.component.hardware.Motor;

/**
 * Created by skylan on 17/2/19.
 */

public class Wheel extends BasePart{
    private Motor motor = null;

    public static final int STOP = Motor.STATE_STOP;
    public static final int FORWARD = Motor.STATE_CLOCKWISE;
    public static final int BACKWARD = Motor.STATE_CONTERCLOCKWISE;

    public Wheel() {
        setDefaultAction(STOP);
    }
}
