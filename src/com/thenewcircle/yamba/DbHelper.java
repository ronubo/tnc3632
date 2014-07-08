package com.thenewcircle.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	public static final String TAG = DbHelper.class.getSimpleName();
	public DbHelper(Context context) {
		super(context, StatusContract.DB_NAME, null, StatusContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format("CREATE table %s ( %s int primary key, %s text, %s text, %s int )",
				StatusContract.TABLE, StatusContract.Column.ID, StatusContract.Column.USER, StatusContract.Column.MESSAGE, StatusContract.Column.CREATED_AT);

		Log.d(TAG,"SQL statmenet: " + sql);
		db.execSQL(sql);
				
				
	}

	// Lazy developer hack
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + StatusContract.TABLE);
		onCreate(db);
	}

}
