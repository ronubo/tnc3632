package com.thenewcircle.yamba;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class StatusProvider extends ContentProvider {
	private static final String TAG = StatusProvider.class.getSimpleName();
	private DbHelper dbHelper;
	
	private static final UriMatcher sUriMatcher = new UriMatcher (UriMatcher.NO_MATCH);
	static {
		sUriMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE, StatusContract.STATUS_DIR); // when we just have a table		
		sUriMatcher.addURI(StatusContract.AUTHORITY, StatusContract.TABLE + "/#", StatusContract.STATUS_ITEM); // when we just have a table/number 
	}
	
	@Override
	public boolean onCreate() {
		dbHelper = new DbHelper(getContext()); // Providers and Receivers call getContext()
		Log.d(TAG, "created");
		return false;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case StatusContract.STATUS_DIR:
			Log.d(TAG, "gotType " + StatusContract.STATUS_TYPE_DIR);
			return StatusContract.STATUS_TYPE_DIR;
		case StatusContract.STATUS_ITEM: 
			Log.d(TAG,"gotType " + StatusContract.STATUS_TYPE_ITEM );
			return StatusContract.STATUS_TYPE_ITEM;
			default:
				throw new IllegalArgumentException("Illegal uri: " + uri);
		}
	}
	

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri ret = null;
		// "Assert" correct URI
		if (sUriMatcher.match(uri) != StatusContract.STATUS_DIR) {
			   throw new IllegalArgumentException ("Illegal uri: " + uri) ;
			}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = db.insertWithOnConflict(StatusContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE );
		// Was insert successful? 
		if (rowId != -1) {
			String id = values.getAsString(StatusContract.Column.ID);
			ret = Uri.withAppendedPath(uri, id);
		}
		
		Log.d(TAG,"inserted uri " + ret);

		return ret;
	}

	/* Implement Purge feature
	   Use db.delete()
	   DELETE FROM status WHERE id=? AND user='?'
	  Note that if selectionArgs are null, it's essentially implementing 
	  "DELETE FROM tableName" - which in SQL deletes all entries
	  
	  Refactor: What if the uri contains the id? That is if we want to delete
	  a specific row?  Then we need to parse it. So we'll move to switch case.
	*/
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		 String where;  // what we'll give to the db.delete() statment
		switch (sUriMatcher.match(uri)) {
			case  StatusContract.STATUS_DIR:
				where = selection;
				// we could do where =  (selection == null) ? "1" : selection; // so we can count deleted rows TODO: Check that!
				break;
			case  StatusContract.STATUS_ITEM:
				long id = ContentUris.parseId(uri); // extracts the id								
				where = StatusContract.Column.ID + "=" + id + 
						(TextUtils.isEmpty(selection) ? "" : " AND ( " + selection + " )" );
				break;			
		default:
			throw new IllegalArgumentException ("Illegal uri: " + uri) ;
		}
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int ret = db.delete(StatusContract.TABLE, where, selectionArgs);
		Log.d(TAG, "deleted");
		return ret;
	}

	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		String where;  // to figure out the selection args by the Uri.
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();		
		switch (sUriMatcher.match(uri)) {
		case  StatusContract.STATUS_DIR:
			// in case of a directory we're not going to do anything
			break;
		case  StatusContract.STATUS_ITEM:
			// instead of doing the crazy stuff as in the delete() function - we'll use the QueryBuilder
			qb.appendWhere(StatusContract.Column.ID + "=" + uri.getLastPathSegment() ); // We know this is the ID since we already matched that.
			break;			
		default:
			throw new IllegalArgumentException ("Illegal uri: " + uri) ;
		}
		

		String orderBy = (TextUtils.isEmpty(sortOrder)) ? StatusContract.DEFAULT_SORT : sortOrder;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
						
		Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		
		Log.d(TAG, "queried records: " + cursor.getCount() );
		return cursor;				
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
