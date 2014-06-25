package com.xiaopiao.password.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePefference storage
 * 
 * @author qinchaowei
 * 
 */

public class SharePrefCache {

	public static final String name = "passwrod_sp";

	public static final class Key {
		public static final String LOGIN_ENCRYPT_PASSWORD = "login_encrypt_password";

	}

	private Context mContext;
	private SharedPreferences mSp;

	public SharePrefCache(Context context) {
		mContext = context.getApplicationContext();
		mSp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE
				| Context.MODE_MULTI_PROCESS);
	}

	/**
	 * get String value for key
	 */
	public String getString(String key) {
		return mSp.getString(key, "");
	}

	/**
	 * save the String value to the key
	 */
	public void setString(String key, String value) {
		mSp.edit().putString(key, value).commit();
	}
}