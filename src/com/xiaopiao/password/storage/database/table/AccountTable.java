package com.xiaopiao.password.storage.database.table;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 账户数据库表
 * 
 * @author qinchaowei
 * 
 */
public class AccountTable {

	public static final String TB_NAME = "account";

	public static final class Columns {
		public static final String ID = "id";// 唯一标识
		public static final String ACCOUNT = "account";
	}

	public static final class SqlStatments {
		public static final String CREATE_TABLE = String.format(
				"create table %s (%s text ,%s text)", TB_NAME, Columns.ID,
				Columns.ACCOUNT);
	}

	/**
	 * 插入一条数据到数据库表中
	 */
	public static boolean addAccount(SQLiteDatabase database, String id,
			String account) {
		ContentValues cv = new ContentValues();
		cv.put(Columns.ID, id);
		cv.put(Columns.ACCOUNT, account);
		return database.insert(TB_NAME, null, cv) > 0;
	}

	/**
	 * 获取所有账户
	 */
	public static List<Bean> getAllAccounts(SQLiteDatabase database) {
		List<Bean> allAccounts = new ArrayList<AccountTable.Bean>();
		Cursor cursor = database.query(TB_NAME, null, null, null, null, null,
				null);
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Bean bean = new Bean();
				bean.id = cursor.getString(cursor.getColumnIndex(Columns.ID));
				bean.account = cursor.getString(cursor
						.getColumnIndex(Columns.ACCOUNT));
				allAccounts.add(bean);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return allAccounts;
	}

	/**
	 * 获取单个账户
	 * 
	 * @return bean or null;
	 */
	public static Bean getAccount(SQLiteDatabase database, String uuid) {
		Bean bean = null;
		String selection = Columns.ID + "= ?";
		String[] selectionArgs = new String[] { uuid };
		Cursor cursor = database.query(TB_NAME, null, selection, selectionArgs,
				null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			bean = new AccountTable.Bean();
			bean.id = cursor.getString(cursor.getColumnIndex(Columns.ID));
			bean.account = cursor.getString(cursor
					.getColumnIndex(Columns.ACCOUNT));
		}
		cursor.close();
		return bean;
	}

	/**
	 * 删除账户
	 */
	public static boolean delAccount(SQLiteDatabase database, String uuid) {
		String whereClause = Columns.ID + "= ?";
		String[] whereArgs = new String[] { uuid };
		int result = database.delete(TB_NAME, whereClause, whereArgs);
		return result > 0;
	}

	/**
	 * 账户表映射数据
	 */
	public static class Bean {
		public String id;
		public String account;
	}
}
