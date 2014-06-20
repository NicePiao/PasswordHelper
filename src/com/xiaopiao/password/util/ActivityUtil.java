package com.xiaopiao.password.util;

import com.xiaopiao.password.ui.AddAccountActivity;

import android.app.Activity;
import android.content.Intent;

/**
 * util for activity jump
 * 
 * @author qinchaowei
 * 
 */
public class ActivityUtil {

	/**
	 * start to add an account
	 */
	public static void startAddAccountActivity(Activity acti) {
		Intent intent = new Intent(acti, AddAccountActivity.class);
		acti.startActivity(intent);
	}

}
