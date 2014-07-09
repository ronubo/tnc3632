package com.thenewcircle.yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	static final String TAG = MainActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main , menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_tweet:	
			startActivity(new Intent(this, StatusActivity.class));
			return true; 
		case R.id.action_refresh:
			startService(new Intent(this, RefreshService.class));
			return true;
		case R.id.action_purge:
		     int rows = getContentResolver().delete(StatusContract.CONTENT_URI, null, null);
             Toast.makeText(this, "Deleted " + rows+ " rows",Toast.LENGTH_SHORT).show();
             return true;

		default:
			return false;
		}
	}

}
