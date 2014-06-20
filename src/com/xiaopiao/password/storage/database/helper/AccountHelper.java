package com.xiaopiao.password.storage.database.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.database.sqlite.SQLiteDatabase;

import com.xiaopiao.password.model.AccountModel;
import com.xiaopiao.password.model.AccountModel.Field;
import com.xiaopiao.password.storage.database.DbHelper;
import com.xiaopiao.password.storage.database.table.AccountTable;
import com.xiaopiao.password.storage.database.table.FieldTable;

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

}
