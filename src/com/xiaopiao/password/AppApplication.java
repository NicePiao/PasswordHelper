package com.xiaopiao.password;

import android.app.Application;

public class AppApplication extends Application {

	public String mLoginPassword = "";

	public void setLoginPsw(String password) {
		mLoginPassword = password;
	}

	public String getLoginPsw() {
		return mLoginPassword;
	}

}
