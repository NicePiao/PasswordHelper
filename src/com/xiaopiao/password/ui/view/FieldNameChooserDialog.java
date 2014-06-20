package com.xiaopiao.password.ui.view;

import java.util.ArrayList;
import java.util.List;

import com.xiaopiao.password.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 字段名字选择Dialog
 * 
 * @author qinchaowei
 * 
 */
public class FieldNameChooserDialog extends Dialog implements
		android.view.View.OnClickListener {

	private DialogListener mDialogListener;

	private TableLayout mNameTable;
	private EditText mUserInputText;
	private Button mCancleBtn;
	private Button mOkBtn;

	public FieldNameChooserDialog(Context context) {
		super(context);
	}

	private List<String> getCommonFeildName() {
		// TODO
		List<String> commonNames = new ArrayList<String>();
		commonNames.add("密码");
		commonNames.add("网址");
		commonNames.add("描述");
		commonNames.add("邮箱");
		return commonNames;
	}

	private void addCommonNameLayout() {
		List<String> commonNames = getCommonFeildName();
		TableRow row = null;
		for (int i = 0; i < commonNames.size(); i++) {
			if (i % 3 == 0) {
				row = new TableRow(getContext());
				mNameTable.addView(row);
			}

			TextView nameTextView = new TextView(getContext());
			nameTextView.setText(commonNames.get(i));
			nameTextView.setOnClickListener(getClickNameTextListener());
			row.addView(nameTextView, getNameTextLp());
		}
	}

	private TableRow.LayoutParams getNameTextLp() {
		TableRow.LayoutParams lp = new TableRow.LayoutParams();
		lp.weight = 1;
		return lp;
	}

	private void initView() {
		mNameTable = (TableLayout) findViewById(R.id.common_filed_table);
		mUserInputText = (EditText) findViewById(R.id.filed_input_text);
		mCancleBtn = (Button) findViewById(R.id.cancle);
		mOkBtn = (Button) findViewById(R.id.ok);
		addCommonNameLayout();

		mCancleBtn.setOnClickListener(this);
		mOkBtn.setOnClickListener(this);

		setTitle("新字段");
	}

	private android.view.View.OnClickListener getClickNameTextListener() {
		return new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TextView textView = (TextView) v;
				mUserInputText.append(textView.getText());
			}
		};

	}

	private void onClickSet() {
		if (mDialogListener != null) {
			mDialogListener.onClickSet(mUserInputText.getText().toString());
		}
		dismiss();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.field_name_choose_layout);
		initView();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.cancle:
			dismiss();
			break;
		case R.id.ok:
			onClickSet();
			break;
		default:
			break;
		}
	}

	/**
	 * 获取用户输入
	 */
	public String getUserInput() {
		return mUserInputText.getText().toString();
	}

	/**
	 * 设置弹框事件监听
	 */
	public void setDialogListener(DialogListener listener) {
		mDialogListener = listener;
	}

	/**
	 * Dialog事件监听
	 */
	public interface DialogListener {
		/**
		 * 用户选择设置
		 * 
		 * @param userInput
		 *            用户输入字段
		 */
		void onClickSet(String userInput);
	}

}
