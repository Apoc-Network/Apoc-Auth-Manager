package com.shouchuang.car.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.shouchuang.car.R;
import com.shouchuang.car.datahelper.Direction;
import com.shouchuang.car.datahelper.MoveDataHelper;
import com.shouchuang.car.ui.widget.DashboardView;
import com.shouchuang.car.ui.widget.VerticalSeekBar;

public class RockerControllerActivity extends Activity {

    public static final int DIRECTION_RECOGNITION_OFFSET = 5;

    private VerticalSeekBar mLeftRocker;
    private VerticalSeekBar mRightRocker;
    private DashboardView mDashboardView;

    private MoveDataHelper dataHelper;

    private int mLeftspeed = 0;
    private int mRightspeed = 0;
    private Direction mLeftWheel = Direction.STOP;
    private Direction mRightWheel = Direction.STOP;


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
                if (mLeftspeed < -DIRECTION_RECOGNITION_OFFSET) {
                    mLeftWheel = Direction.BACKWARD;
                } else if (mLeftspeed > DIRECTION_RECOGNITION_OFFSET) {
                    mLeftWheel = Direction.FORWARD;
                } else {
                    mLeftWheel = Direction.STOP;
                }
                drawDashboard();
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
                drawDashboard();
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
