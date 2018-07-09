package com.shouchuang.car.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.shouchuang.car.R;
import com.shouchuang.car.datahelper.Direction;
import com.shouchuang.car.datahelper.MoveDataHelper;
import com.shouchuang.car.ui.widget.SteeringWheelView;
import com.shouchuang.car.ui.widget.VerticalSeekBar;

public class SteerControllerActivity extends Activity implements SteeringWheelView.WheelScrollDirectionListener {

    public static final int DIRECTION_RECOGNITION_OFFSET = 5;

    private SteeringWheelView mSteer;
    private VerticalSeekBar mRightRocker;

    private MoveDataHelper dataHelper;

    private int mRightspeed = 0;
    private Direction mLeftWheel = Direction.STOP;
    private Direction mRightWheel = Direction.STOP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steer_controller);
        findView();
        dataHelper = new MoveDataHelper();
    }

    private void findView() {
        mSteer = (SteeringWheelView) findViewById(R.id.steer);
        mSteer.setWheelImage(R.drawable.steering_wheel);
        mSteer.setGetWheelScrollDirection(this);
        mRightRocker = (VerticalSeekBar) findViewById(R.id.right_rocker);
        mRightRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // two direction divide progress into two part
                mRightspeed = i - 50;
                if (mRightspeed < -DIRECTION_RECOGNITION_OFFSET) {
                    mRightWheel = Direction.BACKWARD;
                } else if (mRightspeed > DIRECTION_RECOGNITION_OFFSET) {
                    mRightWheel = Direction.FORWARD;
                } else {
                    mRightWheel = Direction.STOP;
                }
                dataHelper.move(mLeftWheel, mRightWheel);
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

    @Override
    protected void onPause() {
        super.onPause();
        dataHelper.stop();
    }

    @Override
    public void onTurnRight() {
        mLeftWheel = Direction.BACKWARD;
        dataHelper.move(mLeftWheel, mRightWheel);
    }

    @Override
    public void onTurnLeft() {
        mLeftWheel = Direction.FORWARD;
        dataHelper.move(mLeftWheel, mRightWheel);
    }

    @Override
    public void onTurnStop() {
        mLeftWheel = Direction.STOP;
        dataHelper.move(mLeftWheel, mRightWheel);
    }
}
