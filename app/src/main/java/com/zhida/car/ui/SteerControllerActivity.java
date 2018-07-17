package com.zhida.car.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.zhida.car.R;
import com.zhida.car.component.Motor;
import com.zhida.car.datahelper.MoveDataHelper;
import com.zhida.car.ui.widget.SteeringWheelView;
import com.zhida.car.ui.widget.VerticalSeekBar;

public class SteerControllerActivity extends Activity implements SteeringWheelView.WheelScrollDirectionListener {

    public static final int DIRECTION_RECOGNITION_OFFSET = 5;
    public static final int MIDDLE_SPEED_LEVEL = 20;
    public static final int HIGH_SPEED_LEVEL = 35;

    private SteeringWheelView mDirectionSteer;
    private VerticalSeekBar mMotivationRocker;

    private MoveDataHelper dataHelper;

    private int mMotivationSpeed = 0;

    private Motor mDirectionWheel;
    private Motor mMotivationWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steer_controller);
        findView();

        mDirectionWheel = new Motor();
        mMotivationWheel = new Motor();
        dataHelper = new MoveDataHelper();
    }

    private void findView() {
        mDirectionSteer = (SteeringWheelView) findViewById(R.id.steer);
        mDirectionSteer.setWheelImage(R.drawable.steering_wheel);
        mDirectionSteer.setGetWheelScrollDirection(this);

        mMotivationRocker = (VerticalSeekBar) findViewById(R.id.right_rocker);
        mMotivationRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // two direction divide progress into two part
                mMotivationSpeed = i - 50;
                updateMotor(mMotivationSpeed, mMotivationWheel);
                dataHelper.move(mDirectionWheel, mMotivationWheel);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(50);
            }
        });
    }

    private void updateMotor(int motorSpeed, Motor motor) {
        if (motorSpeed < -DIRECTION_RECOGNITION_OFFSET) {
            motor.setDirection(Motor.Direction.BACKWARD);
        } else if (motorSpeed > DIRECTION_RECOGNITION_OFFSET) {
            motor.setDirection(Motor.Direction.FORWARD);
        } else {
            motor.setDirection(Motor.Direction.STOP);
        }
        if (Math.abs(motorSpeed) < MIDDLE_SPEED_LEVEL) {
            motor.setSpeed(Motor.Speed.SLOW);
        } else if (Math.abs(motorSpeed) >= HIGH_SPEED_LEVEL) {
            motor.setSpeed(Motor.Speed.HIGH);
        } else {
            motor.setSpeed(Motor.Speed.MEDIUM);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataHelper.stop();
    }

    @Override
    public void onTurnRight() {
        mDirectionWheel.setDirection(Motor.Direction.BACKWARD);
        dataHelper.move(mDirectionWheel, mMotivationWheel);
    }

    @Override
    public void onTurnLeft() {
        mDirectionWheel.setDirection(Motor.Direction.FORWARD);
        dataHelper.move(mDirectionWheel, mMotivationWheel);
    }

    @Override
    public void onTurnStop() {
        mDirectionWheel.setDirection(Motor.Direction.STOP);
        dataHelper.move(mDirectionWheel, mMotivationWheel);
    }
}
