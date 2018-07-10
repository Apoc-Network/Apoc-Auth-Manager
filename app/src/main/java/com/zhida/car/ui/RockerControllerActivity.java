package com.zhida.car.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.zhida.car.R;
import com.zhida.car.component.Motor;
import com.zhida.car.datahelper.MoveDataHelper;
import com.zhida.car.ui.widget.DashboardView;
import com.zhida.car.ui.widget.VerticalSeekBar;

public class RockerControllerActivity extends Activity {

    public static final int DIRECTION_RECOGNITION_OFFSET = 5;
    public static final int MIDDLE_SPEED_LEVEL = 20;
    public static final int HIGH_SPEED_LEVEL = 35;

    private VerticalSeekBar mLeftRocker;
    private VerticalSeekBar mRightRocker;
    private DashboardView mDashboardView;

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
        mLeftRocker = (VerticalSeekBar) findViewById(R.id.left_rocker);
        mRightRocker = (VerticalSeekBar) findViewById(R.id.right_rocker);
        mDashboardView = (DashboardView) findViewById(R.id.dashboard_view);

        mLeftRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // two direction divide progress into two part
                mLeftspeed = i - 50;
                updateMotor(mLeftspeed, mLeftMotor);
                drawDashboard();
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

        mRightRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // two direction divide progress into two part
                mRightspeed = i - 50;
                updateMotor(mRightspeed, mRightMotor);
                drawDashboard();
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

    /**
     * update DashBoard view
     */
    private void drawDashboard() {
        mDashboardView.setVelocity(Math.abs(mLeftspeed) + Math.abs(mRightspeed));
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataHelper.stop();
    }
}
