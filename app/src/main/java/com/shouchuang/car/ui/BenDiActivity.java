package com.shouchuang.car.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shouchuang.car.R;
import com.shouchuang.car.datahelper.ConnectDataHelper;
import com.shouchuang.car.datahelper.Direction;
import com.shouchuang.car.datahelper.MoveDataHelper;
import com.shouchuang.car.utils.WifiManager;

public class BenDiActivity extends Activity implements OnClickListener {

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            //按下操作
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                switch (view.getId()) {
                    case R.id.left_forward:
                        setTimerTask(0);
                        break;
                    case R.id.left_backward:
                        setTimerTask(1);
                        break;
                    case R.id.right_forward:
                        setTimerTask(2);
                        break;
                    case R.id.right_backward:
                        setTimerTask(3);
                        break;
                }
            }
            //抬起操作
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                cancelTimerTask();

                Message msg = new Message();
                msg.what = 12;
                handler.sendMessage(msg);
            }
            //移动操作
            if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            }
            return false;
        }
    };

    private TextView text1;

    private String str_ip;
    private String str_speed;

    private Button btn_left_froward;
    private Button btn_left_stop;
    private Button btn_left_backward;
    private Button btn_right_forward;
    private Button btn_right_stop;
    private Button btn_right_backward;

    private Button but_r_speenUp;
    private Button but_l_speenUp;

    private RelativeLayout mainlayout;

    private String str_instr;
    private Button but1;
    private SeekBar seek;


    private Timer mTimer;
    private int StrSend;

    private ConnectDataHelper mConnectDataHelper = null;
    private MoveDataHelper mMoveDataHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bendi);

        initView();

        mConnectDataHelper = new ConnectDataHelper();
        mMoveDataHelper = new MoveDataHelper();

        if (!WifiManager.isWifiConnected(this)) {
            text1.setText(R.string.str_con);
            Intent intent = new Intent();
            intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
            startActivity(intent);
            but1.setVisibility(View.VISIBLE);
        } else {
            mConnectDataHelper.connectCar();
        }

//        wifi_ssid = WifiManager.getConnectWifiSsid(this);
//        System.out.println(wifi_ssid);
    }

    public void initView() {
        text1 = (TextView) findViewById(R.id.text1);
        but1 = (Button) findViewById(R.id.but1);

        but_l_speenUp = (Button) findViewById(R.id.but_l_speenUp);
        but_r_speenUp = (Button) findViewById(R.id.but_r_speenUp);
        seek = (SeekBar) findViewById(R.id.seek);
        seek.setEnabled(false);

        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);
        btn_left_froward = (Button) findViewById(R.id.left_forward);
        btn_left_stop = (Button) findViewById(R.id.left_stop);
        btn_left_backward = (Button) findViewById(R.id.left_backward);
        btn_right_forward = (Button) findViewById(R.id.right_forward);
        btn_right_stop = (Button) findViewById(R.id.right_stop);
        btn_right_backward = (Button) findViewById(R.id.right_backward);
        
        btn_left_froward.setOnTouchListener(onTouchListener);
        btn_left_backward.setOnTouchListener(onTouchListener);
        btn_right_forward.setOnTouchListener(onTouchListener);
        btn_right_backward.setOnTouchListener(onTouchListener);

        but_l_speenUp.setOnClickListener(this);
        but_r_speenUp.setOnClickListener(this);
        but1.setOnClickListener(this);
    }


    private void setTimerTask(int str) {
        StrSend = str;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 11;
                handler.sendMessage(msg);
            }
        }, 100, 100);
    }

    private void cancelTimerTask() {
        StrSend = -1;
        mTimer.cancel();
    }

    Handler handler = new Handler() {

        /*
         * (non-Javadoc)
         *
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    text1.setText(R.string.str_d_f);
                    but1.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    text1.setText(R.string.str_son_s);
                    but1.setVisibility(View.GONE);
//                    str_ip = IPUtils.getip(str_udp1);
                    System.out.println(str_ip);
                    mainlayout.setVisibility(View.VISIBLE);
//                    getspeed("5");
                    break;
                case 2:
                    //text1.setText(str_instr + "OK");
                    break;
                case 3:
                    //text1.setText(str_instr + "指令发送失败");
                    break;
                case 4:
                    //text1.setText("获取速度失败");
                    break;
                case 5:
                    //text1.setText("当前速度: " + str_speed);
                    Log.e("SkyTest", "Car Speed" + Integer.parseInt(str_speed));
                    seek.setProgress(Integer.parseInt(str_speed));
                    break;
                case 11:
                    switch (StrSend) {
                        case 0:
                            mMoveDataHelper.sendDirection(Direction.LETF_FORWARD);
                            break;
                        case 1:
                            mMoveDataHelper.sendDirection(Direction.LEFT_BACKEARD);
                            break;
                        case 2:
                            mMoveDataHelper.sendDirection(Direction.RIGHT_FORWARD);
                            break;
                        case 3:
                            mMoveDataHelper.sendDirection(Direction.RIGHT_BACKEARD);
                            break;
                    }
                    break;
                case 12:
                    str_instr = "停止";
                    mMoveDataHelper.sendDirection(Direction.LEFT_STOP);
                    mMoveDataHelper.sendDirection(Direction.RIGHT_STOP);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
//            case R.id.but_center:
//                str_instr = "停止";
//                send("0");
//                break;
//		case R.id.btn_left_stop:
//			str_instr = "左转";
//			send("3");
//			break;
//		case R.id.btn_left_backward:
//			str_instr = "右转";
//			send("4");
//			break;
//		case R.id.btn_right_forward:
//			str_instr = "前进";
//			send("1");
//			break;
//		case R.id.btn_rigth_stop:
//			str_instr = "后退";
//			send("2");
//			break;
            case R.id.but_l_speenUp:
                str_instr = "减速";
//                send("7");
//                getspeed("5");
                break;
            case R.id.but_r_speenUp:
                str_instr = "加速";
//                send("6");
//                getspeed("5");
                break;
            case R.id.but1:
                mConnectDataHelper.connectCar();
                break;

            default:
                break;
        }
    }



}
