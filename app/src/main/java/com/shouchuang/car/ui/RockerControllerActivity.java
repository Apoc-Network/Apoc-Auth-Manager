package com.shouchuang.car.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
                if (i < 45) {
                    dataHelper.move(Direction.LEFT_BACKEARD);
                } else if (i > 55) {
                    dataHelper.move(Direction.LETF_FORWARD);
                } else {
                    dataHelper.move(Direction.LEFT_STOP);
                }
                mDashboardView.setVelocity(Math.abs(i - 50)*2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                dataHelper.cancelLeftSend();
            }
        });
        mRightRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i < 45) {
                    dataHelper.move(Direction.RIGHT_BACKEARD);
                } else if (i > 55) {
                    dataHelper.move(Direction.RIGHT_FORWARD);
                } else {
                    dataHelper.move(Direction.RIGHT_STOP);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                dataHelper.cancelRightSend();
                seekBar.setProgress(50);
                ((VerticalSeekBar)seekBar).notifyProgressBar();
            }
        });
        mDashboardView = (DashboardView) findViewById(R.id.dashboard_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataHelper.cancelLeftSend();
        dataHelper.cancelRightSend();
    }
}
