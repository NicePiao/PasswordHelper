package com.xiaopiao.password.storage.database.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xiaopiao.password.model.AccountModel;
import com.xiaopiao.password.model.AccountModel.Field;
import com.xiaopiao.password.storage.database.DbHelper;
import com.xiaopiao.password.storage.database.table.AccountTable;
import com.xiaopiao.password.storage.database.table.FieldTable;
import com.xiaopiao.password.storage.database.table.FieldTable.Bean;

/**
 * 账户逻辑功能类
 * 
 * @author qinchaowei
 * 
 */
public class AccountHelper {

	private static AccountHelper sInstance;

	private Context mContext;

	private AccountHelper(Context context) {
		mContext = context.getApplicationContext();
	};

	public static AccountHelper getInstance(Context context) {

		if (sInstance == null) {
			synchronized (AccountHelper.class) {
				if (sInstance == null) {
					sInstance = new AccountHelper(context);
				}
			}
		}
		return sInstance;
	}

	/**
	 * 新增账户
	 * 
	 * @return 返回处理结果
	 */
	public boolean addNewAccount(AccountModel accountModel) {
		if (!accountModel.isValid()) {
			return false;
		}

		SQLiteDatabase db = DbHelper.getInstance(mContext)
				.getWritableDatabase();
		boolean result = AccountTable.addAccount(db, accountModel.uniqueId,
				accountModel.account);
		if (result) {
			for (Field field : accountModel.fields) {
				FieldTable.addField(db, accountModel.uniqueId, field.title,
						field.content);
			}
		}
		db.close();
		return result;
	}

	/**
	 * 获取所有的账户信息
	 */
	public List<AccountModel> getAllAccounts(Context context) {
		List<AccountModel> accountModels = new ArrayList<AccountModel>();
		SQLiteDatabase db = DbHelper.getInstance(mContext)
				.getWritableDatabase();
		List<AccountTable.Bean> accBeans = AccountTable.getAllAccounts(db);
		for (AccountTable.Bean accBean : accBeans) {
			AccountModel accountModel = new AccountModel();
			accountModel.uniqueId = accBean.id;
			accountModel.account = accBean.account;
			List<FieldTable.Bean> fieldBeans = FieldTable.getAccountFields(db,
					accBean.id);
			for (FieldTable.Bean fieldBean : fieldBeans) {
				AccountModel.Field field = new AccountModel.Field();
				field.title = fieldBean.title;
				field.content = fieldBean.content;
				accountModel.fields.add(field);
			}
			accountModels.add(accountModel);
		}
		return accountModels;
	}

	/**
	 * 获取指定账户信息
	 * 
	 * @return {@link AccountModel} or null
	 */
	public AccountModel getAccount(Context context, String uuid) {
		AccountModel accountModel = null;
		SQLiteDatabase db = DbHelper.getInstance(mContext)
				.getWritableDatabase();
		AccountTable.Bean accBean = AccountTable.getAccount(db, uuid);
		if (accBean != null) {
			accountModel = new AccountModel();
			accountModel.uniqueId = accBean.id;
			accountModel.account = accBean.account;
			List<FieldTable.Bean> fieldBeans = FieldTable.getAccountFields(db,
					accBean.id);
			for (FieldTable.Bean fieldBean : fieldBeans) {
				AccountModel.Field field = new AccountModel.Field();
				field.title = fieldBean.title;
				field.content = fieldBean.content;
				accountModel.fields.add(field);
			}
		}
		return accountModel;
	}

	/**
	 * 获取建议词
	 * 
	 * @param context
	 * @return 二元数组: 0->List<String> 1->Map<String,List<String>>
	 */
	public List<Object> getSugList(Context context) {
		List<Object> sugList = new ArrayList<Object>();
		List<String> titleList = new ArrayList<String>();
		Map<String, List<String>> contentMap = new HashMap<String, List<String>>();
		sugList.add(titleList);
		sugList.add(contentMap);

		SQLiteDatabase db = DbHelper.getInstance(mContext)
				.getWritableDatabase();
		List<Bean> allBeans = FieldTable.getAllBeans(db);

		String fieldTitle = "";
		List<String> fieldContents = new ArrayList<String>();
		for (int i = 0; i < allBeans.size(); i++) {
			Bean bean = allBeans.get(i);
			boolean toPack = fieldTitle.equals(bean.title);
			if (i == 0) {
				toPack = false;
			}
			if (i == allBeans.size() - 1) {
				toPack = true;
			}

			if (toPack) {
				fieldTitle = bean.title;
				fieldContents.add(bean.content);
				titleList.add(fieldTitle);
				contentMap.put(fieldTitle, fieldContents);
				fieldContents = new ArrayList<String>();
			}

			fieldTitle = bean.title;
			fieldContents.add(bean.content);
		}

		return sugList;
	}
}
