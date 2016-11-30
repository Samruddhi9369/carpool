
package com.carpool.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "android_api";

	// Login table name
	private static final String TABLE_LOGIN = "login";
	
	//  flag table name
		private static final String TABLE_LOGINFLAG = "login1";
		
	public static String status="";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PHNO = "phno";
	private static final String KEY_ADDR = "address";
	private static final String KEY_STAT = "status";
	private static final String KEY_IP = "ipString";
	private static final String KEY_UID = "uid";
	private static final String KEY_CREATED_AT = "created_at";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," 
				+ KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE,"
				+ KEY_PHNO + " TEXT UNIQUE,"
				+ KEY_ADDR + " TEXT,"
				+ KEY_IP + " TEXT,"
				+ KEY_UID + " TEXT,"
				+ KEY_CREATED_AT + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);
		
		
		String CREATE_LOGINFLAG_TABLE = "CREATE TABLE " + TABLE_LOGINFLAG + "("
				
				+ KEY_EMAIL + " TEXT UNIQUE,"
				
				+ KEY_STAT + " TEXT" + ")";
		
		db.execSQL(CREATE_LOGINFLAG_TABLE);
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGINFLAG);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String name, String email,/*String status, String address,*/ String uid, String created_at) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name); // Name
		values.put(KEY_EMAIL, email); // Email
		
		//values.put(KEY_PHNO, phno); // Name
		//values.put(KEY_ADDR, address); // Email
		values.put(KEY_UID, uid); // Email
		values.put(KEY_CREATED_AT, created_at); // Created At

		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		
		
		db.close(); // Closing database connection
	}
	
	
	/**
	 * Storing user details in database
	 * 
	public void addUser1(String email, String status) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values1 = new ContentValues();
		values1.put(KEY_EMAIL, email); // Email
		
		
		values1.put(KEY_STAT, status); // status

		// Inserting Row
		db.insert(TABLE_LOGINFLAG, null, values1);
		
		
		db.close(); // Closing database connection
	}*/
	
	
	
	
	   
    
   	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String,String> user = new HashMap<String,String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
		 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
        	user.put("name", cursor.getString(1));
        	user.put("email", cursor.getString(2));
        	user.put("phno", cursor.getString(3));
        	user.put("address", cursor.getString(4));
        	user.put("ipString", cursor.getString(5));
        	user.put("uid", cursor.getString(6));
        	user.put("created_at", cursor.getString(7));
        }
        cursor.close();
        db.close();
		// return user
		return user;
	}
	
	
	/**
	 * Getting status of user  from database
	 * */
	/*public HashMap<String, String> getUserStatusDetails(){
		HashMap<String,String> user = new HashMap<String,String>();
		String selectQuery = "SELECT  status FROM " + TABLE_LOGINFLAG;
		 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
        	
        	user.put("email", cursor.getString(1));
        	
        	user.put("status", cursor.getString(2));
        }
       
        
        if(cursor != null)
        {
            while(cursor.moveToNext()){
                String email = cursor.getString(cursor.getColumnIndex("email"));
                status = cursor.getString(cursor.getColumnIndex("status"));


                // use these strings as you want
            }
        }
        
        
        cursor.close();
        db.close();
		// return user
		return user;
	}*/
	
	
	


	/**
	 * Getting user login status
	 * return true if rows are there in table
	 * */
	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		
		// return row count
		return rowCount;
	}
	
	/**
	 * Re crate database
	 * Delete all tables and create them again
	 * */
	public void resetTables(){
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.close();
	}

}
	