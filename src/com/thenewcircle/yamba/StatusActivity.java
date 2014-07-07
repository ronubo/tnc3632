
package com.thenewcircle.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class StatusActivity extends Activity {
	
	private static final String TAG = StatusActivity.class.getSimpleName();

	private Button mButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG,"onCreated");
		
		setContentView(R.layout.activity_status);		

		mButton = (Button) findViewById(R.id.button_tweet);

		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(StatusActivity.this, "Button Clicked!",
						Toast.LENGTH_SHORT).show();
				
				Log.d("IntelTag", "onClicked!");
			}
		});

	}
	
	@Override
	public void onBackPressed() {
		Log.d(TAG,"onBackPressed");
		super.onBackPressed();		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d(TAG,"onStarted");
	}
	
	@Override
	protected void onStop() {
		Log.d(TAG,"onStopped");
		super.onStop();
	}
	
	@Override
	protected void onPause() {
		Log.d(TAG,"onPaused");
		super.onPause();
	}
	
	@Override
	protected void onResume() {		
		super.onResume();
		Log.d(TAG,"onResumed");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.d(TAG,"onRestarted");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG,"onDestroyed");
	}
}
