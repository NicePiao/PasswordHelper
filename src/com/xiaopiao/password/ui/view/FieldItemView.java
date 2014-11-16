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
 * �˻������ֶοؼ�
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
		initView("�ֶ�", true);
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
	 * ��ȡ�û�����
	 */
	public String getUserInput() {
		if (mInputText.getText() != null) {
			return mInputText.getText().toString();
		} else {
			return "";
		}
	}

	/*
	 * ��ȡ���ֶα���
	 */
	public String getFieldTitle() {
		if (mFieldTitle.getText() != null) {
			return mFieldTitle.getText().toString();
		} else {
			return "";
		}
	}

	/**
	 * �����ֶα���
	 */
	public void setTitle(String titleText) {
		mFieldTitle.setText(titleText);
	}

	/**
	 * �����Ƿ���ʾɾ����ť
	 */
	public void setShowDelBtn(boolean showDel) {
		mDeleteBtn.setVisibility(showDel ? View.VISIBLE : View.GONE);
	}

	/**
	 * �ռ��Ƿ�û�б�����
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
			setVisibility(View.GONE);// TODO:�ȼ򵥴���
			break;
		default:
			break;
		}

	}
}
