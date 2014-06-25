package com.xiaopiao.password.util;

import com.xiaopiao.password.ui.AccountListActivity;
import com.xiaopiao.password.ui.AddAccountActivity;
import com.xiaopiao.password.ui.AppLoginActivity;

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
	 * start accounts activity
	 */
	public static void startAccountListActivity(Activity acti) {
		Intent intent = new Intent(acti, AccountListActivity.class);
		acti.startActivity(intent);
	}

	/**
	 * start to add an account
	 */
	public static void startAddAccountActivity(Activity acti) {
		Intent intent = new Intent(acti, AddAccountActivity.class);
		acti.startActivity(intent);
	}

	// public static void startLoginActivityForResult(Activity acti) {
	// Intent intent = new Intent(acti, AppLoginActivity.class);
	// acti.startActivity(intent);
	// }

}
