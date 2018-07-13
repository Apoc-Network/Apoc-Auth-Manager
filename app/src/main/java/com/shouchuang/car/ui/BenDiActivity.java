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
import com.shouchuang.car.utils.WifiManager;

public class BenDiActivity extends Activity implements OnClickListener {

    private TextView text1;

    private String str_ip;
    private String str_speed;
    private Button but_center;
    private Button but_left;
    private Button but_right;
    private Button but_up;
    private Button but_below;
    private Button but_r_speenUp;
    private Button but_l_speenUp;

    private RelativeLayout mainlayout;

    private String str_instr;
    private Button but1;
    private SeekBar seek;


    private Timer mTimer;
    private int StrSend;

    private ConnectDataHelper mConnectDataHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bendi);

        initView();

        mConnectDataHelper = new ConnectDataHelper();

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
        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);
        but_center = (Button) findViewById(R.id.but_center);
        but_left = (Button) findViewById(R.id.but_left);
        but_right = (Button) findViewById(R.id.but_right);
        but_up = (Button) findViewById(R.id.but_up);
        but_below = (Button) findViewById(R.id.but_below);
        but_l_speenUp = (Button) findViewById(R.id.but_l_speenUp);
        but_r_speenUp = (Button) findViewById(R.id.but_r_speenUp);
        but1 = (Button) findViewById(R.id.but1);
        seek = (SeekBar) findViewById(R.id.seek);
        seek.setEnabled(false);

        but_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //按下操作
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//		        	Log.e("按下操作", "按下操作"); 
                    setTimerTask(3);
                }
                //抬起操作
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//		        	Log.e("抬起操作", "抬起操作"); 
                    cancelTimerTask();

                    Message msg = new Message();
                    msg.what = 12;
                    handler.sendMessage(msg);
                }
                //移动操作
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
//		        	Log.e("移动操作", "移动操作"); 
                }
                return false;
            }
        });
        but_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //按下操作
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//		        	Log.e("按下操作", "按下操作"); 
                    setTimerTask(4);
                }
                //抬起操作
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//		        	Log.e("抬起操作", "抬起操作"); 
                    cancelTimerTask();

                    Message msg = new Message();
                    msg.what = 12;
                    handler.sendMessage(msg);
                }
                //移动操作
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
//		        	Log.e("移动操作", "移动操作"); 
                }
                return false;
            }
        });
        but_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //按下操作
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//		        	Log.e("按下操作", "按下操作"); 
                    setTimerTask(1);
                }
                //抬起操作
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//		        	Log.e("抬起操作", "抬起操作"); 
                    cancelTimerTask();

                    Message msg = new Message();
                    msg.what = 12;
                    handler.sendMessage(msg);
                }
                //移动操作
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
//		        	Log.e("移动操作", "移动操作"); 
                }
                return false;
            }
        });
        but_below.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //按下操作
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//		        	Log.e("按下操作", "按下操作"); 
                    setTimerTask(2);
                }
                //抬起操作
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//		        	Log.e("抬起操作", "抬起操作"); 
                    cancelTimerTask();

                    Message msg = new Message();
                    msg.what = 12;
                    handler.sendMessage(msg);
                }
                //移动操作
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
//		        	Log.e("移动操作", "移动操作"); 
                }
                return false;
            }
        });

        but_center.setOnClickListener(this);
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
        }, 100, 1000);
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
                        case 1:
                            str_instr = "前进";
//                            send("1");
                            break;
                        case 2:
                            str_instr = "后退";
//                            send("2");
                            break;
                        case 3:
                            str_instr = "左转";
//                            send("3");
                            break;
                        case 4:
                            str_instr = "右转";
//                            send("4");
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        default:
                            break;
                    }

                    break;

                case 12:
                    str_instr = "停止";
//                    send("0");
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
            case R.id.but_center:
                str_instr = "停止";
//                send("0");
                break;
//		case R.id.but_left:
//			str_instr = "左转";
//			send("3");
//			break;
//		case R.id.but_right:
//			str_instr = "右转";
//			send("4");
//			break;
//		case R.id.but_up:
//			str_instr = "前进";
//			send("1");
//			break;
//		case R.id.but_below:
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
