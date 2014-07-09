package com.thenewcircle.yamba;

import java.util.List;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class RefreshService extends IntentService {
	private final static String TAG = RefreshService.class.getSimpleName();
	private final static int MAX_FETCH_POSTS = 20;
	
	// RIP db private DbHelper mDbHelper;
	
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

		//RIP db mDbHelper = new DbHelper(this);
		//RIP db SQLiteDatabase db = mDbHelper.getWritableDatabase();
		ContentValues values = new ContentValues(); // Defines ORM
		
		YambaClient cloud = new YambaClient("student","password");

		List<Status> timeline;
		try {
			timeline = cloud.getTimeline(MAX_FETCH_POSTS);		

			for (Status status : timeline) {
				// Log values logcat 
				Log.d(TAG, 
						String.format("Status: %s said %s at %s", 
								status.getUser(), 
								status.getMessage(), 
								status.getCreatedAt().toString()));				
				// Add values to DB
				values.clear(); // Not necessary here, since we update all fields, but why not.
				values.put(StatusContract.Column.ID, status.getId());
				values.put(StatusContract.Column.USER, status.getUser());
				values.put(StatusContract.Column.MESSAGE, status.getMessage());
				values.put(StatusContract.Column.CREATED_AT, status.getCreatedAt().getTime()); // getTime()
				
				// This commented line would yield exception: db.insert(StatusContract.TABLE, null, values);
				// Next line will solve it:
				
				//RIP db: db.insertWithOnConflict(StatusContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE );
                getContentResolver().insert(StatusContract.CONTENT_URI, values);


				
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
