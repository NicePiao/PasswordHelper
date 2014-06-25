package com.xiaopiao.password.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaopiao.password.AppApplication;
import com.xiaopiao.password.R;
import com.xiaopiao.password.storage.SharePrefCache;
import com.xiaopiao.password.ui.view.LockPatternView;
import com.xiaopiao.password.ui.view.LockPatternView.Cell;
import com.xiaopiao.password.ui.view.LockPatternView.OnPatternListener;
import com.xiaopiao.password.util.ActivityUtil;
import com.xiaopiao.password.util.LockPatternUtils;
import com.xiaopiao.password.util.SecretUtil;

/**
 * application login page
 * 
 * @author qinchaowei
 * 
 */
public class AppLoginActivity extends Activity {

	private LockPatternView mLockPatternView;
	private TextView mTopText;

	private AppApplication mAppApplication;
	private SharePrefCache mSharePref;
	private String mUserEncryptPsw;
	private String mTempleEncryptPsw;

	// private String mUserInputPsw;

	private void initParam() {
		mAppApplication = (AppApplication) getApplication();
		mSharePref = new SharePrefCache(this);
		mUserEncryptPsw = mSharePref
				.getString(SharePrefCache.Key.LOGIN_ENCRYPT_PASSWORD);
	}

	private void onUserTypeInPsw(String userTypePsw) {
		// user haven't login before
		if (TextUtils.isEmpty(mUserEncryptPsw)) {
			// first time to set password
			if (TextUtils.isEmpty(mTempleEncryptPsw)) {
				if (userTypePsw.length() < 6) {
					Toast.makeText(this,
							"too short for a password ,please enter again",
							Toast.LENGTH_SHORT).show();
					mLockPatternView
							.setDisplayMode(LockPatternView.DisplayMode.Wrong);
				} else {
					mTempleEncryptPsw = SecretUtil.SHA1(userTypePsw);
					Toast.makeText(this, "please enter again",
							Toast.LENGTH_SHORT).show();
					mLockPatternView.clearPattern();
				}
			} else {// second time to set password
				if (mTempleEncryptPsw.equals(SecretUtil.SHA1(userTypePsw))) {
					mAppApplication.setLoginPsw(userTypePsw);
					mSharePref.setString(
							SharePrefCache.Key.LOGIN_ENCRYPT_PASSWORD,
							mTempleEncryptPsw);
					onLoginSuccess();
					finish();
				} else {
					Toast.makeText(this,
							"two inputs are not the same ,please input again",
							Toast.LENGTH_SHORT).show();
					mLockPatternView
							.setDisplayMode(LockPatternView.DisplayMode.Wrong);
					mTempleEncryptPsw = "";
				}
			}
		} else {
			if (mUserEncryptPsw.equals(SecretUtil.SHA1(userTypePsw))) {
				mAppApplication.setLoginPsw(userTypePsw);
				onLoginSuccess();
				finish();
			} else {
				Toast.makeText(this, "error password ,please input again",
						Toast.LENGTH_SHORT).show();
				mLockPatternView
						.setDisplayMode(LockPatternView.DisplayMode.Wrong);
			}
		}
	}

	private void onLoginSuccess() {
		ActivityUtil.startAccountListActivity(this);
	}

	private void initView() {
		mLockPatternView = (LockPatternView) findViewById(R.id.SpLockPattern);
		mTopText = (TextView) findViewById(R.id.top_text);

		mLockPatternView.setOnPatternListener(new OnPatternListener() {

			@Override
			public void onPatternStart() {

			}

			@Override
			public void onPatternDetected(List<Cell> pattern) {
				String userInputPsw = LockPatternUtils.patternToString(pattern);
				onUserTypeInPsw(userInputPsw);
			}

			@Override
			public void onPatternCleared() {

			}

			@Override
			public void onPatternCellAdded(List<Cell> pattern) {

			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_login_layout);
		initParam();
		initView();
	}

}
