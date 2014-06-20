package com.xiaopiao.password.storage.database;

import com.xiaopiao.password.storage.database.table.AccountTable;
import com.xiaopiao.password.storage.database.table.FieldTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Êý¾Ý¿âÖúÊÖ
 * 
 * @author qinchaowei
 * 
 */
public class DbHelper extends SQLiteOpenHelper {

	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "password";

	private static DbHelper sInstance;

	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public static DbHelper getInstance(Context context) {
		if (sInstance == null) {
			synchronized (DbHelper.class) {
				if (sInstance == null) {
					sInstance = new DbHelper(context, DB_NAME, null, DB_VERSION);
				}
			}
		}
		return sInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	private void createTable(SQLiteDatabase db) {
		db.execSQL(AccountTable.SqlStatments.CREATE_TABLE);
		db.execSQL(FieldTable.SqlStatments.CREATE_TABLE);
	}

}
