package com.catglo.errorpopupinascrollviewtest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		EditText top    = (EditText)findViewById(R.id.errorTextTop);
		EditText bottom = (EditText)findViewById(R.id.errorTextBottom);
		
		top.setText("B");
		bottom.setText("B");
		//top.setError("Top error");
		bottom.setError("Bottom error");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
