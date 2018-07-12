package com.zhida.car.component.parts;

/**
 * Created by skylan on 17/2/19.
 */

public class BasePart {
    protected int mDefaultAction;

    protected int getDefaultAction() {
        return mDefaultAction;
    }

    protected void setDefaultAction(int defaultAction) {
        this.mDefaultAction = defaultAction;
    }
}
