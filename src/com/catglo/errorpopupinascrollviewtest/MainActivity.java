package com.catglo.errorpopupinascrollviewtest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ScrollView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		//Use this setContentView for default behavior
		//setContentView(R.layout.activity_main);
		
		//Use this setContentView for the fix described here http://stackoverflow.com/a/20382468
		//setContentView(R.layout.activity_nonfocusingscrollview);
		
		//Use this setContentView for the fix with the MyEditText class. This solution was not fully completed and may be worth exploring
		//setContentView(R.layout.activity_main_my_edit_text);
		
		//Use this for this fix that worked best so far http://stackoverflow.com/a/6486348/528746 
		setContentView(R.layout.activity_focusable_fix);
		ScrollView scroll = (ScrollView)findViewById(R.id.scrollView1);
		scroll.setOnTouchListener(new OnTouchListener() {public boolean onTouch(View arg0, MotionEvent arg1) {        
            View focussedView = getCurrentFocus(); 
            if( focussedView != null ) focussedView.clearFocus();
                
            return false;
	    }});
		
		EditText top    = (EditText)findViewById(R.id.errorTextTop);
		EditText bottom = (EditText)findViewById(R.id.errorTextBottom);
		
		top.setText("B");
		bottom.setText("B");
		top.setError("Top error");
		bottom.setError("Bottom error");
		
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
