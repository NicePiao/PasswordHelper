package com.xiaopiao.password.ui.view;

import com.xiaopiao.password.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * 账户新增字段控件
 * 
 * @author qinchaowei
 * 
 */
public class FieldItemView extends RelativeLayout implements OnClickListener {

	private TextView mFieldTitle;
	private EditText mInputText;
	private ImageButton mDeleteBtn;

	public FieldItemView(Context context) {
		super(context);
		initView("字段", true);
	}

	public FieldItemView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.FieldItemView);
		String titleName = typeArray
				.getString(R.styleable.FieldItemView_filed_title_text);
		boolean showDelBtn = typeArray.getBoolean(
				R.styleable.FieldItemView_filed_delete_btn_show, true);

		initView(titleName, showDelBtn);
	}

	private void initView(String title, boolean showDelBtn) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.password_field_layout, this);

		mFieldTitle = (TextView) findViewById(R.id.field_title);
		mInputText = (EditText) findViewById(R.id.field_input);
		mDeleteBtn = (ImageButton) findViewById(R.id.field_del_btn);

		mDeleteBtn.setOnClickListener(this);
		setTitle(title);
		setShowDelBtn(showDelBtn);
	}

	/**
	 * 获取用户输入
	 */
	public String getUserInput() {
		if (mInputText.getText() != null) {
			return mInputText.getText().toString();
		} else {
			return "";
		}
	}

	/*
	 * 获取该字段标题
	 */
	public String getFieldTitle() {
		if (mFieldTitle.getText() != null) {
			return mFieldTitle.getText().toString();
		} else {
			return "";
		}
	}

	/**
	 * 设置字段标题
	 */
	public void setTitle(String titleText) {
		mFieldTitle.setText(titleText);
	}

	/**
	 * 设置是否显示删除按钮
	 */
	public void setShowDelBtn(boolean showDel) {
		mDeleteBtn.setVisibility(showDel ? View.VISIBLE : View.GONE);
	}

	/**
	 * 空间是否没有被隐藏
	 * 
	 * @return
	 */
	public boolean isShowing() {
		return getVisibility() == View.VISIBLE;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.field_del_btn:
			setVisibility(View.GONE);// TODO:先简单处理
			break;
		default:
			break;
		}

	}
}
