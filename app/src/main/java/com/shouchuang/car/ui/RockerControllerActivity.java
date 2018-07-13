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
        mLeftRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mLeftspeed = i - 50;
                if (mLeftspeed < -5) {
                    mLeftWheel = Direction.BACKWARD;
                } else if (mLeftspeed > 5) {
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
                ((VerticalSeekBar) seekBar).notifyProgressBar();
            }
        });
        mRightRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mRightspeed = i - 50;
                if (mRightspeed < -5) {
                    mRightWheel = Direction.BACKWARD;
                } else if (mRightspeed > 5) {
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
                ((VerticalSeekBar) seekBar).notifyProgressBar();
            }
        });
        mDashboardView = (DashboardView) findViewById(R.id.dashboard_view);
    }

    private void drawDashboard() {
        mDashboardView.setVelocity(Math.abs(mLeftspeed) + Math.abs(mRightspeed));
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataHelper.stop();
    }
}
