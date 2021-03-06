
package com.thenewcircle.yamba;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StatusActivity extends Activity {

	private static final String TAG = StatusActivity.class.getSimpleName();

	private Button mButton;
	private EditText mTextStatus;
	private TextView mTextCount;
	private int mDefaultTextCountColor;
	private static final int MAX_TWEET_LENGTH = 140;
	private static final int TWEET_LENGTH_WARNING_THRESHOLD = 50;
	
	private YambaClient mYambaClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(TAG,"onCreated");

		setContentView(R.layout.activity_status);		

		mButton = (Button) findViewById(R.id.button_tweet);
		mTextStatus = (EditText) findViewById(R.id.edit_status);
		mTextCount = (TextView) findViewById(R.id.text_count);
		
		mTextCount.setText(Integer.toString(MAX_TWEET_LENGTH));
		mDefaultTextCountColor = mTextCount.getTextColors().getDefaultColor();

		
		mYambaClient = 
				new YambaClient("student", "password");
		
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
								
				
				// Required: 
				// 1. See what's on the edit text
				// 2. When button is pressed (That's why we're here!) - Post to cloud
				// Means:
				// Yamba Client
				
				Log.d(TAG, "Post status button clicked!");
				
				// TODO AsyncTask
				new PostTask().execute(mTextStatus.getText().toString());
			}
		});


		mTextStatus.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int count = MAX_TWEET_LENGTH - s.length();
				mTextCount.setText(Integer.toString(count));

				if (count <= TWEET_LENGTH_WARNING_THRESHOLD) {
					//For this we need the getResources()... :mTextCount.setTextColor(android.R.color.holo_red_dark);
					// So use:
					mTextCount.setTextColor(Color.RED);
				} else {
					mTextCount.setTextColor(mDefaultTextCountColor);
				}


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
	
	class PostTask extends AsyncTask<String, Void, String> {
		
//		Context mContext;
//		PostTask (Context ctx) { mContext = ctx ; }
//		PostTask ()
		
		private ProgressDialog mProgress = new ProgressDialog(StatusActivity.this);
		
		@Override
		protected void onPreExecute() {			
			super.onPreExecute();
			mProgress.setTitle("Post Status");
			mProgress.setMessage("Please wait...");
			mProgress.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String status = params[0];			

			try {
				mYambaClient.postStatus(status);
				Log.d(TAG,"Posted status in background");
				return "Successfuly posted status";                
			} catch (YambaClientException e) {
				Log.e(TAG, "Failed to post status in background" );
				e.printStackTrace();
				return "Failed to post status";
			}
		}
		
		@Override
		protected void onPostExecute(String result) {			
			super.onPostExecute(result);
			mProgress.dismiss();
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_SHORT).show();
		}
		
	}
}
