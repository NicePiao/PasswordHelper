package com.xiaopiao.password.model;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

/**
 * 一个账户数据
 * 
 * @author qinchaowei
 * 
 */
public class AccountModel {

	public String uniqueId;
	public String account;
	public List<Field> fields = new ArrayList<AccountModel.Field>();

	/**
	 * 数据是否合法
	 */
	public boolean isValid() {
		return !TextUtils.isEmpty(uniqueId) && !TextUtils.isEmpty(account);
	}

	/**
	 * 字段类 例如：{邮箱,qinchaowei1989@gmail.com}
	 */
	public static class Field {
		public String title;
		public String content;
	}

}
