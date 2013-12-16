package com.catglo.errorpopupinascrollviewtest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

public class MyEditText extends EditText {

	private CharSequence mError;
	private boolean mErrorWasChanged;
	private ErrorPopup mPopup;

	public MyEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public MyEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setError(CharSequence error){
	    error = TextUtils.stringOrSpannedString(error);

        mError = error;
        
        if (error == null) {
            if (mPopup != null) {
                if (mPopup.isShowing()) {
                    mPopup.dismiss();
                }

                mPopup = null;
            }
        } else {
            if (isFocused()) {
                showError();
            }
        }
	}
	
	
	 /**
     * Returns the Y offset to make the pointy top of the error point
     * at the middle of the error icon.
     */
    private int getErrorX() {
        /*
         * The "25" is the distance between the point and the right edge
         * of the background
         */
        final float scale = getResources().getDisplayMetrics().density;

        Drawable[] dr = getCompoundDrawables();
       // final Drawables dr = mDrawables;
        return getWidth() - mPopup.getWidth()
                - getPaddingRight()
               //   - (dr != null ? dr[0].mDrawableSizeRight : 0) / 2 + (int) (25 * scale + 0.5f);
                - (dr != null ? 50 : 0) / 2 + (int) (25 * scale + 0.5f);
    }

    /**
     * Returns the Y offset to make the pointy top of the error point
     * at the bottom of the error icon.
     */
    private int getErrorY() {
        /*
         * Compound, not extended, because the icon is not clipped
         * if the text height is smaller.
         */
        int vspace = getBottom() - getTop() -
                     getCompoundPaddingBottom() - getCompoundPaddingTop();

        Drawable[] dr = getCompoundDrawables();
      //  final Drawables dr = mDrawables;
        int icontop = getCompoundPaddingTop()
//                + (vspace - (dr != null ? dr.mDrawableHeightRight : 0)) / 2;
                  + (vspace - (dr != null ? 50 : 0)) / 2;

        /*
         * The "2" is the distance between the point and the top edge
         * of the background.
         */

        //return icontop + (dr != null ? dr.mDrawableHeightRight : 0)
        return icontop + (dr != null ? 50 : 0) 		
                - getHeight() - 2;
    }
    
    
	private void showError() {
        
        if (mPopup == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            final TextView err = (TextView) new TextView(getContext());
            err.setBackgroundColor(Color.WHITE);
            
            
            final float scale = getResources().getDisplayMetrics().density;
            mPopup = new ErrorPopup(err, (int) (200 * scale + 0.5f),(int) (50 * scale + 0.5f));
            mPopup.setFocusable(false);
            // The user is entering text, so the input method is needed.  We
            // don't want the popup to be displayed on top of it.
            mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        }

        TextView tv = (TextView) mPopup.getContentView();
        chooseSize(mPopup, mError, tv);
        tv.setText(mError);

        mPopup.showAsDropDown(this, getErrorX(), getErrorY());
        mPopup.fixDirection(mPopup.isAboveAnchor());
    }
	
	 private void chooseSize(PopupWindow pop, CharSequence text, TextView tv) {
        int wid = tv.getPaddingLeft() + tv.getPaddingRight();
        int ht = tv.getPaddingTop() + tv.getPaddingBottom();

        /*
         * Figure out how big the text would be if we laid it out to the
         * full width of this view minus the border.
         */
        int cap = getWidth() - wid;
        if (cap < 0) {
            cap = 200; // We must not be measured yet -- setFrame() will fix it.
        }

        Layout l = new StaticLayout(text, tv.getPaint(), cap,
                                    Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        float max = 0;
        for (int i = 0; i < l.getLineCount(); i++) {
            max = Math.max(max, l.getLineWidth(i));
        }

        /*
         * Now set the popup size to be big enough for the text plus the border.
         */
        pop.setWidth(wid + (int) Math.ceil(max));
        pop.setHeight(ht + l.getHeight());
    }
	
	private void hideError() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mError != null) {
            hideError();
        }
    }
	
	@Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        
        if (focused) {
            if (mError != null) {
                showError();
            }
        } else {
            if (mError != null) {
                hideError();
            }
        }
    }

	

	private static class ErrorPopup extends PopupWindow {
        private boolean mAbove = false;
        private final TextView mView;
        private ScrollView scrollView=null;
        
        ErrorPopup(TextView v, int width, int height) {
            super(v, width, height);
            mView = v;
            scrollView = searchForScrollView(v);
        }

        private ScrollView searchForScrollView(View v) {
			View parent = (View)v.getParent();
			if (parent==null) return null;
			if (parent instanceof ScrollView){
				return (ScrollView)parent;
			}
			return searchForScrollView(v);
		}

		void fixDirection(boolean above) {
            mAbove = above;

//            if (above) {
//                mView.setBackgroundResource(android.R.drawable.);
//            } else {
//                mView.setBackgroundResource(com.android.internal.R.drawable.popup_inline_error);
//            }
        }

        
        @Override
        public void update(int x, int y, int w, int h, boolean force) {
            if (scrollView != null){
            	Rect scrollBounds = new Rect();
            	scrollView.getHitRect(scrollBounds);
            	if (mView.getLocalVisibleRect(scrollBounds)==false) {
            	    return;
            	}
            }
    		super.update(x, y, w, h, force);

            boolean above = isAboveAnchor();
            if (above != mAbove) {
                fixDirection(above);
            }
        }
    }
}
