package com.catglo.errorpopupinascrollviewtest;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;


public class NonFocusingScrollView extends ScrollView {

    private boolean mBlockRequestFocusOnFling = false;

    public NonFocusingScrollView(Context context) {
        super(context);
    }

    public NonFocusingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonFocusingScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ArrayList<View> getFocusables(int direction) {
        if(mBlockRequestFocusOnFling)
            return new ArrayList<View>();
        return super.getFocusables(direction);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        if(!mBlockRequestFocusOnFling)
        super.requestChildFocus(child, focused);
    }


    @Override
    public void fling(int velocityY) {
        mBlockRequestFocusOnFling = true;
        super.fling(velocityY);
        mBlockRequestFocusOnFling = false;
    }
}
