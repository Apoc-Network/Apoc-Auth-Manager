package com.shouchuang.car.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.shouchuang.car.R;
import com.shouchuang.car.ui.widget.VerticalSeekBar;

public class RockerControllerActivity extends Activity {

    private VerticalSeekBar mLeftRocker;
    private VerticalSeekBar mRightRocker;

    SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
//            mLeftRocker.setProgress(50);
            Log.e("SkyTest", "stop" );
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.e("SkyTest", "Progress:" + progress);
//            MYSendValue[2]=progress;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocker_controller);
        findView();
    }



    private void findView() {
        mLeftRocker = (VerticalSeekBar) findViewById(R.id.left_rocker);
        mRightRocker = (VerticalSeekBar) findViewById(R.id.right_rocker);
        mLeftRocker.setOnSeekBarChangeListener(mSeekBarChangeListener);
        mRightRocker.setOnSeekBarChangeListener(mSeekBarChangeListener);
    }

}
