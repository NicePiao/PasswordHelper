package com.xiaopiao.password.sync;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.xiaopiao.password.model.AccountModel;
import com.xiaopiao.password.model.AccountModel.Field;
import com.xiaopiao.password.storage.database.helper.AccountHelper;

/**
 * 数据同步管理器
 * 
 * @author qinchaowei
 * 
 */
public class SyncManager {

	// private static final String DIR_NAME="com.xiaopiao.passwrod";
	private static final String BAK_NAME = "psw.bak";

	private static SyncManager sInstance;

	private Context mContext;

	private SyncManager(Context context) {
		mContext = context.getApplicationContext();
	};

	public static SyncManager getInstance(Context context) {

		if (sInstance == null) {
			synchronized (AccountHelper.class) {
				if (sInstance == null) {
					sInstance = new SyncManager(context);
				}
			}
		}
		return sInstance;
	}

	/**
	 * 执行同步操作 1 获取数据库内容 2 获取本地备份内容 3 合并内容 4 重新写入数据库内容 5 重新写入本地备份内容
	 */
	public void doSynchnize() {
		List<AccountModel> dbAccounts = AccountHelper.getInstance(mContext)
				.getAllAccounts();
		List<AccountModel> localAccounts = getLocalAccounts();

		List<AccountModel> insertToDB = getDbNew(dbAccounts, localAccounts);
		List<AccountModel> newLocalAccounts = new ArrayList<AccountModel>();
		newLocalAccounts.addAll(dbAccounts);
		newLocalAccounts.addAll(insertToDB);

		insertDbNew(insertToDB);
		saveLocal(newLocalAccounts);
	}

	private List<AccountModel> getLocalAccounts() {
		List<AccountModel> accountModels = new ArrayList<AccountModel>();
		try {
			File bakFile = new File(Environment.getExternalStorageDirectory()
					+ File.separator + mContext.getPackageName()
					+ File.separator + BAK_NAME);
			if (bakFile.exists()) {
				FileReader fr = new FileReader(bakFile);
				StringBuilder sb = new StringBuilder();
				char[] buff = new char[1024];
				// todo 方法调用验证
				while (true) {
					int len = fr.read(buff);
					sb.append(buff);
					if (len == -1) {
						break;
					}
				}
				fr.close();

				JSONArray jArray = new JSONArray(fr.toString());
				for (int i = 0; i < jArray.length(); i++) {
					AccountModel accModel = new AccountModel();
					JSONObject jObj = jArray.optJSONObject(i);
					accModel.uniqueId = jObj.optString("uuid");
					JSONArray fieldArr = jObj.optJSONArray("fields");
					if (fieldArr != null) {
						List<Field> fieldsList = new ArrayList<AccountModel.Field>();
						for (int j = 0; j < fieldArr.length(); i++) {
							JSONObject fieldObj = fieldArr.optJSONObject(j);
							Field field = new Field();
							field.title = fieldObj.optString("title");
							field.content = fieldObj.optString("content");
							fieldsList.add(field);
						}
						accModel.fields = fieldsList;
					}
					accountModels.add(accModel);
				}
			}
			Log.d("qcw", accountModels.toString());
		} catch (Exception e) {
		}
		return accountModels;
	}

	private List<AccountModel> getDbNew(List<AccountModel> dbAccounts,
			List<AccountModel> localAccounts) {
		List<AccountModel> insertList = new ArrayList<AccountModel>();
		// todo:效率问题
		for (AccountModel localAcc : localAccounts) {
			boolean hasContained = false;
			for (AccountModel dbAcc : dbAccounts) {
				if (localAcc.uniqueId.equals(dbAcc.uniqueId)) {
					hasContained = true;
					break;
				}
			}
			if (!hasContained) {
				insertList.add(localAcc);
			}
		}
		return insertList;
	}

	private void insertDbNew(List<AccountModel> newAccounts) {
		for (AccountModel accModel : newAccounts) {
			AccountHelper.getInstance(mContext).addNewAccount(accModel);
		}
	}

	private void saveLocal(List<AccountModel> allAccounts) {
		try {
			JSONStringer jStringer = new JSONStringer();
			jStringer.array();
			for (AccountModel accModel : allAccounts) {
				jStringer.object();
				jStringer.key("uuid").value(accModel.uniqueId);
				jStringer.key("fields");
				jStringer.array();
				for (Field field : accModel.fields) {
					jStringer.object();
					jStringer.key("title").value(field.title);
					jStringer.key("content").value(field.content);
					jStringer.endObject();
				}
				jStringer.endArray();
				jStringer.endObject();
			}
			jStringer.endArray();

			File dir = new File(Environment.getExternalStorageDirectory()
					+ File.separator + mContext.getPackageName());
			dir.mkdirs();
			File bakFile = new File(dir, BAK_NAME);
			if (!bakFile.exists()) {
				bakFile.createNewFile();
			}
			FileWriter fw = new FileWriter(bakFile, false);
			fw.write(jStringer.toString());
			fw.close();
			Log.d("qcw", jStringer.toString());
		} catch (Exception e) {
		}
	}
}
