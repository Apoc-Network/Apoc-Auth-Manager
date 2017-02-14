package com.shouchuang.car.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.shouchuang.car.R;

public class ControllerSelectActivity extends Activity {

    public static final String ACTION_ROCKER_CONTROLLER = "com.shouchuang.car.ui.rocker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_select);

        HorizontalInfiniteCycleViewPager controllerSelectViewPager=
                (HorizontalInfiniteCycleViewPager) findViewById(R.id.hicvp);

        controllerSelectViewPager.setAdapter(new ControllerSelectAdapter(this));

    }

    class ControllerSelectAdapter extends PagerAdapter {

        private final int[] CONTROLLER_PIC_RES = {
                R.drawable.ic_launcher,
                R.drawable.ic_launcher
        };

        private final String[] ACTION = {
                ACTION_ROCKER_CONTROLLER,
                ACTION_ROCKER_CONTROLLER
        };

        private Context mContext;
        private LayoutInflater mLayoutInflater;

        public ControllerSelectAdapter(final Context context) {
            mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return CONTROLLER_PIC_RES.length;
        }

        @Override
        public int getItemPosition(final Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = mLayoutInflater.inflate(R.layout.item, container, false);
            ImageView pic = (ImageView) view.findViewById(R.id.img_item);
            pic.setImageResource(CONTROLLER_PIC_RES[position]);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(ACTION[position]);
                    startActivity(intent);
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(final View view, final Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(final ViewGroup container, final int position, final Object object) {
            container.removeView((View) object);
        }
    }

}
