package com.thenewcircle.yamba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	private static final String TAG = BootReceiver.class.getSimpleName();
	private static final long DEFAULT_INTERVAL = 30000; // 30 secs just for the
														// example

	@Override
	public void onReceive(Context context, Intent intent) {

		PendingIntent operation = PendingIntent.getService(context, -1,
				new Intent(context, RefreshService.class),
				PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC,
				System.currentTimeMillis(), DEFAULT_INTERVAL, operation);

		Log.d(TAG, "onReceived");
	}

}
