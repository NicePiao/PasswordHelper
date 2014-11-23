package com.xiaopiao.password.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

import android.app.Activity;
import android.content.pm.FeatureInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.xiaopiao.password.R;
import com.xiaopiao.password.model.AccountModel;
import com.xiaopiao.password.model.AccountModel.Field;
import com.xiaopiao.password.storage.database.helper.AccountHelper;
import com.xiaopiao.password.ui.view.FieldItemView;
import com.xiaopiao.password.ui.view.FieldNameChooserDialog;
import com.xiaopiao.password.ui.view.FieldNameChooserDialog.DialogListener;

/**
 * 添加新密码Activity
 * 
 * @author qinchaowei
 * 
 */
public class AddAccountActivity extends Activity implements
		android.view.View.OnClickListener {

	private static final int MSG_SUG_ADD_EMPTY_FIELD_ITEM = 0;
	private static final int MSG_SUG_ADD_SCROLL = 1;

	private Handler mHandler;

	private ScrollView mScrollView;
	private LinearLayout mFieldContainer;
	private Button mSaveAccBtn;

	private List<String> mFieldSugList = new ArrayList<String>();
	private Map<String, List<String>> mContentSugMap = new HashMap<String, List<String>>();

	private boolean mIsResume = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_add_layout);
		initParam();
		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mIsResume = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		mIsResume = false;
	}

	private void initParam() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_SUG_ADD_EMPTY_FIELD_ITEM:
					addNewEmptyItemIfNeed();
					break;
				case MSG_SUG_ADD_SCROLL:
					mScrollView.smoothScrollBy(0, 1000);
					break;
				default:
					break;
				}
			}

		};
		initSugs();
	}

	private void initSugs() {
		List<Object> sugList = AccountHelper.getInstance(this).getSugList();
		mFieldSugList = (List<String>) sugList.get(0);
		mContentSugMap = (Map<String, List<String>>) sugList.get(1);
		// 添加默认值
		List<String> defTitles = new ArrayList<String>();
		defTitles.add("标题");
		defTitles.add("账户");
		defTitles.add("密码");
		defTitles.add("邮箱");
		defTitles.add("描述");
		for (String title : defTitles) {
			if (!mFieldSugList.contains(title)) {
				mFieldSugList.add(title);
			}
		}
	}

	private void initView() {
		mScrollView = (ScrollView) findViewById(R.id.scroll_view);
		mFieldContainer = (LinearLayout) findViewById(R.id.field_container);
		mSaveAccBtn = (Button) findViewById(R.id.save_account);
		mSaveAccBtn.setOnClickListener(this);
		setDefaultFieldItems();
	}

	private void setDefaultFieldItems() {
		addNewFieldItem("标题");
		addNewFieldItem("账户");
		addNewFieldItem("密码");
		addNewFieldItem("");
		updateDelBtns();
		getFieldItem(0).requestFocusView(1);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
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
		// if (TextUtils.isEmpty(filedName)) {
		// return;
		// }
		final FieldItemView item = new FieldItemView(this);
		item.setTitle(filedName);
		item.setShowDelBtn(true);
		item.setTextWatcher(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mHandler.removeMessages(MSG_SUG_ADD_EMPTY_FIELD_ITEM);
				mHandler.sendEmptyMessageDelayed(MSG_SUG_ADD_EMPTY_FIELD_ITEM,
						1000);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		mFieldContainer.addView(item);
	}

	private void addNewEmptyItemIfNeed() {
		FieldItemView fv = getLastVisiableFieldItem();
		if (fv == null || !TextUtils.isEmpty(fv.getUserInput())) {
			addNewFieldItem("");
			updateDelBtns();
			mHandler.sendEmptyMessageDelayed(MSG_SUG_ADD_SCROLL, 200);
		}
	}

	private void updateDelBtns() {
		for (int i = 0; i < mFieldContainer.getChildCount(); i++) {
			FieldItemView fv = (FieldItemView) mFieldContainer.getChildAt(i);
			fv.setShowDelBtn(true);
		}
		FieldItemView fv = getLastVisiableFieldItem();
		if (fv != null) {
			fv.setShowDelBtn(false);
		}
	}

	private void saveAccount() {
		AccountModel accModel = new AccountModel();
		String uniqueId = UUID.randomUUID().toString();
		List<Field> fileds = getFields();

		if (fileds == null || fileds.isEmpty()) {
			Toast.makeText(this, "空空如也", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}

		accModel.uniqueId = uniqueId;
		accModel.account = uniqueId;
		accModel.fields = fileds;

		boolean saveSuccess = AccountHelper.getInstance(this).addNewAccount(
				accModel);
		if (!saveSuccess) {
			Toast.makeText(this, "保存账户失败", Toast.LENGTH_SHORT).show();
		} else {
			finish();
		}

	}

	private FieldItemView getLastVisiableFieldItem() {
		for (int i = mFieldContainer.getChildCount() - 1; i >= 0; i--) {
			FieldItemView fv = (FieldItemView) mFieldContainer.getChildAt(i);
			if (fv.isShowing()) {
				return fv;
			}
		}
		return null;
	}

	private FieldItemView getFieldItem(int pos) {
		if (pos < mFieldContainer.getChildCount()) {
			return (FieldItemView) mFieldContainer.getChildAt(pos);
		} else {
			return null;
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

	/**
	 * 获取建议列表词语
	 * 
	 */
	public List<String> getSugList(FieldItemView view, int pos) {
		List<String> sugs = new ArrayList<String>();
		if (pos == FieldItemView.ITEM_TITLE) {
			List<String> titleSugs = new ArrayList<String>();
			for (String s : mFieldSugList) {
				titleSugs.add(s);
			}

			for (int i = 0; i < mFieldContainer.getChildCount(); i++) {
				FieldItemView fv = (FieldItemView) mFieldContainer
						.getChildAt(i);
				if (fv == view) {
					break;
				} else {
					if (fv.isShowing()
							&& !TextUtils.isEmpty(fv.getFieldTitle())) {
						titleSugs.remove(fv.getFieldTitle());
					}

				}
			}
			if (titleSugs != null) {
				sugs = titleSugs;
			}

		} else if (pos == FieldItemView.ITEM_CONTENT) {
			List<String> contentSugs = mContentSugMap.get(view.getFieldTitle());
			if (contentSugs != null) {
				sugs = contentSugs;
			}
		}

		// 去重
		TreeSet<String> set = new TreeSet<String>();
		set.addAll(sugs);
		String[] arr = new String[set.size()];
		set.toArray(arr);
		sugs = Arrays.asList(arr);

		return sugs;
	}

	/**
	 * 查看Activity是否已经展示
	 */
	public boolean isResume() {
		return mIsResume;
	}

}
