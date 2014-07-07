package com.thenewcircle.yamba;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	static final String TAG = MainActivity.class.getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main , menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.item_post) {
			Intent intent = new Intent(this, StatusActivity.class);
			startActivity(intent);
			
		}
		Log.d(TAG, "Clicked menu item: " + item.getItemId());
		return super.onMenuItemSelected(featureId, item);
	}
	
	

}
