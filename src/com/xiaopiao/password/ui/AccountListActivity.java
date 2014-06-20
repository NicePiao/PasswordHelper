package com.xiaopiao.password.ui;

import java.util.List;

import com.xiaopiao.password.R;
import com.xiaopiao.password.model.AccountModel;
import com.xiaopiao.password.storage.database.helper.AccountHelper;
import com.xiaopiao.password.ui.adapter.AccountAdapter;
import com.xiaopiao.password.util.ActivityUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 账户列表界面
 * 
 * @author qinchaowei
 * 
 */
public class AccountListActivity extends Activity implements OnClickListener {

	private ListView mAccListV;
	private Button mAddAccBtn;

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
		mAddAccBtn.setOnClickListener(this);
		setListVContent();
	}

	private void setListVContent() {
		List<AccountModel> accModels = AccountHelper.getInstance(this)
				.getAllAccounts(this);
		mAccListV.setAdapter(new AccountAdapter(this, accModels));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_account:
			ActivityUtil.startAddAccountActivity(this);
			break;
		default:
			break;
		}
	}
}
