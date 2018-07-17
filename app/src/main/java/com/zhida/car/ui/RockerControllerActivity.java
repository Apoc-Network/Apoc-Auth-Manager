package com.zhida.car.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.marcinmoskala.arcseekbar.ArcSeekBar;
import com.marcinmoskala.arcseekbar.ProgressListener;
import com.zhida.car.R;
import com.zhida.car.component.Motor;
import com.zhida.car.datahelper.MoveDataHelper;
import com.zhida.car.ui.widget.VerticalSeekBar;

public class RockerControllerActivity extends Activity {

    public static final int DIRECTION_RECOGNITION_OFFSET = 5;
    public static final int MIDDLE_SPEED_LEVEL = 20;
    public static final int HIGH_SPEED_LEVEL = 35;

    private VerticalSeekBar mLeftMotorRocker;
    private VerticalSeekBar mRightMotorRocker;
    private ArcSeekBar mLeftServoRocker;
    private ArcSeekBar mRightServoRocker;

    private MoveDataHelper dataHelper;

    private int mLeftspeed = 0;
    private int mRightspeed = 0;
    private Motor mLeftMotor = new Motor();
    private Motor mRightMotor = new Motor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocker_controller);

        findView();
        dataHelper = new MoveDataHelper();
    }

    private void findView() {
        mLeftMotorRocker = (VerticalSeekBar) findViewById(R.id.rocker_left);
        mRightMotorRocker = (VerticalSeekBar) findViewById(R.id.rocker_right);
        mLeftServoRocker = (ArcSeekBar) findViewById(R.id.seekArc_left);
        mRightServoRocker = (ArcSeekBar) findViewById(R.id.seekArc_right);

        mLeftMotorRocker.setTouchArea(findViewById(R.id.left_touch_area));
        mLeftMotorRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // two direction divide progress into two part
                mLeftspeed = i - 50;
                updateMotor(mLeftspeed, mLeftMotor);
                dataHelper.move(mLeftMotor, mRightMotor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(50);
            }
        });

        mRightMotorRocker.setTouchArea(findViewById(R.id.right_touch_area));
        mRightMotorRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // two direction divide progress into two part
                mRightspeed = i - 50;
                updateMotor(mRightspeed, mRightMotor);
                dataHelper.move(mLeftMotor, mRightMotor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(50);
            }
        });

        mLeftServoRocker.setOnStopTrackingTouch(new ProgressListener() {
            @Override
            public void invoke(int progress) {
                mLeftServoRocker.setProgress(50);
            }
        });

        mRightServoRocker.setOnStopTrackingTouch(new ProgressListener() {
            @Override
            public void invoke(int progress) {
                mRightServoRocker.setProgress(50);
            }
        });
    }

    private void updateMotor(int motorSpeed, Motor motor) {
        if (motorSpeed < -DIRECTION_RECOGNITION_OFFSET) {
            motor.setDirection(Motor.Direction.BACKWARD);
        } else if (motorSpeed > DIRECTION_RECOGNITION_OFFSET) {
            motor.setDirection( Motor.Direction.FORWARD);
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
}
