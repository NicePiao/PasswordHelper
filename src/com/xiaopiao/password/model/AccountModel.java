package com.xiaopiao.password.model;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

/**
 * һ���˻�����
 * 
 * @author qinchaowei
 * 
 */
public class AccountModel {

	public String uniqueId;
	public String account;
	public List<Field> fields = new ArrayList<AccountModel.Field>();

	/**
	 * �����Ƿ�Ϸ�
	 */
	public boolean isValid() {
		return !TextUtils.isEmpty(uniqueId) && !TextUtils.isEmpty(account);
	}

	/**
	 * �ֶ��� ���磺{����,qinchaowei1989@gmail.com}
	 */
	public static class Field {
		public String title;
		public String content;
	}

}
