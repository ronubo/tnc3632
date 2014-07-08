package com.thenewcircle.yamba;

import java.util.List;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class RefreshService extends IntentService {
	private final static String TAG = RefreshService.class.getSimpleName();

	private final static int MAX_FETCH_POSTS = 20;
	
	public RefreshService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public RefreshService() {
		this(TAG);
	}

		
	@Override
	public void onCreate() {
		Log.d(TAG,"onCreated");
		super.onCreate();
	}
	
	// Only thing we care about for IntentService!
	@Override
	protected void onHandleIntent(Intent intent) {

		YambaClient cloud = new YambaClient("student","password");

		List<Status> timeline;
		try {
			timeline = cloud.getTimeline(MAX_FETCH_POSTS);		

			for (Status status : timeline) {
				Log.d(TAG, 
						String.format("Status: %s said %s at %s", 
								status.getUser(), 
								status.getMessage(), 
								status.getCreatedAt().toString()));			
			}
		} catch (YambaClientException e) {
			Log.e(TAG, "Failed to retreive timeline" );
			e.printStackTrace();
		}
	
	}	
	
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		Log.d(TAG,"onStart");
		super.onStart(intent, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG,"onDestroyed");
		super.onDestroy();
	}


}
