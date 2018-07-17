package com.zhida.car.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class SteeringWheelView extends android.support.v7.widget.AppCompatImageView {

    private Bitmap imageOriginal, imageScaled;     //variables for original and re-sized image
    private Matrix matrix;                         //Matrix used to perform rotations
    private int wheelHeight, wheelWidth;           //height and width of the view
    private Context context;

    public interface WheelScrollDirectionListener {
        void onTurnRight();

        void onTurnLeft();

        void onTurnStop();
    }

    WheelScrollDirectionListener mWheelScrollDirectionListener = null;

    public void setGetWheelScrollDirection(WheelScrollDirectionListener listener) {
        this.mWheelScrollDirectionListener = listener;
    }

    public SteeringWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    //initializations
    private void init(Context context) {
        this.context = context;
        this.setScaleType(ScaleType.MATRIX);

        // initialize the matrix only once
        if (matrix == null) {
            matrix = new Matrix();
        } else {
            matrix.reset();
        }

        //touch events listener
        this.setOnTouchListener(new WheelTouchListener());
    }

    /**
     * Set the wheel image.
     *
     * @param drawableId the id of the drawable to be used as the wheel image.
     */
    public void setWheelImage(int drawableId) {
        imageOriginal = BitmapFactory.decodeResource(context.getResources(), drawableId);
    }

    /*
     * We need this to get the dimensions of the view. Once we get those,
     * We can scale the image to make sure it's proper,
     * Initialize the matrix and align it with the views center.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // method called multiple times but initialized just once
        if (wheelHeight == 0 || wheelWidth == 0) {
            wheelHeight = h;
            wheelWidth = w;
            // resize the image
            Matrix resize = new Matrix();
            resize.postScale(
                    (float) Math.min(wheelWidth, wheelHeight) / (float) imageOriginal.getWidth(),
                    (float) Math.min(wheelWidth, wheelHeight) / (float) imageOriginal.getHeight()
            );
            imageScaled = Bitmap.createBitmap(imageOriginal, 0, 0, imageOriginal.getWidth(),
                    imageOriginal.getHeight(), resize, false);
            // translate the matrix to the image view's center
            float translateX = wheelWidth / 2 - imageScaled.getWidth() / 2;
            float translateY = wheelHeight / 2 - imageScaled.getHeight() / 2;
            matrix.postTranslate(translateX, translateY);
            SteeringWheelView.this.setImageBitmap(imageScaled);
            SteeringWheelView.this.setImageMatrix(matrix);
        }
    }

    /**
     * get the angle of a touch event.
     */
    private double getAngle(double x, double y) {
        x = x - (wheelWidth / 2d);
        y = wheelHeight - y - (wheelHeight / 2d);

        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
                return 180 - Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 3:
                return 180 + (-1 * Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            default:
                return 0;
        }
    }

    /**
     * get the quadrant of the wheel which contains the touch point (x,y)
     *
     * @return quadrant 1,2,3 or 4
     */
    private static int getQuadrant(double x, double y) {
        if (x >= 0) {
            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }

    /**
     * rotate the wheel by the given angle
     *
     * @param degrees
     */
    private void rotateWheel(float degrees) {
        matrix.postRotate(degrees, wheelWidth / 2, wheelHeight / 2);
        SteeringWheelView.this.setImageMatrix(matrix);
    }

    //listener for touch events on the wheel
    private class WheelTouchListener implements OnTouchListener {

        private double startAngle;
        private double currentAngle;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    //get the start angle for the current move event
                    startAngle = getAngle(event.getX(), event.getY());
                    break;

                case MotionEvent.ACTION_MOVE:
                    //get the current angle for the current move event
                    currentAngle = getAngle(event.getX(), event.getY());


                    float dimen = (float) (startAngle - currentAngle);
                    //rotate the wheel by the difference
                    rotateWheel(dimen);

                    if (dimen > 0.0) {
                        mWheelScrollDirectionListener.onTurnRight();
                    } else if (dimen < -0.0) {
                        mWheelScrollDirectionListener.onTurnLeft();
                    } else {
                        mWheelScrollDirectionListener.onTurnStop();
                    }
                    //current angle becomes start angle for the next motion
                    startAngle = currentAngle;
                    break;

                case MotionEvent.ACTION_UP:
                    mWheelScrollDirectionListener.onTurnStop();
                    break;
            }
            return true;
        }
    }

}
