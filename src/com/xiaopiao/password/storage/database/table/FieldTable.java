package com.xiaopiao.password.storage.database.table;

import java.util.ArrayList;
import java.util.List;

import com.xiaopiao.password.storage.database.table.AccountTable.Bean;
import com.xiaopiao.password.storage.database.table.AccountTable.Columns;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FieldTable {

	public static final String TB_NAME = "field";

	public static final class Columns {
		public static final String REFID = "refid";
		public static final String TITLE = "title";
		public static final String CONTENT = "content";
	}

	public static final class SqlStatments {
		public static final String CREATE_TABLE = String.format(
				"create table %s (%s text ,%s text ,%s text)", TB_NAME,
				Columns.REFID, Columns.TITLE, Columns.CONTENT);
	}

	/**
	 * 账户表映射数据
	 */
	public static class Bean {
		public String refid;
		public String title;
		public String content;
	}

	/**
	 * 插入一条数据到数据库表中
	 */
	public static boolean addField(SQLiteDatabase database, String refid,
			String title, String content) {
		ContentValues cv = new ContentValues();
		cv.put(Columns.REFID, refid);
		cv.put(Columns.TITLE, title);
		cv.put(Columns.CONTENT, content);
		return database.insert(TB_NAME, null, cv) > 0;
	}

	/**
	 * 获取指定账户的所有字段
	 */
	public static List<Bean> getAccountFields(SQLiteDatabase database,
			String refId) {
		List<Bean> allFields = new ArrayList<Bean>();
		String selection = Columns.REFID + "= ?";
		String[] selectionArgs = new String[] { refId };
		Cursor cursor = database.query(TB_NAME, null, selection, selectionArgs,
				null, null, null);
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Bean bean = new Bean();
				bean.refid = cursor.getString(cursor
						.getColumnIndex(Columns.REFID));
				bean.title = cursor.getString(cursor
						.getColumnIndex(Columns.TITLE));
				bean.content = cursor.getString(cursor
						.getColumnIndex(Columns.CONTENT));
				allFields.add(bean);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return allFields;
	}

	/**
	 * 获取所有字段内容
	 * 
	 */
	public static List<Bean> getAllBeans(SQLiteDatabase database) {
		List<Bean> allFields = new ArrayList<Bean>();
		String orderBy = Columns.TITLE + " asc";
		Cursor cursor = database.query(TB_NAME, null, null, null, null, null,
				orderBy);
		if (cursor != null && cursor.moveToFirst()) {
			do {
				Bean bean = new Bean();
				bean.refid = cursor.getString(cursor
						.getColumnIndex(Columns.REFID));
				bean.title = cursor.getString(cursor
						.getColumnIndex(Columns.TITLE));
				bean.content = cursor.getString(cursor
						.getColumnIndex(Columns.CONTENT));
				allFields.add(bean);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return allFields;
	}

	/**
	 * 删除字段
	 */
	public static boolean delFields(SQLiteDatabase database, String uuid) {
		String whereClause = Columns.REFID + "= ?";
		String[] whereArgs = new String[] { uuid };
		int result = database.delete(TB_NAME, whereClause, whereArgs);
		return result > 0;
	}

}
