package com.zhida.car.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.zhida.car.utils.LogUtils;

/**
 * Created by skylan on 16/12/27.
 */

public class VerticalSeekBar extends SeekBar {

    private Drawable mThumb;
    private View mTouchableAreaView;
    private Rect mTouchableArea;

    public VerticalSeekBar(Context context) {
        super(context);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    public void notifyProgressBar() {
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    public void setTouchArea(View view) {
        mTouchableAreaView = view;
    }

    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        mThumb = thumb;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mThumb != null && mThumb.isStateful()) {
            int[] state = getDrawableState();
            mThumb.setState(state);
        }
        invalidate();
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);
        super.onDraw(c);
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (mTouchableAreaView != null && mTouchableArea == null) {
                    mTouchableArea = new Rect();
                    mTouchableAreaView.getDrawingRect(mTouchableArea);
                    int[] location = new int[2];
                    mTouchableAreaView.getLocationOnScreen(location);
                    mTouchableArea.left = location[0];
                    mTouchableArea.top = location[1];
                    mTouchableArea.right = mTouchableArea.right + location[0];
                    mTouchableArea.bottom = mTouchableArea.bottom + location[1];
                    LogUtils.e("test", "" + mTouchableArea);
                }
                if (mTouchableArea != null &&
                        !mTouchableArea.contains((int)event.getRawX(), (int)event.getRawY())) {
                    setProgress(50);
                    break;
                }
                setProgress(getMax() - (int) (getMax() * event.getY() / getHeight()));
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
            case MotionEvent.ACTION_UP:
                super.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

}