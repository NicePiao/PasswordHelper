package com.xiaopiao.password.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xiaopiao.password.R;
import com.xiaopiao.password.model.AccountModel;
import com.xiaopiao.password.model.AccountModel.Field;
import com.xiaopiao.password.storage.database.helper.AccountHelper;
import com.xiaopiao.password.ui.view.FieldItemView;
import com.xiaopiao.password.ui.view.FieldNameChooserDialog;
import com.xiaopiao.password.ui.view.FieldNameChooserDialog.DialogListener;

/**
 * ÃÌº”–¬√‹¬ÎActivity
 * 
 * @author qinchaowei
 * 
 */
public class AddAccountActivity extends Activity implements
		android.view.View.OnClickListener {

	private LinearLayout mFieldContainer;
	private FieldItemView mAccountFeildView;
	private List<FieldItemView> mFieldViewList = new ArrayList<FieldItemView>();
	private Button mAddFieldBtn;
	private Button mSaveAccBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_add_layout);
		initView();
	}

	private void initView() {
		mFieldContainer = (LinearLayout) findViewById(R.id.field_container);
		mAccountFeildView = (FieldItemView) findViewById(R.id.account_filed);
		mAddFieldBtn = (Button) findViewById(R.id.add_new_field_btn);
		mSaveAccBtn = (Button) findViewById(R.id.save_account);
		mAddFieldBtn.setOnClickListener(this);
		mSaveAccBtn.setOnClickListener(this);

		addNewFieldItem("√‹¬Î");
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.add_new_field_btn:
			showFieldNameChooseDialog();
			break;
		case R.id.save_account:
			saveAccount();
			break;
		default:
			break;
		}
	}

	private void showFieldNameChooseDialog() {
		FieldNameChooserDialog dialog = new FieldNameChooserDialog(this);
		dialog.setDialogListener(new DialogListener() {

			@Override
			public void onClickSet(String userInput) {
				addNewFieldItem(userInput);
			}
		});
		dialog.show();

	}

	private void addNewFieldItem(String filedName) {
		if (TextUtils.isEmpty(filedName)) {
			return;
		}

		FieldItemView item = new FieldItemView(this);
		item.setTitle(filedName);
		item.setShowDelBtn(true);
		mFieldContainer.addView(item);
	}

	private void saveAccount() {
		AccountModel accModel = new AccountModel();

		String uniqueId = UUID.randomUUID().toString();
		String accountName = mAccountFeildView.getUserInput();

		accModel.uniqueId = uniqueId;
		accModel.account = accountName;
		accModel.fields = getFields();

		boolean saveSuccess = AccountHelper.getInstance(this).addNewAccount(
				accModel);
		if (!saveSuccess) {
			Toast.makeText(this, "±£¥Ê’Àªß ß∞‹", Toast.LENGTH_SHORT).show();
		} else {
			finish();
		}

	}

	private List<Field> getFields() {
		List<Field> fieldList = new ArrayList<AccountModel.Field>();
		for (int i = 0; i < mFieldContainer.getChildCount(); i++) {
			View child = mFieldContainer.getChildAt(i);
			if (child instanceof FieldItemView) {
				Field field = new Field();
				FieldItemView fv = (FieldItemView) child;
				if (fv.isShowing() && !TextUtils.isEmpty(fv.getFieldTitle())
						&& !TextUtils.isEmpty(fv.getUserInput())) {
					field.title = fv.getFieldTitle();
					field.content = fv.getUserInput().trim();
					fieldList.add(field);
				}
			}
		}
		return fieldList;
	}

}
