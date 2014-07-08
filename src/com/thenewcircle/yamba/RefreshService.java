package com.thenewcircle.yamba;

import java.util.Date;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class RefreshService extends Service {
	private final static String TAG = Service.class.getSimpleName();
	
	private final static int MAX_FETCH_POSTS = 20;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.d(TAG,"onCreated");
		super.onCreate();
	}
	
	// Only thing we really care about
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
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
		
		return super.onStartCommand(intent, flags, startId);
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
