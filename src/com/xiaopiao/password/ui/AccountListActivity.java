package com.xiaopiao.password.ui;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaopiao.password.R;
import com.xiaopiao.password.model.AccountModel;
import com.xiaopiao.password.storage.database.helper.AccountHelper;
import com.xiaopiao.password.sync.SyncManager;
import com.xiaopiao.password.ui.adapter.AccountAdapter;
import com.xiaopiao.password.ui.view.ContentDialog;
import com.xiaopiao.password.util.ActivityUtil;

/**
 * 账户列表界面
 * 
 * @author qinchaowei
 * 
 */
public class AccountListActivity extends Activity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {

	private ListView mAccListV;
	private Button mAddAccBtn;
	private View mSyncView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_list_layout);
		initViews();
	}

	@Override
	protected void onResume() {
		super.onResume();
		setListVContent();
	}

	private void initViews() {
		mAccListV = (ListView) findViewById(R.id.acc_list);
		mAddAccBtn = (Button) findViewById(R.id.add_account);
		mSyncView = findViewById(R.id.synchronize);
		mAddAccBtn.setOnClickListener(this);
		mSyncView.setOnClickListener(this);
		setListVContent();
	}

	private void setListVContent() {
		List<AccountModel> accModels = AccountHelper.getInstance(this)
				.getAllAccounts();
		mAccListV.setAdapter(new AccountAdapter(this, accModels));
		mAccListV.setOnItemClickListener(this);
		mAccListV.setOnItemLongClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_account:
			ActivityUtil.startAddAccountActivity(this);
			break;
		case R.id.synchronize:
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					SyncManager.getInstance(AccountListActivity.this)
							.doSynchnize();
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					Toast.makeText(AccountListActivity.this, "同步数据完成",
							Toast.LENGTH_SHORT).show();
					setListVContent();
				}

			}.execute();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		AccountModel accModel = ((AccountAdapter) mAccListV.getAdapter())
				.getData(position);
		if (accModel != null) {
			new ContentDialog(AccountListActivity.this, accModel.uniqueId)
					.show();
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final AccountModel accModel = ((AccountAdapter) mAccListV.getAdapter())
				.getData(position);
		if (accModel != null) {
			final String[] items = new String[] { "删除" };
			new AlertDialog.Builder(AccountListActivity.this).setTitle("操作")
					.setItems(items, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
							case 0:
								AccountHelper.getInstance(
										AccountListActivity.this).delAccount(
										accModel.uniqueId);
								setListVContent();
								break;
							}
						}
					}).show();
		}
		return true;
	}

}
