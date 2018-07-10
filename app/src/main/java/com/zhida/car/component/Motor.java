package com.zhida.car.component;


import com.zhida.car.component.BasePart;

/**
 * Created by skylan on 17/2/
 * 0000(motor 4 bit)
 * 00(motor high 2 bit speed 00/01/10)
 *   00(motor low 2 bit direction 00/01/10)
 */

public class Motor extends BasePart {

    private Direction mDirection;
    private Speed mSpeed;

    public enum Direction {
        // 00
        STOP(0),
        // 01
        FORWARD(1),
        // 10
        BACKWARD(2);

        private int mValue;

        Direction(int value) {
            this.mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }

    public enum Speed {
        // 00
        SLOW(0),
        // 01
        MEDIUM(1),
        // 10
        HIGH(2);

        private int mValue;

        Speed(int value) {
            mValue = value;
        }

        public int getValue() {
            return mValue;
        }
    }

    public Motor() {
        // default 0000
        mDirection = Direction.STOP;
        mSpeed = Speed.SLOW;
    }

    public Direction getDirection() {
        return mDirection;
    }

    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    public Speed getSpeed() {
        return mSpeed;
    }

    public void setSpeed(Speed speed) {
        this.mSpeed = speed;
    }

    public int getAction() {
        return (mSpeed.getValue() << 2) | mDirection.getValue();
    }
}
